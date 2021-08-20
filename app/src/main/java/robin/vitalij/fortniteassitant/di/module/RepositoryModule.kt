package robin.vitalij.fortniteassitant.di.module

import android.content.Context
import dagger.Module
import dagger.Provides
import robin.vitalij.fortniteassitant.utils.ResourceProvider
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun resourceProvider(context: Context) = ResourceProvider(context)

}