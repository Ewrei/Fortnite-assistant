package robin.vitalij.fortniteassitant.utils.mapper

import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.common.extensions.DATE_STATS_FULL
import robin.vitalij.fortniteassitant.common.extensions.getDateZFull
import robin.vitalij.fortniteassitant.common.extensions.getStringFormat
import robin.vitalij.fortniteassitant.db.entity.UserEntity
import robin.vitalij.fortniteassitant.model.enums.BattlesType
import robin.vitalij.fortniteassitant.model.enums.GameType
import robin.vitalij.fortniteassitant.model.network.stats.*
import robin.vitalij.fortniteassitant.ui.home.adapter.viewholder.statistics.adapter.viewmodel.*
import robin.vitalij.fortniteassitant.utils.mapper.base.Mapper
import robin.vitalij.fortniteassitant.utils.ResourceProvider

private const val TWO_SESSION = 2

class DetailsStatisticsMapper(
    private val resourceProvider: ResourceProvider,
    private val battlesType: BattlesType,
    private val gameType: GameType
) : Mapper<List<UserEntity>, List<HomeBodyStats>> {

    override fun transform(obj: List<UserEntity>): List<HomeBodyStats> {
        val list = mutableListOf<HomeBodyStats>()

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
        val list = mutableListOf<HomeBodyStats>()

        when (battlesType) {
            BattlesType.OVERALL -> {
                list.addAll(getOverallBodyStats(statsTypeDevice.overall!!))
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

    private fun getOverallBodyStats(overall: Overall): List<HomeBodyStats> {
        val list = mutableListOf<HomeBodyStats>()

        list.add(
            HomeBodyStatsViewModel(
                leftTop = overall.matches.getStringFormat(),
                leftTopTitle = resourceProvider.getString(R.string.matches),
                rightTop = overall.kd.getStringFormat(),
                rightTopTitle = resourceProvider.getString(R.string.kd),
                leftBottom = overall.kills.getStringFormat(),
                leftBottomTitle = resourceProvider.getString(R.string.kills),
                rightBottom = overall.deaths.getStringFormat(),
                rightBottomTitle = resourceProvider.getString(R.string.deaths),
                center = overall.winRate,
                centerTitle = resourceProvider.getString(R.string.wins_percent),
            )
        )

        list.add(
            HomeBodyStatsShortViewModel(
                leftTop = overall.wins.getStringFormat(),
                leftTopTitle = resourceProvider.getString(R.string.top_one),
                rightTop = overall.top3.getStringFormat(),
                rightTopTitle = resourceProvider.getString(R.string.top_three),
                leftBottom = overall.top5.getStringFormat(),
                leftBottomTitle = resourceProvider.getString(R.string.top_five),
                rightBottom = overall.top6.getStringFormat(),
                rightBottomTitle = resourceProvider.getString(R.string.top_six),
            )
        )

        list.add(
            HomeBodyStatsShortViewModel(
                leftTop = overall.top10.getStringFormat(),
                leftTopTitle = resourceProvider.getString(R.string.top_ten),
                rightTop = overall.top12.getStringFormat(),
                rightTopTitle = resourceProvider.getString(R.string.top_twelve),
                leftBottom = overall.top25.getStringFormat(),
                leftBottomTitle = resourceProvider.getString(R.string.top_twenty_five),
                rightBottom = overall.lastModified.getDateZFull(DATE_STATS_FULL),
                rightBottomTitle = resourceProvider.getString(R.string.last_modified),
            )
        )

        list.add(HomeBodyHeaderViewModel(resourceProvider.getString(R.string.score)))

        list.add(
            HomeBodyStatsShortViewModel(
                leftTop = overall.score.getStringFormat(),
                leftTopTitle = resourceProvider.getString(R.string.score),
                rightTop = overall.getAvgScore().getStringFormat(),
                rightTopTitle = resourceProvider.getString(R.string.avg_score),
                leftBottom = overall.scorePerMin.getStringFormat(),
                leftBottomTitle = resourceProvider.getString(R.string.score_per_min),
                rightBottom = overall.scorePerMatch.getStringFormat(),
                rightBottomTitle = resourceProvider.getString(R.string.score_per_match),
            )
        )

        list.add(HomeBodyHeaderViewModel(resourceProvider.getString(R.string.other)))

        list.add(
            HomeBodyStatsShortViewModel(
                leftTop = overall.killsPerMin.getStringFormat(),
                leftTopTitle = resourceProvider.getString(R.string.kills_per_min),
                rightTop = overall.killsPerMatch.getStringFormat(),
                rightTopTitle = resourceProvider.getString(R.string.kills_per_match),
                leftBottom = overall.minutesPlayed.getStringFormat(),
                leftBottomTitle = resourceProvider.getString(R.string.minutes_played),
                rightBottom = overall.playersOutlived.getStringFormat(),
                rightBottomTitle = resourceProvider.getString(R.string.players_outlived),
            )
        )

        return list
    }

    private fun getSoloMatchesBodyStats(
        soloMatches: SoloMatches
    ): List<HomeBodyStats> {
        val list = mutableListOf<HomeBodyStats>()

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
                rightBottom = soloMatches.lastModified.getDateZFull(DATE_STATS_FULL),
                rightBottomTitle = resourceProvider.getString(R.string.last_modified),
            )
        )

        list.add(HomeBodyHeaderViewModel(resourceProvider.getString(R.string.score)))

        list.add(
            HomeBodyStatsShortViewModel(
                leftTop = soloMatches.score.getStringFormat(),
                leftTopTitle = resourceProvider.getString(R.string.score),
                rightTop = soloMatches.getAvgScore().getStringFormat(),
                rightTopTitle = resourceProvider.getString(R.string.avg_score),
                leftBottom = soloMatches.scorePerMin.getStringFormat(),
                leftBottomTitle = resourceProvider.getString(R.string.score_per_min),
                rightBottom = soloMatches.scorePerMatch.getStringFormat(),
                rightBottomTitle = resourceProvider.getString(R.string.score_per_match),
            )
        )

        list.add(HomeBodyHeaderViewModel(resourceProvider.getString(R.string.other)))

        list.add(
            HomeBodyStatsShortViewModel(
                leftTop = soloMatches.killsPerMin.getStringFormat(),
                leftTopTitle = resourceProvider.getString(R.string.kills_per_min),
                rightTop = soloMatches.killsPerMatch.getStringFormat(),
                rightTopTitle = resourceProvider.getString(R.string.kills_per_match),
                leftBottom = soloMatches.minutesPlayed.getStringFormat(),
                leftBottomTitle = resourceProvider.getString(R.string.minutes_played),
                rightBottom = soloMatches.playersOutlived.getStringFormat(),
                rightBottomTitle = resourceProvider.getString(R.string.players_outlived),
            )
        )

        return list
    }

    private fun getDuoMatchesBodyStats(
        soloMatches: DuoMatches,
    ): List<HomeBodyStats> {
        val list = mutableListOf<HomeBodyStats>()

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
                rightBottom = soloMatches.lastModified.getDateZFull(DATE_STATS_FULL),
                rightBottomTitle = resourceProvider.getString(R.string.last_modified)
            )
        )

        list.add(HomeBodyHeaderViewModel(resourceProvider.getString(R.string.score)))

        list.add(
            HomeBodyStatsShortViewModel(
                leftTop = soloMatches.score.getStringFormat(),
                leftTopTitle = resourceProvider.getString(R.string.score),
                rightTop = soloMatches.getAvgScore().getStringFormat(),
                rightTopTitle = resourceProvider.getString(R.string.avg_score),
                leftBottom = soloMatches.scorePerMin.getStringFormat(),
                leftBottomTitle = resourceProvider.getString(R.string.score_per_min),
                rightBottom = soloMatches.scorePerMatch.getStringFormat(),
                rightBottomTitle = resourceProvider.getString(R.string.score_per_match),
            )
        )

        list.add(HomeBodyHeaderViewModel(resourceProvider.getString(R.string.other)))

        list.add(
            HomeBodyStatsShortViewModel(
                leftTop = soloMatches.killsPerMin.getStringFormat(),
                leftTopTitle = resourceProvider.getString(R.string.kills_per_min),
                rightTop = soloMatches.killsPerMatch.getStringFormat(),
                rightTopTitle = resourceProvider.getString(R.string.kills_per_match),
                leftBottom = soloMatches.minutesPlayed.getStringFormat(),
                leftBottomTitle = resourceProvider.getString(R.string.minutes_played),
                rightBottom = soloMatches.playersOutlived.getStringFormat(),
                rightBottomTitle = resourceProvider.getString(R.string.players_outlived),
            )
        )

        return list
    }

    private fun getThreeMatchesBodyStats(
        trioMatches: TrioMatches,
    ): List<HomeBodyStats> {
        val list = mutableListOf<HomeBodyStats>()

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
                rightBottom = trioMatches.lastModified.getDateZFull(DATE_STATS_FULL),
                rightBottomTitle = resourceProvider.getString(R.string.last_modified)
            )
        )

        list.add(HomeBodyHeaderViewModel(resourceProvider.getString(R.string.score)))

        list.add(
            HomeBodyStatsShortViewModel(
                leftTop = trioMatches.score.getStringFormat(),
                leftTopTitle = resourceProvider.getString(R.string.score),
                rightTop = trioMatches.getAvgScore().getStringFormat(),
                rightTopTitle = resourceProvider.getString(R.string.avg_score),
                leftBottom = trioMatches.scorePerMin.getStringFormat(),
                leftBottomTitle = resourceProvider.getString(R.string.score_per_min),
                rightBottom = trioMatches.scorePerMatch.getStringFormat(),
                rightBottomTitle = resourceProvider.getString(R.string.score_per_match),
            )
        )

        list.add(HomeBodyHeaderViewModel(resourceProvider.getString(R.string.other)))

        list.add(
            HomeBodyStatsShortViewModel(
                leftTop = trioMatches.killsPerMin.getStringFormat(),
                leftTopTitle = resourceProvider.getString(R.string.kills_per_min),
                rightTop = trioMatches.killsPerMatch.getStringFormat(),
                rightTopTitle = resourceProvider.getString(R.string.kills_per_match),
                leftBottom = trioMatches.minutesPlayed.getStringFormat(),
                leftBottomTitle = resourceProvider.getString(R.string.minutes_played),
                rightBottom = trioMatches.playersOutlived.getStringFormat(),
                rightBottomTitle = resourceProvider.getString(R.string.players_outlived),
            )
        )

        return list
    }

    private fun getLtmMatchesBodyStats(
        ltm: Ltm
    ): List<HomeBodyStats> {
        val list = mutableListOf<HomeBodyStats>()

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

        list.add(
            HomeBodyStatsSmallViewModel(
                leftTop = ltm.wins.getStringFormat(),
                leftTopTitle = resourceProvider.getString(R.string.top_one),
                rightTop = ltm.lastModified.getDateZFull(DATE_STATS_FULL),
                rightTopTitle = resourceProvider.getString(R.string.last_modified)
            )
        )

        list.add(HomeBodyHeaderViewModel(resourceProvider.getString(R.string.score)))

        list.add(
            HomeBodyStatsShortViewModel(
                leftTop = ltm.score.getStringFormat(),
                leftTopTitle = resourceProvider.getString(R.string.score),
                rightTop = ltm.getAvgScore().getStringFormat(),
                rightTopTitle = resourceProvider.getString(R.string.avg_score),
                leftBottom = ltm.scorePerMin.getStringFormat(),
                leftBottomTitle = resourceProvider.getString(R.string.score_per_min),
                rightBottom = ltm.scorePerMatch.getStringFormat(),
                rightBottomTitle = resourceProvider.getString(R.string.score_per_match),
            )
        )

        list.add(HomeBodyHeaderViewModel(resourceProvider.getString(R.string.other)))

        list.add(
            HomeBodyStatsShortViewModel(
                leftTop = ltm.killsPerMin.getStringFormat(),
                leftTopTitle = resourceProvider.getString(R.string.kills_per_min),
                rightTop = ltm.killsPerMatch.getStringFormat(),
                rightTopTitle = resourceProvider.getString(R.string.kills_per_match),
                leftBottom = ltm.minutesPlayed.getStringFormat(),
                leftBottomTitle = resourceProvider.getString(R.string.minutes_played),
                rightBottom = ltm.playersOutlived.getStringFormat(),
                rightBottomTitle = resourceProvider.getString(R.string.players_outlived),
            )
        )

        return list
    }
}
