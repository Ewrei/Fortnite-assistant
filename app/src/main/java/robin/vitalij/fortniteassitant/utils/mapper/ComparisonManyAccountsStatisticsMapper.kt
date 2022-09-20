package robin.vitalij.fortniteassitant.utils.mapper

import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.model.comparison.ManyPlayerSchedule
import robin.vitalij.fortniteassitant.model.comparison.PlayerModel
import robin.vitalij.fortniteassitant.model.enums.BattlesType
import robin.vitalij.fortniteassitant.model.enums.GameType
import robin.vitalij.fortniteassitant.model.enums.TypeParameter
import robin.vitalij.fortniteassitant.model.network.stats.*
import robin.vitalij.fortniteassitant.ui.comparison.selected.manyaccount.statistics.adapter.viewmodel.ComparisonManyPlayers
import robin.vitalij.fortniteassitant.ui.comparison.selected.manyaccount.statistics.adapter.viewmodel.ComparisonManyPlayersHeaderViewModel
import robin.vitalij.fortniteassitant.ui.comparison.selected.manyaccount.statistics.adapter.viewmodel.ComparisonManyPlayersScheduleViewModel
import robin.vitalij.fortniteassitant.utils.mapper.base.Mapper
import robin.vitalij.fortniteassitant.utils.ResourceProvider

