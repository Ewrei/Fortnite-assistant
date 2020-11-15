package robin.vitalij.fortniteassitant.di.component

import dagger.Component
import robin.vitalij.fortniteassitant.ui.main.MainActivity
import robin.vitalij.fortniteassitant.di.module.DatabaseModule
import robin.vitalij.fortniteassitant.di.module.FortniteAppModule
import robin.vitalij.fortniteassitant.di.module.NetworkModule
import robin.vitalij.fortniteassitant.di.module.RepositoryModule
import robin.vitalij.fortniteassitant.ui.news.NewsFragment
import javax.inject.Singleton

@Singleton
@Component(modules = [FortniteAppModule::class, DatabaseModule::class, RepositoryModule::class, NetworkModule::class])
interface AppComponent {

    fun inject(activity: MainActivity)

    fun inject(fragment: NewsFragment)
}