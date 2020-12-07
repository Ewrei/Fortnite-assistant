package robin.vitalij.fortniteassitant.utils.mapper

import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.common.extensions.differenceUser
import robin.vitalij.fortniteassitant.common.extensions.getDetailStatisticsModelList
import robin.vitalij.fortniteassitant.common.extensions.getStringFormat
import robin.vitalij.fortniteassitant.db.entity.UserEntity
import robin.vitalij.fortniteassitant.db.projection.UserHistory
import robin.vitalij.fortniteassitant.model.FullHomeModel
import robin.vitalij.fortniteassitant.model.network.stats.DuoMatches
import robin.vitalij.fortniteassitant.model.network.stats.SoloMatches
import robin.vitalij.fortniteassitant.model.network.stats.StatsTypeDevice
import robin.vitalij.fortniteassitant.model.network.stats.TrioMatches
import robin.vitalij.fortniteassitant.ui.home.adapter.viewholder.session.adapter.viewmodel.HomeSession
import robin.vitalij.fortniteassitant.ui.home.adapter.viewholder.session.adapter.viewmodel.HomeSessionOtherViewModel
import robin.vitalij.fortniteassitant.ui.home.adapter.viewholder.session.adapter.viewmodel.HomeSessionSessionViewModel
import robin.vitalij.fortniteassitant.ui.home.adapter.viewholder.statistics.adapter.viewmodel.*
import robin.vitalij.fortniteassitant.ui.home.adapter.viewmodel.*
import robin.vitalij.fortniteassitant.utils.TextUtils
import robin.vitalij.fortniteassitant.utils.mapper.base.Mapper
import robin.vitalij.fortniteassitant.utils.ResourceProvider

private const val TWO_SESSION = 2

