package robin.vitalij.fortniteassitant.utils.mapper

import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.common.extensions.getStringFormat
import robin.vitalij.fortniteassitant.db.entity.UserEntity
import robin.vitalij.fortniteassitant.model.network.stats.Matches
import robin.vitalij.fortniteassitant.model.network.stats.StatsTypeDevice
import robin.vitalij.fortniteassitant.ui.home.adapter.viewholder.statistics.adapter.viewmodel.*
import robin.vitalij.fortniteassitant.ui.home.adapter.viewmodel.Home
import robin.vitalij.fortniteassitant.ui.home.adapter.viewmodel.HomeHeaderViewModel
import robin.vitalij.fortniteassitant.ui.home.adapter.viewmodel.HomeStatisticsViewModel
import robin.vitalij.fortniteassitant.utils.TextUtils
import robin.vitalij.fortniteassitant.utils.mapper.base.Mapper
import robin.vitalij.fortniteassitant.utils.view.ResourceProvider

private const val TWO_SESSION = 2

class HomeMapper(
    private val resourceProvider: ResourceProvider
) : Mapper<List<UserEntity>, List<Home>> {

    override fun transform(obj: List<UserEntity>): List<Home> {
        val list = arrayListOf<Home>()

        val userEntity: UserEntity = obj.last()
        val userLastEntity: UserEntity = obj.first()

        list.add(
            HomeHeaderViewModel(
                avatarUrl = "",
                userName = userLastEntity.name,
                playerId = userLastEntity.playerId,
                matchesPlayed = userLastEntity.all?.overall?.matches ?: 0,
                timeHours = TextUtils.getAverage(
                    userLastEntity.all?.overall?.minutesPlayed?.toDouble(),
                    60.0
                ),
            )
        )

        list.add(
            HomeStatisticsViewModel(
                getAllBodyStats(userLastEntity.all),
                getAllBodyStats(userLastEntity.keyboardMouse),
                getAllBodyStats(userLastEntity.gamepad),
                getAllBodyStats(userLastEntity.touch)
            )
        )

        return list
    }

    private fun getAllBodyStats(statsTypeDevice: StatsTypeDevice?): List<HomeBodyStats> {
        val list = arrayListOf<HomeBodyStats>()

        if (statsTypeDevice == null || statsTypeDevice.overall?.matches == 0) {
            list.add(HomeBodyEmptyViewModel(resourceProvider.getString(R.string.no_results_for_this_platform)))
        } else {

            list.add(HomeBodyHeaderViewModel(resourceProvider.getString(R.string.overall_battles)))

            list.add(
                HomeBodyStatsViewModel(
                    leftTop = statsTypeDevice.overall!!.matches.getStringFormat(),
                    leftTopTitle = resourceProvider.getString(R.string.matches),
                    rightTop = statsTypeDevice.overall.kd.getStringFormat(),
                    rightTopTitle = resourceProvider.getString(R.string.kd),
                    leftBottom = statsTypeDevice.overall.kills.getStringFormat(),
                    leftBottomTitle = resourceProvider.getString(R.string.kills),
                    rightBottom = statsTypeDevice.overall.deaths.getStringFormat(),
                    rightBottomTitle = resourceProvider.getString(R.string.deaths),
                    center = statsTypeDevice.overall.winRate,
                    centerTitle = resourceProvider.getString(R.string.wins_percent),
                )
            )

            list.add(
                HomeBodyStatsShortViewModel(
                    leftTop = statsTypeDevice.overall.wins.getStringFormat(),
                    leftTopTitle = resourceProvider.getString(R.string.top_one),
                    rightTop = statsTypeDevice.overall.top3.getStringFormat(),
                    rightTopTitle = resourceProvider.getString(R.string.top_three),
                    leftBottom = statsTypeDevice.overall.top5.getStringFormat(),
                    leftBottomTitle = resourceProvider.getString(R.string.top_five),
                    rightBottom = statsTypeDevice.overall.top10.getStringFormat(),
                    rightBottomTitle = resourceProvider.getString(R.string.top_ten),
                )
            )

            statsTypeDevice.solo?.let {
                list.addAll(
                    getMatchesBodyStats(
                        it,
                        resourceProvider.getString(R.string.solo_battles)
                    )
                )
            }

            statsTypeDevice.duo?.let {
                list.addAll(
                    getMatchesBodyStats(
                        it,
                        resourceProvider.getString(R.string.duo_battles)
                    )
                )
            }

            statsTypeDevice.trio?.let {
                list.addAll(
                    getMatchesBodyStats(
                        it,
                        resourceProvider.getString(R.string.trio_battles)
                    )
                )
            }

            statsTypeDevice.squad?.let {
                list.addAll(
                    getMatchesBodyStats(
                        it,
                        resourceProvider.getString(R.string.squad_battles)
                    )
                )
            }
        }

        return list
    }

    private fun getMatchesBodyStats(matches: Matches, battlesTitle: String): List<HomeBodyStats> {
        val list = arrayListOf<HomeBodyStats>()

        list.add(HomeBodyHeaderViewModel(battlesTitle))

        list.add(
            HomeBodyStatsViewModel(
                leftTop = matches.matches.getStringFormat(),
                leftTopTitle = resourceProvider.getString(R.string.matches),
                rightTop = matches.kd.getStringFormat(),
                rightTopTitle = resourceProvider.getString(R.string.kd),
                leftBottom = matches.kills.getStringFormat(),
                leftBottomTitle = resourceProvider.getString(R.string.kills),
                rightBottom = matches.deaths.getStringFormat(),
                rightBottomTitle = resourceProvider.getString(R.string.deaths),
                center = matches.winRate,
                centerTitle = resourceProvider.getString(R.string.wins_percent),
            )
        )

        list.add(
            HomeBodyStatsShortViewModel(
                leftTop = matches.wins.getStringFormat(),
                leftTopTitle = resourceProvider.getString(R.string.top_one),
                rightTop = matches.top10.getStringFormat(),
                rightTopTitle = resourceProvider.getString(R.string.top_ten),
                leftBottom = matches.top25.getStringFormat(),
                leftBottomTitle = resourceProvider.getString(R.string.top_twenty_five),
                rightBottom = matches.minutesPlayed.getStringFormat(),
                rightBottomTitle = resourceProvider.getString(R.string.minutes_played),
            )
        )

        return list
    }
}
