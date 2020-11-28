package robin.vitalij.fortniteassitant.repository

import android.content.Context
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.formats.UnifiedNativeAd
import robin.vitalij.fortniteassitant.BuildConfig
import robin.vitalij.fortniteassitant.interfaces.NativeResult
import robin.vitalij.fortniteassitant.repository.storage.PreferenceManager
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

private const val NUMBER_OF_ADS = 8

@Singleton
class NativeRepository @Inject constructor(
    private val context: Context,
    private val preferenceManager: PreferenceManager
) {
    private var adLoader: AdLoader? = null
    private var nativeAds = arrayListOf<UnifiedNativeAd>()

    fun loadNativeAds(nativeResult: NativeResult) {
        val builder = AdLoader.Builder(context, BuildConfig.NATIVE_ID)
        adLoader = builder.forUnifiedNativeAd { unifiedNativeAd ->
            nativeAds.add(unifiedNativeAd)
            nativeResult.onSusses(nativeAds)
        }.withAdListener(
            object : AdListener() {
                override fun onAdFailedToLoad(errorCode: Int) {
                    if (errorCode == 2) {
                        nativeResult.onSusses(nativeAds)
                    } else {
                        nativeResult.onSusses(nativeAds)
                    }
                    if (adLoader?.isLoading == false) {
                        nativeResult.onSusses(nativeAds)
                    }
                }
            }).build()

        adLoader?.loadAds(
            AdRequest.Builder().build(),
            NUMBER_OF_ADS
        )
    }

    fun getNativeAd(): UnifiedNativeAd? {
        nativeAds.shuffle()

        if (nativeAds.isEmpty()) {
            loadNativeAds(object :
                NativeResult {
                override fun onSusses(data: List<UnifiedNativeAd>) {
                }
            })
        }

        return if (nativeAds.isNotEmpty()) nativeAds[0] else null
    }

    fun getNativeAds(size: Int): List<UnifiedNativeAd> {
        nativeAds.shuffle()

        if (preferenceManager.getIsSubscription() || preferenceManager.getDisableAdvertising() > Date().time) {
            nativeAds.clear()
        } else {
            if (nativeAds.isEmpty() || nativeAds.size < size) {
                loadNativeAds(object : NativeResult {
                    override fun onSusses(data: List<UnifiedNativeAd>) {
                    }
                })
            }
        }
        return if (nativeAds.size < size) nativeAds else nativeAds.take(size)
    }
}