class HomeMapper(
    private val resourceProvider: ResourceProvider,
    private val histories: List<UserHistory>
) : Mapper<List<UserEntity>, FullHomeModel> {

    override fun transform(obj: List<UserEntity>): FullHomeModel {
        val list = arrayListOf<Home>()

        val userEntity: UserEntity = obj.last()
        val userLastEntity: UserEntity = obj.first()

        list.add(
            HomeHeaderViewModel(
                avatarUrl = userLastEntity.avatar ?: "",
                userName = userLastEntity.name,
                playerId = userLastEntity.playerId,
                matchesPlayed = userLastEntity.all?.overall?.matches ?: 0,
                timeHours = TextUtils.getAverage(
                    userLastEntity.all?.overall?.minutesPlayed?.toDouble(),
                    60.0
                ),
            )
        )

        val history = HistoryMapper().transform(histories)
        if (history.isNotEmpty()) {
            list.add(HomeTitleViewModel(resourceProvider.getString(R.string.game_sessions)))

            if (history.size <= TWO_SESSION) {
                val sessions = arrayListOf<HomeSession>()
                history.forEach {
                    sessions.add(HomeSessionSessionViewModel(it))
                }
                list.add(
                    HomeSessionViewModel(
                        sessions
                    )
                )
            } else {
                val sessions = arrayListOf<HomeSession>()
                history.take(TWO_SESSION).forEach {
                    sessions.add(HomeSessionSessionViewModel(it))
                }

                var lastHistory = histories[2].user
                lastHistory = histories.last().user.differenceUser(lastHistory)

                sessions.add(
                    HomeSessionOtherViewModel(
                        matches = lastHistory.all?.overall?.matches ?: 0,
                        winRate = lastHistory.all?.overall?.winRate ?: 0.0,
                        kd = lastHistory.all?.overall?.kd ?: 0.0
                    )
                )
                list.add(
                    HomeSessionViewModel(
                        sessions
                    )
                )
            }
        }

        list.add(
            HomeStatisticsViewModel(
                getAllBodyStats(userLastEntity.all),
                getAllBodyStats(userLastEntity.keyboardMouse),
                getAllBodyStats(userLastEntity.gamepad),
                getAllBodyStats(userLastEntity.touch)
            )
        )

        return FullHomeModel(homes = list, userLastEntity.getDetailStatisticsModelList())
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
                    getSoloMatchesBodyStats(
                        it,
                        resourceProvider.getString(R.string.solo_battles)
                    )
                )
            }

            statsTypeDevice.duo?.let {
                list.addAll(
                    getDuoMatchesBodyStats(
                        it,
                        resourceProvider.getString(R.string.duo_battles)
                    )
                )
            }

            statsTypeDevice.trio?.let {
                list.addAll(
                    getThreeMatchesBodyStats(
                        it,
                        resourceProvider.getString(R.string.trio_battles)
                    )
                )
            }

            statsTypeDevice.squad?.let {
                list.addAll(
                    getThreeMatchesBodyStats(
                        it,
                        resourceProvider.getString(R.string.squad_battles)
                    )
                )
            }

            list.add(HomeBodeDetailsStatisticsViewModel(resourceProvider.getString(R.string.overall_battles)))
        }

        return list
    }

    private fun getSoloMatchesBodyStats(
        soloMatches: SoloMatches,
        battlesTitle: String
    ): List<HomeBodyStats> {
        val list = arrayListOf<HomeBodyStats>()

        list.add(HomeBodyHeaderViewModel(battlesTitle))

        list.add(
            HomeBodyStatsViewModel(
                leftTop = soloMatches.matches.getStringFormat(),
                leftTopTitle = resourceProvider.getString(R.string.matches),
                rightTop = soloMatches.kd.getStringFormat(),
                rightTopTitle = resourceProvider.getString(R.string.kd),
                leftBottom = soloMatches.kills.getStringFormat(),
                leftBottomTitle = resourceProvider.getString(R.string.kills),
                rightBottom = soloMatches.deaths.getStringFormat(),
                rightBottomTitle = resourceProvider.getString(R.string.deaths),
                center = soloMatches.winRate,
                centerTitle = resourceProvider.getString(R.string.wins_percent),
            )
        )

        list.add(
            HomeBodyStatsShortViewModel(
                leftTop = soloMatches.wins.getStringFormat(),
                leftTopTitle = resourceProvider.getString(R.string.top_one),
                rightTop = soloMatches.top10.getStringFormat(),
                rightTopTitle = resourceProvider.getString(R.string.top_ten),
                leftBottom = soloMatches.top25.getStringFormat(),
                leftBottomTitle = resourceProvider.getString(R.string.top_twenty_five),
                rightBottom = soloMatches.minutesPlayed.getStringFormat(),
                rightBottomTitle = resourceProvider.getString(R.string.minutes_played),
            )
        )

        return list
    }

    private fun getDuoMatchesBodyStats(
        soloMatches: DuoMatches,
        battlesTitle: String
    ): List<HomeBodyStats> {
        val list = arrayListOf<HomeBodyStats>()

        list.add(HomeBodyHeaderViewModel(battlesTitle))

        list.add(
            HomeBodyStatsViewModel(
                leftTop = soloMatches.matches.getStringFormat(),
                leftTopTitle = resourceProvider.getString(R.string.matches),
                rightTop = soloMatches.kd.getStringFormat(),
                rightTopTitle = resourceProvider.getString(R.string.kd),
                leftBottom = soloMatches.kills.getStringFormat(),
                leftBottomTitle = resourceProvider.getString(R.string.kills),
                rightBottom = soloMatches.deaths.getStringFormat(),
                rightBottomTitle = resourceProvider.getString(R.string.deaths),
                center = soloMatches.winRate,
                centerTitle = resourceProvider.getString(R.string.wins_percent),
            )
        )

        list.add(
            HomeBodyStatsShortViewModel(
                leftTop = soloMatches.wins.getStringFormat(),
                leftTopTitle = resourceProvider.getString(R.string.top_one),
                rightTop = soloMatches.top5.getStringFormat(),
                rightTopTitle = resourceProvider.getString(R.string.top_five),
                leftBottom = soloMatches.top12.getStringFormat(),
                leftBottomTitle = resourceProvider.getString(R.string.top_twelve),
                rightBottom = soloMatches.minutesPlayed.getStringFormat(),
                rightBottomTitle = resourceProvider.getString(R.string.minutes_played),
            )
        )

        return list
    }

    private fun getThreeMatchesBodyStats(
        trioMatches: TrioMatches,
        battlesTitle: String
    ): List<HomeBodyStats> {
        val list = arrayListOf<HomeBodyStats>()

        list.add(HomeBodyHeaderViewModel(battlesTitle))

        list.add(
            HomeBodyStatsViewModel(
                leftTop = trioMatches.matches.getStringFormat(),
                leftTopTitle = resourceProvider.getString(R.string.matches),
                rightTop = trioMatches.kd.getStringFormat(),
                rightTopTitle = resourceProvider.getString(R.string.kd),
                leftBottom = trioMatches.kills.getStringFormat(),
                leftBottomTitle = resourceProvider.getString(R.string.kills),
                rightBottom = trioMatches.deaths.getStringFormat(),
                rightBottomTitle = resourceProvider.getString(R.string.deaths),
                center = trioMatches.winRate,
                centerTitle = resourceProvider.getString(R.string.wins_percent),
            )
        )

        list.add(
            HomeBodyStatsShortViewModel(
                leftTop = trioMatches.wins.getStringFormat(),
                leftTopTitle = resourceProvider.getString(R.string.top_one),
                rightTop = trioMatches.top3.getStringFormat(),
                rightTopTitle = resourceProvider.getString(R.string.top_three),
                leftBottom = trioMatches.top6.getStringFormat(),
                leftBottomTitle = resourceProvider.getString(R.string.top_six),
                rightBottom = trioMatches.minutesPlayed.getStringFormat(),
                rightBottomTitle = resourceProvider.getString(R.string.minutes_played),
            )
        )

        return list
    }
}
