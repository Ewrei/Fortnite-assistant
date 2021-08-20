package robin.vitalij.fortniteassitant.di.module

import android.content.Context
import dagger.Module
import dagger.Provides
import robin.vitalij.fortniteassitant.repository.storage.AppPreferenceManager
import robin.vitalij.fortniteassitant.repository.storage.PreferenceManager
import javax.inject.Singleton

@Module
@Suppress("unused")
class FortniteAppModule(private val context: Context) {

    @Provides
    @Singleton
    fun context(): Context = context

    @Provides
    @Singleton
    fun providePreferenceManager(): PreferenceManager {
        return AppPreferenceManager(
            context
        )
    }
}