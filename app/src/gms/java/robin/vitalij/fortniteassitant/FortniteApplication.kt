package robin.vitalij.fortniteassitant

import android.app.Application
import com.google.android.exoplayer2.upstream.cache.SimpleCache
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.initialization.InitializationStatus
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener
import com.unity3d.ads.IUnityAdsInitializationListener
import com.unity3d.ads.UnityAds
import com.yandex.mobile.ads.common.InitializationListener
import robin.vitalij.fortniteassitant.di.component.AppComponent
import robin.vitalij.fortniteassitant.di.component.DaggerAppComponent
import robin.vitalij.fortniteassitant.di.module.DatabaseModule
import robin.vitalij.fortniteassitant.di.module.FortniteAppModule

class FortniteApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        appComponent = getComponent()
        MobileAds.initialize(this)

        UnityAds.initialize(applicationContext, "4721429",
            BuildConfig.DEBUG, true, object : IUnityAdsInitializationListener {
                override fun onInitializationComplete() {
                    //do nothing
                }

                override fun onInitializationFailed(
                    error: UnityAds.UnityAdsInitializationError?,
                    message: String?
                ) {
                    //do nothing
                }

            })

        com.yandex.mobile.ads.common.MobileAds.initialize(this, object : InitializationListener,
            OnInitializationCompleteListener {
            override fun onInitializationCompleted() {
                //do nothing
            }

            override fun onInitializationComplete(p0: InitializationStatus) {
                //do nothing
            }
        })
    }

    private fun getComponent(): AppComponent {
        return DaggerAppComponent
            .builder()
            .fortniteAppModule(FortniteAppModule(applicationContext))
            .databaseModule(DatabaseModule(applicationContext))
            .build()
    }

    companion object {

        lateinit var appComponent: AppComponent

        var simpleCache: SimpleCache? = null

    }
}