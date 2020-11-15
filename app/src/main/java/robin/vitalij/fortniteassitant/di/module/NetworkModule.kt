package robin.vitalij.fortniteassitant.di.module

import android.annotation.SuppressLint
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import robin.vitalij.fortniteassitant.api.FortniteRequestsApi
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager


const val TIMEOUT_SEC = 30L
private const val ROOT_STEAM_URL = "https://fortnite-api.com"

@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideSteamRequestsApi(): FortniteRequestsApi {
        val okHttpClient = HttpClientFactory()
            .createHttpClient()
        val retrofit = Retrofit.Builder()
            .baseUrl(ROOT_STEAM_URL)
            .client(okHttpClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(FortniteRequestsApi::class.java)
    }

    fun getUnsafeOkHttpClient(): OkHttpClient.Builder {
        try {
            val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {

                override fun getAcceptedIssuers(): Array<X509Certificate> {
                    return arrayOf()
                }

                @SuppressLint("TrustAllX509TrustManager")
                @Throws(CertificateException::class)
                override fun checkClientTrusted(
                    chain: Array<X509Certificate>,
                    authType: String
                ) {
                    //do nothing
                }

                @SuppressLint("TrustAllX509TrustManager")
                @Throws(CertificateException::class)
                override fun checkServerTrusted(
                    chain: Array<X509Certificate>,
                    authType: String
                ) {
                    //do nothing
                }
            })
            val sslContext = SSLContext.getInstance("SSL")
            sslContext.init(null, trustAllCerts, java.security.SecureRandom())
            val sslSocketFactory = sslContext.socketFactory
            val builder = OkHttpClient.Builder()
            builder.sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
            builder.hostnameVerifier(HostnameVerifier { var1, var2 -> true })
            return builder
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }

    inner class HttpClientFactory {
        fun createHttpClient(): OkHttpClient {
            val builder = getUnsafeOkHttpClient()
                .addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
                .protocols(listOf(Protocol.HTTP_2, Protocol.HTTP_1_1))
                .connectTimeout(TIMEOUT_SEC, TimeUnit.SECONDS)
                .readTimeout(TIMEOUT_SEC, TimeUnit.SECONDS)
                .writeTimeout(TIMEOUT_SEC, TimeUnit.SECONDS)
                .addInterceptor { chain ->
                    val request = chain.request().newBuilder().build()
                    chain.proceed(request)
                }
            return builder.build()
        }
    }
}