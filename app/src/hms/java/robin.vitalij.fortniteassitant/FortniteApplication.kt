package robin.vitalij.fortniteassitant

import android.app.Application
import com.google.android.exoplayer2.upstream.cache.SimpleCache
import com.huawei.hms.ads.HwAds
import robin.vitalij.fortniteassitant.di.component.AppComponent
import robin.vitalij.fortniteassitant.di.component.DaggerAppComponent
import robin.vitalij.fortniteassitant.di.module.DatabaseModule
import robin.vitalij.fortniteassitant.di.module.FortniteAppModule

class FortniteApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        appComponent = getComponent()
        HwAds.init(this)
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

    }
}