package robin.vitalij.fortniteassitant.di.component

import dagger.Component
import robin.vitalij.fortniteassitant.ui.main.MainActivity
import robin.vitalij.fortniteassitant.di.module.DatabaseModule
import robin.vitalij.fortniteassitant.di.module.FortniteAppModule
import robin.vitalij.fortniteassitant.di.module.NetworkModule
import robin.vitalij.fortniteassitant.di.module.RepositoryModule
import robin.vitalij.fortniteassitant.ui.bottomsheet.profile.ProfileResultFragment
import robin.vitalij.fortniteassitant.ui.chartlist.ChartsTypeFragment
import robin.vitalij.fortniteassitant.ui.charts.ChartsFragment
import robin.vitalij.fortniteassitant.ui.details.statistics.DetailsStatisticsFragment
import robin.vitalij.fortniteassitant.ui.details.viewpager.AdapterDetailsStatisticsFragment
import robin.vitalij.fortniteassitant.ui.history.HistoryMatchFragment
import robin.vitalij.fortniteassitant.ui.home.HomeFragment
import robin.vitalij.fortniteassitant.ui.news.NewsFragment
import robin.vitalij.fortniteassitant.ui.search.fortnite.SearchUserFragment
import robin.vitalij.fortniteassitant.ui.subscription.SubscriptionsFragment
import javax.inject.Singleton

@Singleton
@Component(modules = [FortniteAppModule::class, DatabaseModule::class, RepositoryModule::class, NetworkModule::class])
interface AppComponent {

    fun inject(activity: MainActivity)

    fun inject(fragment: NewsFragment)
    fun inject(fragment: SearchUserFragment)
    fun inject(fragment: HistoryMatchFragment)
    fun inject(fragment: HomeFragment)
    fun inject(fragment: DetailsStatisticsFragment)
    fun inject(fragment: AdapterDetailsStatisticsFragment)
    fun inject(fragment: ChartsFragment)
    fun inject(fragment: SubscriptionsFragment)
    fun inject(fragment: ChartsTypeFragment)

    fun inject(fragment: ProfileResultFragment)
}