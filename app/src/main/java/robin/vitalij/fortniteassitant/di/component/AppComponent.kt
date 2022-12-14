package robin.vitalij.fortniteassitant.di.component

import dagger.Component
import robin.vitalij.fortniteassitant.di.module.DatabaseModule
import robin.vitalij.fortniteassitant.di.module.FortniteAppModule
import robin.vitalij.fortniteassitant.di.module.NetworkModule
import robin.vitalij.fortniteassitant.di.module.RepositoryModule
import robin.vitalij.fortniteassitant.ui.achiviements.AchievementsFragment
import robin.vitalij.fortniteassitant.ui.ads_gift_fever.BasicRulesFragment
import robin.vitalij.fortniteassitant.ui.banners.BannersFragment
import robin.vitalij.fortniteassitant.ui.battle_pass_rewards.BattlePassRewardsFragment
import robin.vitalij.fortniteassitant.ui.bottomsheet.banner.BannerResultFragment
import robin.vitalij.fortniteassitant.ui.bottomsheet.battlepassrewards.BattlePassRewardsResultFragment
import robin.vitalij.fortniteassitant.ui.bottomsheet.contactus.ContactUsResultFragment
import robin.vitalij.fortniteassitant.ui.bottomsheet.cosmetic.CosmeticResultFragment
import robin.vitalij.fortniteassitant.ui.bottomsheet.currentshop.CurrentShopResultFragment
import robin.vitalij.fortniteassitant.ui.bottomsheet.fish.FishResultFragment
import robin.vitalij.fortniteassitant.ui.bottomsheet.input_account_id.InputAccountIdResultFragment
import robin.vitalij.fortniteassitant.ui.bottomsheet.profile.ProfileResultFragment
import robin.vitalij.fortniteassitant.ui.bottomsheet.top.TopResultFragment
import robin.vitalij.fortniteassitant.ui.bottomsheet.upcomingshop.UpcomingShopResultFragment
import robin.vitalij.fortniteassitant.ui.bottomsheet.user.UserResultFragment
import robin.vitalij.fortniteassitant.ui.bottomsheet.vehicles.VehiclesResultFragment
import robin.vitalij.fortniteassitant.ui.bottomsheet.weapon.WeaponResultFragment
import robin.vitalij.fortniteassitant.ui.chartlist.ChartsTypeFragment
import robin.vitalij.fortniteassitant.ui.chartlist.view_pager.AdapterChartsTypeFragment
import robin.vitalij.fortniteassitant.ui.charts.ChartsFragment
import robin.vitalij.fortniteassitant.ui.comparison.selected.ComparisonSelectedFragment
import robin.vitalij.fortniteassitant.ui.comparison.selected.listuser.SelectedListUserFragment
import robin.vitalij.fortniteassitant.ui.comparison.selected.manyaccount.statistics.ComparisonManyPlayersStatisticsFragment
import robin.vitalij.fortniteassitant.ui.comparison.selected.manyaccount.viewpager.AdapterManyAccountFragment
import robin.vitalij.fortniteassitant.ui.comparison.statistics.ComparisonStatisticsFragment
import robin.vitalij.fortniteassitant.ui.comparison.viewpager.AdapterComparisonFragment
import robin.vitalij.fortniteassitant.ui.cosmetics.CatalogCosmeticsFragment
import robin.vitalij.fortniteassitant.ui.cosmetics.catalog.CosmeticsFragment
import robin.vitalij.fortniteassitant.ui.cosmetics_new.CosmeticsNewFragment
import robin.vitalij.fortniteassitant.ui.crew.details.CrewViewDetailsFragment
import robin.vitalij.fortniteassitant.ui.crew.main.GameCrewFragment
import robin.vitalij.fortniteassitant.ui.details.statistics.DetailsStatisticsFragment
import robin.vitalij.fortniteassitant.ui.details.viewpager.AdapterDetailsStatisticsFragment
import robin.vitalij.fortniteassitant.ui.fish.FishFragment
import robin.vitalij.fortniteassitant.ui.fishstats.FishStatsFragment
import robin.vitalij.fortniteassitant.ui.history.HistoryFragment
import robin.vitalij.fortniteassitant.ui.home.HomeFragment
import robin.vitalij.fortniteassitant.ui.main.MainActivity
import robin.vitalij.fortniteassitant.ui.news.fragment.NewsFragment
import robin.vitalij.fortniteassitant.ui.news.view_pager.AdapterNewsFragment
import robin.vitalij.fortniteassitant.ui.search.fortnite.SearchUserFragment
import robin.vitalij.fortniteassitant.ui.season.statistics.DetailsSeasonStatisticsFragment
import robin.vitalij.fortniteassitant.ui.season.viewpager.AdapterDetailsSeasonStatisticsFragment
import robin.vitalij.fortniteassitant.ui.session.statistics.DetailsSessionStatisticsFragment
import robin.vitalij.fortniteassitant.ui.session.viewpager.AdapterSessionFragment
import robin.vitalij.fortniteassitant.ui.setting.SettingFragment
import robin.vitalij.fortniteassitant.ui.setting.applicationinfo.ApplicationInfoFragment
import robin.vitalij.fortniteassitant.ui.shop.current.CurrentShopFragment
import robin.vitalij.fortniteassitant.ui.shop.upcoming.UpcomingShopFragment
import robin.vitalij.fortniteassitant.ui.shop.viewpager.AdapterShoppingFragment
import robin.vitalij.fortniteassitant.ui.splash.SplashActivity
import robin.vitalij.fortniteassitant.ui.subscription.SubscriptionsFragment
import robin.vitalij.fortniteassitant.ui.top.TopFragment
import robin.vitalij.fortniteassitant.ui.users.UsersFragment
import robin.vitalij.fortniteassitant.ui.vehicles.VehiclesFragment
import robin.vitalij.fortniteassitant.ui.weapons.WeaponFragment
import robin.vitalij.fortniteassitant.ui.wiki.WikiFragment
import javax.inject.Singleton

