package robin.vitalij.fortniteassitant.utils.mapper

import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.common.extensions.getStringFormat
import robin.vitalij.fortniteassitant.db.entity.UserEntity
import robin.vitalij.fortniteassitant.model.enums.BattlesType
import robin.vitalij.fortniteassitant.model.enums.GameType
import robin.vitalij.fortniteassitant.model.network.stats.*
import robin.vitalij.fortniteassitant.ui.home.adapter.viewholder.statistics.adapter.viewmodel.HomeBodyStats
import robin.vitalij.fortniteassitant.ui.home.adapter.viewholder.statistics.adapter.viewmodel.HomeBodyStatsShortViewModel
import robin.vitalij.fortniteassitant.ui.home.adapter.viewholder.statistics.adapter.viewmodel.HomeBodyStatsViewModel
import robin.vitalij.fortniteassitant.utils.mapper.base.Mapper
import robin.vitalij.fortniteassitant.utils.view.ResourceProvider

private const val TWO_SESSION = 2

class DetailsStatisticsMapper(
    private val resourceProvider: ResourceProvider,
    private val battlesType: BattlesType,
    private val gameType: GameType
) : Mapper<List<UserEntity>, List<HomeBodyStats>> {

    override fun transform(obj: List<UserEntity>): List<HomeBodyStats> {
        val list = arrayListOf<HomeBodyStats>()

        val userEntity: UserEntity = obj.last()
        val userLastEntity: UserEntity = obj.first()

        when (gameType) {
            GameType.ALL -> {
                list.addAll(getAllBodyStats(userLastEntity.all!!))
            }
            GameType.KEYBOARD_MOUSE -> {
                list.addAll(getAllBodyStats(userLastEntity.keyboardMouse!!))
            }
            GameType.GAMEPAD -> {
                list.addAll(getAllBodyStats(userLastEntity.gamepad!!))
            }
            GameType.TOUCH -> {
                list.addAll(getAllBodyStats(userLastEntity.touch!!))
            }
        }

        return list
    }

    private fun getAllBodyStats(statsTypeDevice: StatsTypeDevice): List<HomeBodyStats> {
        val list = arrayListOf<HomeBodyStats>()

        when (battlesType) {
            BattlesType.OVERALL -> {
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
            }
            BattlesType.SOLO -> {
                statsTypeDevice.solo?.let {
                    list.addAll(
                        getSoloMatchesBodyStats(
                            it
                        )
                    )
                }
            }
            BattlesType.DUO -> {
                statsTypeDevice.duo?.let {
                    list.addAll(
                        getDuoMatchesBodyStats(
                            it
                        )
                    )
                }
            }
            BattlesType.TRIO -> {
                statsTypeDevice.trio?.let {
                    list.addAll(
                        getThreeMatchesBodyStats(
                            it
                        )
                    )
                }
            }
            BattlesType.SQUAD -> {
                statsTypeDevice.squad?.let {
                    list.addAll(
                        getThreeMatchesBodyStats(
                            it
                        )
                    )
                }
            }
            BattlesType.LTM -> {
                statsTypeDevice.ltm?.let {
                    list.addAll(
                        getLtmMatchesBodyStats(
                            it
                        )
                    )
                }
            }
        }

        return list
    }

    private fun getSoloMatchesBodyStats(
        soloMatches: SoloMatches
    ): List<HomeBodyStats> {
        val list = arrayListOf<HomeBodyStats>()

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
    ): List<HomeBodyStats> {
        val list = arrayListOf<HomeBodyStats>()

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
    ): List<HomeBodyStats> {
        val list = arrayListOf<HomeBodyStats>()

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

    private fun getLtmMatchesBodyStats(
        ltm: Ltm
    ): List<HomeBodyStats> {
        val list = arrayListOf<HomeBodyStats>()

        list.add(
            HomeBodyStatsViewModel(
                leftTop = ltm.matches.getStringFormat(),
                leftTopTitle = resourceProvider.getString(R.string.matches),
                rightTop = ltm.kd.getStringFormat(),
                rightTopTitle = resourceProvider.getString(R.string.kd),
                leftBottom = ltm.kills.getStringFormat(),
                leftBottomTitle = resourceProvider.getString(R.string.kills),
                rightBottom = ltm.deaths.getStringFormat(),
                rightBottomTitle = resourceProvider.getString(R.string.deaths),
                center = ltm.winRate,
                centerTitle = resourceProvider.getString(R.string.wins_percent),
            )
        )

//        list.add(
//            HomeBodyStatsShortViewModel(
//                leftTop = ltm.wins.getStringFormat(),
//                leftTopTitle = resourceProvider.getString(R.string.top_one),
//                rightTop = ltm.top3.getStringFormat(),
//                rightTopTitle = resourceProvider.getString(R.string.top_three),
//                leftBottom = ltm.top6.getStringFormat(),
//                leftBottomTitle = resourceProvider.getString(R.string.top_six),
//                rightBottom = ltm.minutesPlayed.getStringFormat(),
//                rightBottomTitle = resourceProvider.getString(R.string.minutes_played),
//            )
//        )

        return list
    }
}
