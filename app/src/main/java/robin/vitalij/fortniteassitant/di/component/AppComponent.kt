package robin.vitalij.fortniteassitant.di.component

import dagger.Component
import robin.vitalij.fortniteassitant.ui.main.MainActivity
import robin.vitalij.fortniteassitant.di.module.DatabaseModule
import robin.vitalij.fortniteassitant.di.module.FortniteAppModule
import robin.vitalij.fortniteassitant.di.module.NetworkModule
import robin.vitalij.fortniteassitant.di.module.RepositoryModule
import robin.vitalij.fortniteassitant.ui.bottomsheet.profile.ProfileResultFragment
import robin.vitalij.fortniteassitant.ui.history.HistoryMatchFragment
import robin.vitalij.fortniteassitant.ui.home.HomeFragment
import robin.vitalij.fortniteassitant.ui.news.NewsFragment
import robin.vitalij.fortniteassitant.ui.search.fortnite.SearchUserFragment
import javax.inject.Singleton

@Singleton
@Component(modules = [FortniteAppModule::class, DatabaseModule::class, RepositoryModule::class, NetworkModule::class])
interface AppComponent {

    fun inject(activity: MainActivity)

    fun inject(fragment: NewsFragment)
    fun inject(fragment: SearchUserFragment)
    fun inject(fragment: HistoryMatchFragment)
    fun inject(fragment: HomeFragment)

    fun inject(fragment: ProfileResultFragment)
}