class ComparisonManyAccountsStatisticsMapper(
    private val resourceProvider: ResourceProvider,
    private val battlesType: BattlesType,
    private val gameType: GameType
) : Mapper<List<PlayerModel>, List<ComparisonManyPlayers>> {

    override fun transform(obj: List<PlayerModel>): List<ComparisonManyPlayers> {
        val users = mutableListOf<UserCompary>()

        obj.forEach {
            users.add(
                UserCompary(
                    it.userEntity.name,
                    when (gameType) {
                        GameType.ALL -> {
                            it.userEntity.all
                        }
                        GameType.TOUCH -> {
                            it.userEntity.touch
                        }
                        GameType.GAMEPAD -> {
                            it.userEntity.gamepad
                        }
                        GameType.KEYBOARD_MOUSE -> {
                            it.userEntity.keyboardMouse
                        }
                    }
                )
            )
        }

        val list = mutableListOf<ComparisonManyPlayers>()

        when (battlesType) {
            BattlesType.OVERALL -> {
                list.addAll(getOverallMatches(users))
            }
            BattlesType.SOLO -> {
                list.addAll(getOverallMatches(users))
            }
            BattlesType.DUO -> {
                list.addAll(getOverallMatches(users))
            }
            BattlesType.TRIO -> {
                list.addAll(getOverallMatches(users))
            }
            BattlesType.SQUAD -> {
                list.addAll(getOverallMatches(users))
            }
            BattlesType.LTM -> {
                list.addAll(getOverallMatches(users))
            }
        }

        return list
    }

    private fun getOverallMatches(
        statisticUsers: List<UserCompary>
    ): List<ComparisonManyPlayers> {
        val list = mutableListOf<ComparisonManyPlayers>()

        list.add(
            ComparisonManyPlayersScheduleViewModel(
                title = resourceProvider.getString(R.string.matches),
                manyPlayers = getManyPlayerSchedules(statisticUsers, TypeParameter.MATCHES)
            )
        )

        list.add(
            ComparisonManyPlayersScheduleViewModel(
                title = resourceProvider.getString(R.string.wins),
                manyPlayers = getManyPlayerSchedules(statisticUsers, TypeParameter.WINS)
            )
        )

        list.add(
            ComparisonManyPlayersScheduleViewModel(
                title = resourceProvider.getString(R.string.wins_percent),
                manyPlayers = getManyPlayerSchedules(statisticUsers, TypeParameter.WIN_RATE)
            )
        )

        list.add(
            ComparisonManyPlayersScheduleViewModel(
                title = resourceProvider.getString(R.string.kd),
                manyPlayers = getManyPlayerSchedules(statisticUsers, TypeParameter.KD)
            )
        )

        list.add(
            ComparisonManyPlayersScheduleViewModel(
                title = resourceProvider.getString(R.string.kills),
                manyPlayers = getManyPlayerSchedules(statisticUsers, TypeParameter.KILLS)
            )
        )

        list.add(
            ComparisonManyPlayersScheduleViewModel(
                title = resourceProvider.getString(R.string.deaths),
                manyPlayers = getManyPlayerSchedules(statisticUsers, TypeParameter.DEATHS)
            )
        )

        list.add(
            ComparisonManyPlayersScheduleViewModel(
                title = resourceProvider.getString(R.string.killsPerMin),
                manyPlayers = getManyPlayerSchedules(statisticUsers, TypeParameter.KILLS_PER_MIN)
            )
        )

        list.add(
            ComparisonManyPlayersScheduleViewModel(
                title = resourceProvider.getString(R.string.killsPerMatch),
                manyPlayers = getManyPlayerSchedules(statisticUsers, TypeParameter.KILLS_PER_MATCH)
            )
        )

        list.add(ComparisonManyPlayersHeaderViewModel(resourceProvider.getString(R.string.tops)))

        list.add(
            ComparisonManyPlayersScheduleViewModel(
                title = resourceProvider.getString(R.string.top_one),
                manyPlayers = getManyPlayerSchedules(statisticUsers, TypeParameter.WINS)
            )
        )

        if (battlesType == BattlesType.OVERALL || battlesType == BattlesType.TRIO || battlesType == BattlesType.SQUAD) {
            list.add(
                ComparisonManyPlayersScheduleViewModel(
                    title = resourceProvider.getString(R.string.top_three),
                    manyPlayers = getManyPlayerSchedules(statisticUsers, TypeParameter.TOP_3)
                )
            )
        }

        if (battlesType == BattlesType.OVERALL || battlesType == BattlesType.DUO) {
            list.add(
                ComparisonManyPlayersScheduleViewModel(
                    title = resourceProvider.getString(R.string.top_five),
                    manyPlayers = getManyPlayerSchedules(statisticUsers, TypeParameter.TOP_5)
                )
            )
        }

        if (battlesType == BattlesType.OVERALL || battlesType == BattlesType.TRIO || battlesType == BattlesType.SQUAD) {
            list.add(
                ComparisonManyPlayersScheduleViewModel(
                    title = resourceProvider.getString(R.string.top_six),
                    manyPlayers = getManyPlayerSchedules(statisticUsers, TypeParameter.TOP_6)
                )
            )
        }

        if (battlesType == BattlesType.OVERALL || battlesType == BattlesType.SOLO) {
            list.add(
                ComparisonManyPlayersScheduleViewModel(
                    title = resourceProvider.getString(R.string.top_ten),
                    manyPlayers = getManyPlayerSchedules(statisticUsers, TypeParameter.TOP_10)
                )
            )
        }

        if (battlesType == BattlesType.OVERALL || battlesType == BattlesType.DUO) {
            list.add(
                ComparisonManyPlayersScheduleViewModel(
                    title = resourceProvider.getString(R.string.top_twelve),
                    manyPlayers = getManyPlayerSchedules(statisticUsers, TypeParameter.TOP_12)
                )
            )
        }

        if (battlesType == BattlesType.OVERALL || battlesType == BattlesType.SOLO) {
            list.add(
                ComparisonManyPlayersScheduleViewModel(
                    title = resourceProvider.getString(R.string.top_twenty_five),
                    manyPlayers = getManyPlayerSchedules(statisticUsers, TypeParameter.TOP_25)
                )
            )
        }

        list.add(ComparisonManyPlayersHeaderViewModel(resourceProvider.getString(R.string.score)))

        list.add(
            ComparisonManyPlayersScheduleViewModel(
                title = resourceProvider.getString(R.string.score),
                manyPlayers = getManyPlayerSchedules(statisticUsers, TypeParameter.SCORE)
            )
        )

        list.add(
            ComparisonManyPlayersScheduleViewModel(
                title = resourceProvider.getString(R.string.avg_score),
                manyPlayers = getManyPlayerSchedules(statisticUsers, TypeParameter.AVG_SCORE)
            )
        )

        list.add(
            ComparisonManyPlayersScheduleViewModel(
                title = resourceProvider.getString(R.string.score_per_min),
                manyPlayers = getManyPlayerSchedules(statisticUsers, TypeParameter.SCORE_PER_MIN)
            )
        )

        list.add(
            ComparisonManyPlayersScheduleViewModel(
                title = resourceProvider.getString(R.string.score_per_match),
                manyPlayers = getManyPlayerSchedules(statisticUsers, TypeParameter.SCORE_PER_MATCH)
            )
        )

        list.add(
            ComparisonManyPlayersScheduleViewModel(
                title = resourceProvider.getString(R.string.minutes_played),
                manyPlayers = getManyPlayerSchedules(statisticUsers, TypeParameter.MINUTES_PLAYED)
            )
        )

        list.add(
            ComparisonManyPlayersScheduleViewModel(
                title = resourceProvider.getString(R.string.players_outlived),
                manyPlayers = getManyPlayerSchedules(
                    statisticUsers,
                    TypeParameter.PLAYERS_OUT_LIVED
                )
            )
        )

        return list
    }

    fun getManyPlayerSchedules(
        statisticUsers: List<UserCompary>,
        typeParameter: TypeParameter
    ): List<ManyPlayerSchedule> {
        val list = mutableListOf<ManyPlayerSchedule>()

        statisticUsers.forEach {
            list.add(
                ManyPlayerSchedule(
                    it.name,
                    getValue(typeParameter, it)
                )
            )
        }
        return list
    }

    fun getValue(typeParameter: TypeParameter, userCompary: UserCompary): Double {
        when (battlesType) {
            BattlesType.OVERALL -> {
                return getValue(typeParameter, userCompary.statsTypeDevice?.overall)
            }
            BattlesType.SOLO -> {
                return getValue(typeParameter, userCompary.statsTypeDevice?.solo)
            }
            BattlesType.DUO -> {
                return getValue(typeParameter, userCompary.statsTypeDevice?.duo)
            }
            BattlesType.TRIO -> {
                return getValue(typeParameter, userCompary.statsTypeDevice?.trio)
            }
            BattlesType.SQUAD -> {
                return getValue(typeParameter, userCompary.statsTypeDevice?.squad)
            }
            BattlesType.LTM -> {
                return getValue(
                    typeParameter, userCompary.statsTypeDevice?.ltm
                )
            }
        }
    }

    fun getValue(typeParameter: TypeParameter, userEntity: Overall?): Double {
        return when (typeParameter) {
            TypeParameter.SCORE -> userEntity?.score?.toDouble() ?: 0.0
            TypeParameter.AVG_SCORE -> userEntity?.getAvgScore() ?: 0.0
            TypeParameter.SCORE_PER_MIN -> userEntity?.scorePerMin ?: 0.0
            TypeParameter.SCORE_PER_MATCH -> userEntity?.scorePerMatch ?: 0.0
            TypeParameter.WINS -> userEntity?.wins?.toDouble() ?: 0.0
            TypeParameter.TOP_3 -> userEntity?.top3?.toDouble() ?: 0.0
            TypeParameter.TOP_5 -> userEntity?.top5?.toDouble() ?: 0.0
            TypeParameter.TOP_6 -> userEntity?.top6?.toDouble() ?: 0.0
            TypeParameter.TOP_10 -> userEntity?.top10?.toDouble() ?: 0.0
            TypeParameter.TOP_12 -> userEntity?.top12?.toDouble() ?: 0.0
            TypeParameter.TOP_25 -> userEntity?.top25?.toDouble() ?: 0.0
            TypeParameter.KILLS -> userEntity?.kills?.toDouble() ?: 0.0
            TypeParameter.KILLS_PER_MIN -> userEntity?.killsPerMin ?: 0.0
            TypeParameter.KILLS_PER_MATCH -> userEntity?.killsPerMatch ?: 0.0
            TypeParameter.DEATHS -> userEntity?.deaths?.toDouble() ?: 0.0
            TypeParameter.KD -> userEntity?.kd ?: 0.0
            TypeParameter.MATCHES -> userEntity?.matches?.toDouble() ?: 0.0
            TypeParameter.WIN_RATE -> userEntity?.winRate ?: 0.0
            TypeParameter.MINUTES_PLAYED -> userEntity?.minutesPlayed?.toDouble() ?: 0.0
            TypeParameter.PLAYERS_OUT_LIVED -> userEntity?.playersOutlived?.toDouble() ?: 0.0
        }
    }

    fun getValue(typeParameter: TypeParameter, userEntity: SoloMatches?): Double {
        return when (typeParameter) {
            TypeParameter.SCORE -> userEntity?.score?.toDouble() ?: 0.0
            TypeParameter.AVG_SCORE -> userEntity?.getAvgScore() ?: 0.0
            TypeParameter.SCORE_PER_MIN -> userEntity?.scorePerMin ?: 0.0
            TypeParameter.SCORE_PER_MATCH -> userEntity?.scorePerMatch ?: 0.0
            TypeParameter.WINS -> userEntity?.wins?.toDouble() ?: 0.0
            TypeParameter.TOP_10 -> userEntity?.top10?.toDouble() ?: 0.0
            TypeParameter.TOP_25 -> userEntity?.top25?.toDouble() ?: 0.0
            TypeParameter.KILLS -> userEntity?.kills?.toDouble() ?: 0.0
            TypeParameter.KILLS_PER_MIN -> userEntity?.killsPerMin ?: 0.0
            TypeParameter.KILLS_PER_MATCH -> userEntity?.killsPerMatch ?: 0.0
            TypeParameter.DEATHS -> userEntity?.deaths?.toDouble() ?: 0.0
            TypeParameter.KD -> userEntity?.kd ?: 0.0
            TypeParameter.MATCHES -> userEntity?.matches?.toDouble() ?: 0.0
            TypeParameter.WIN_RATE -> userEntity?.winRate ?: 0.0
            TypeParameter.MINUTES_PLAYED -> userEntity?.minutesPlayed?.toDouble() ?: 0.0
            TypeParameter.PLAYERS_OUT_LIVED -> userEntity?.playersOutlived?.toDouble() ?: 0.0
            else -> 0.0
        }
    }

    fun getValue(typeParameter: TypeParameter, userEntity: DuoMatches?): Double {
        return when (typeParameter) {
            TypeParameter.SCORE -> userEntity?.score?.toDouble() ?: 0.0
            TypeParameter.AVG_SCORE -> userEntity?.getAvgScore() ?: 0.0
            TypeParameter.SCORE_PER_MIN -> userEntity?.scorePerMin ?: 0.0
            TypeParameter.SCORE_PER_MATCH -> userEntity?.scorePerMatch ?: 0.0
            TypeParameter.WINS -> userEntity?.wins?.toDouble() ?: 0.0
            TypeParameter.TOP_5 -> userEntity?.top5?.toDouble() ?: 0.0
            TypeParameter.TOP_12 -> userEntity?.top12?.toDouble() ?: 0.0
            TypeParameter.KILLS -> userEntity?.kills?.toDouble() ?: 0.0
            TypeParameter.KILLS_PER_MIN -> userEntity?.killsPerMin ?: 0.0
            TypeParameter.KILLS_PER_MATCH -> userEntity?.killsPerMatch ?: 0.0
            TypeParameter.DEATHS -> userEntity?.deaths?.toDouble() ?: 0.0
            TypeParameter.KD -> userEntity?.kd ?: 0.0
            TypeParameter.MATCHES -> userEntity?.matches?.toDouble() ?: 0.0
            TypeParameter.WIN_RATE -> userEntity?.winRate ?: 0.0
            TypeParameter.MINUTES_PLAYED -> userEntity?.minutesPlayed?.toDouble() ?: 0.0
            TypeParameter.PLAYERS_OUT_LIVED -> userEntity?.playersOutlived?.toDouble() ?: 0.0
            else -> 0.0
        }
    }

    fun getValue(typeParameter: TypeParameter, userEntity: TrioMatches?): Double {
        return when (typeParameter) {
            TypeParameter.SCORE -> userEntity?.score?.toDouble() ?: 0.0
            TypeParameter.AVG_SCORE -> userEntity?.getAvgScore() ?: 0.0
            TypeParameter.SCORE_PER_MIN -> userEntity?.scorePerMin ?: 0.0
            TypeParameter.SCORE_PER_MATCH -> userEntity?.scorePerMatch ?: 0.0
            TypeParameter.WINS -> userEntity?.wins?.toDouble() ?: 0.0
            TypeParameter.TOP_3 -> userEntity?.top3?.toDouble() ?: 0.0
            TypeParameter.TOP_6 -> userEntity?.top6?.toDouble() ?: 0.0
            TypeParameter.KILLS -> userEntity?.kills?.toDouble() ?: 0.0
            TypeParameter.KILLS_PER_MIN -> userEntity?.killsPerMin ?: 0.0
            TypeParameter.KILLS_PER_MATCH -> userEntity?.killsPerMatch ?: 0.0
            TypeParameter.DEATHS -> userEntity?.deaths?.toDouble() ?: 0.0
            TypeParameter.KD -> userEntity?.kd ?: 0.0
            TypeParameter.MATCHES -> userEntity?.matches?.toDouble() ?: 0.0
            TypeParameter.WIN_RATE -> userEntity?.winRate ?: 0.0
            TypeParameter.MINUTES_PLAYED -> userEntity?.minutesPlayed?.toDouble() ?: 0.0
            TypeParameter.PLAYERS_OUT_LIVED -> userEntity?.playersOutlived?.toDouble() ?: 0.0
            else -> 0.0
        }
    }

    fun getValue(typeParameter: TypeParameter, userEntity: Ltm?): Double {
        return when (typeParameter) {
            TypeParameter.SCORE -> userEntity?.score?.toDouble() ?: 0.0
            TypeParameter.AVG_SCORE -> userEntity?.getAvgScore() ?: 0.0
            TypeParameter.SCORE_PER_MIN -> userEntity?.scorePerMin ?: 0.0
            TypeParameter.SCORE_PER_MATCH -> userEntity?.scorePerMatch ?: 0.0
            TypeParameter.WINS -> userEntity?.wins?.toDouble() ?: 0.0
            TypeParameter.KILLS -> userEntity?.kills?.toDouble() ?: 0.0
            TypeParameter.KILLS_PER_MIN -> userEntity?.killsPerMin ?: 0.0
            TypeParameter.KILLS_PER_MATCH -> userEntity?.killsPerMatch ?: 0.0
            TypeParameter.DEATHS -> userEntity?.deaths?.toDouble() ?: 0.0
            TypeParameter.KD -> userEntity?.kd ?: 0.0
            TypeParameter.MATCHES -> userEntity?.matches?.toDouble() ?: 0.0
            TypeParameter.WIN_RATE -> userEntity?.winRate ?: 0.0
            TypeParameter.MINUTES_PLAYED -> userEntity?.minutesPlayed?.toDouble() ?: 0.0
            TypeParameter.PLAYERS_OUT_LIVED -> userEntity?.playersOutlived?.toDouble() ?: 0.0
            else -> 0.0
        }
    }

    data class UserCompary(val name: String, val statsTypeDevice: StatsTypeDevice?)
}