@Singleton
@Component(modules = [FortniteAppModule::class, DatabaseModule::class, RepositoryModule::class, NetworkModule::class])
interface AppComponent {

    fun inject(activity: MainActivity)
    fun inject(activity: SplashActivity)

    fun inject(fragment: NewsFragment)
    fun inject(fragment: SearchUserFragment)
    fun inject(fragment: HomeFragment)
    fun inject(fragment: DetailsStatisticsFragment)
    fun inject(fragment: AdapterDetailsStatisticsFragment)
    fun inject(fragment: ChartsFragment)
    fun inject(fragment: SubscriptionsFragment)
    fun inject(fragment: ChartsTypeFragment)
    fun inject(fragment: ApplicationInfoFragment)
    fun inject(fragment: SettingFragment)
    fun inject(fragment: UsersFragment)
    fun inject(fragment: AdapterComparisonFragment)
    fun inject(fragment: ComparisonStatisticsFragment)
    fun inject(fragment: SelectedListUserFragment)
    fun inject(fragment: ComparisonManyPlayersStatisticsFragment)
    fun inject(fragment: ComparisonSelectedFragment)
    fun inject(fragment: AdapterManyAccountFragment)
    fun inject(fragment: HistoryFragment)
    fun inject(fragment: DetailsSessionStatisticsFragment)
    fun inject(fragment: AdapterSessionFragment)
    fun inject(fragment: DetailsSeasonStatisticsFragment)
    fun inject(fragment: AdapterDetailsSeasonStatisticsFragment)
    fun inject(fragment: AdapterShoppingFragment)
    fun inject(fragment: TopFragment)
    fun inject(fragment: UpcomingShopFragment)
    fun inject(fragment: WikiFragment)
    fun inject(fragment: BattlePassRewardsFragment)
    fun inject(fragment: AdapterChartsTypeFragment)
    fun inject(fragment: WeaponFragment)
    fun inject(fragment: FishFragment)
    fun inject(fragment: FishStatsFragment)
    fun inject(fragment: AchievementsFragment)
    fun inject(fragment: CosmeticsNewFragment)
    fun inject(fragment: CatalogCosmeticsFragment)
    fun inject(fragment: CosmeticsFragment)
    fun inject(fragment: AdapterNewsFragment)
    fun inject(fragment: BannersFragment)
    fun inject(fragment: GameCrewFragment)
    fun inject(fragment: CrewViewDetailsFragment)
    fun inject(fragment: VehiclesFragment)
    fun inject(fragment: CurrentShopFragment)

    fun inject(fragment: CosmeticResultFragment)
    fun inject(fragment: ProfileResultFragment)
    fun inject(fragment: ContactUsResultFragment)
    fun inject(fragment: UserResultFragment)
    fun inject(fragment: TopResultFragment)
    fun inject(fragment: UpcomingShopResultFragment)
    fun inject(fragment: CurrentShopResultFragment)
    fun inject(fragment: BattlePassRewardsResultFragment)
    fun inject(fragment: WeaponResultFragment)
    fun inject(fragment: FishResultFragment)
    fun inject(fragment: BannerResultFragment)
    fun inject(fragment: VehiclesResultFragment)
    fun inject(fragment: InputAccountIdResultFragment)
    fun inject(fragment: BasicRulesFragment)
}