package robin.vitalij.fortniteassitant.utils.mapper

import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.model.comparison.ComparisonProfileResponse
import robin.vitalij.fortniteassitant.model.enums.BattlesType
import robin.vitalij.fortniteassitant.model.enums.GameType
import robin.vitalij.fortniteassitant.model.network.stats.*
import robin.vitalij.fortniteassitant.ui.comparison.statistics.adapter.viewmodel.ComparisonPlayer
import robin.vitalij.fortniteassitant.ui.comparison.statistics.adapter.viewmodel.ComparisonScheduleViewModel
import robin.vitalij.fortniteassitant.ui.comparison.statistics.adapter.viewmodel.ComparisonStatisticsHeaderViewModel
import robin.vitalij.fortniteassitant.ui.comparison.statistics.adapter.viewmodel.ComparisonStatisticsViewModel
import robin.vitalij.fortniteassitant.utils.mapper.base.Mapper
import robin.vitalij.fortniteassitant.utils.view.ResourceProvider

class ComparisonPlayersMapper(
    private val resourceProvider: ResourceProvider,
    private val isSchedule: Boolean,
    private val battlesType: BattlesType,
    private val gameType: GameType
) : Mapper<ComparisonProfileResponse, List<ComparisonPlayer>> {

    override fun transform(obj: ComparisonProfileResponse): List<ComparisonPlayer> {
        val list = arrayListOf<ComparisonPlayer>()

        if (gameType == GameType.ALL) {
            if (isSchedule) {
                list.addAll(
                    getSchedule(
                        obj.playerModel.userEntity.all,
                        obj.playerTwoModel.userEntity.all,
                        obj.playerModel.userEntity.name,
                        obj.playerTwoModel.userEntity.name
                    )
                )
            } else {
                list.addAll(
                    getStatistics(
                        obj.playerModel.userEntity.all,
                        obj.playerTwoModel.userEntity.all
                    )
                )
            }
        } else if (gameType == GameType.TOUCH) {
            if (isSchedule) {
                list.addAll(
                    getSchedule(
                        obj.playerModel.userEntity.touch,
                        obj.playerTwoModel.userEntity.touch,
                        obj.playerModel.userEntity.name,
                        obj.playerTwoModel.userEntity.name
                    )
                )
            } else {
                list.addAll(
                    getStatistics(
                        obj.playerModel.userEntity.touch,
                        obj.playerTwoModel.userEntity.touch
                    )
                )
            }

        } else if (gameType == GameType.GAMEPAD) {
            if (isSchedule) {
                list.addAll(
                    getSchedule(
                        obj.playerModel.userEntity.gamepad,
                        obj.playerTwoModel.userEntity.gamepad,
                        obj.playerModel.userEntity.name,
                        obj.playerTwoModel.userEntity.name
                    )
                )
            } else {
                list.addAll(
                    getStatistics(
                        obj.playerModel.userEntity.gamepad,
                        obj.playerTwoModel.userEntity.gamepad
                    )
                )
            }

        } else if (gameType == GameType.KEYBOARD_MOUSE) {
            if (isSchedule) {
                list.addAll(
                    getSchedule(
                        obj.playerModel.userEntity.keyboardMouse,
                        obj.playerTwoModel.userEntity.keyboardMouse,
                        obj.playerModel.userEntity.name,
                        obj.playerTwoModel.userEntity.name
                    )
                )
            } else {
                list.addAll(
                    getStatistics(
                        obj.playerModel.userEntity.keyboardMouse,
                        obj.playerTwoModel.userEntity.keyboardMouse
                    )
                )
            }

        }
        return list
    }

    private fun getStatistics(
        statsTypeDeviceOne: StatsTypeDevice?,
        statsTypeDeviceTwo: StatsTypeDevice?
    ): List<ComparisonPlayer> {
        val list = arrayListOf<ComparisonPlayer>()

        if (battlesType == BattlesType.OVERALL) {
            list.addAll(
                getOverallStatistics(
                    statsTypeDeviceOne?.overall,
                    statsTypeDeviceTwo?.overall
                )
            )
        } else if (battlesType == BattlesType.SOLO) {
            list.addAll(
                getOverallStatistics(
                    statsTypeDeviceOne?.solo,
                    statsTypeDeviceTwo?.solo
                )
            )
        } else if (battlesType == BattlesType.DUO) {
            list.addAll(
                getOverallStatistics(
                    statsTypeDeviceOne?.duo,
                    statsTypeDeviceTwo?.duo
                )
            )
        } else if (battlesType == BattlesType.TRIO) {
            list.addAll(
                getOverallStatistics(
                    statsTypeDeviceOne?.trio,
                    statsTypeDeviceTwo?.trio
                )
            )
        } else if (battlesType == BattlesType.SQUAD) {
            list.addAll(
                getOverallStatistics(
                    statsTypeDeviceOne?.squad,
                    statsTypeDeviceTwo?.squad
                )
            )
        } else if (battlesType == BattlesType.LTM) {
            list.addAll(
                getOverallStatistics(
                    statsTypeDeviceOne?.ltm,
                    statsTypeDeviceTwo?.ltm
                )
            )
        }

        return list
    }

    private fun getSchedule(
        statsTypeDeviceOne: StatsTypeDevice?,
        statsTypeDeviceTwo: StatsTypeDevice?,
        nameOne: String,
        nameTwo: String
    ): List<ComparisonPlayer> {
        val list = arrayListOf<ComparisonPlayer>()

        if (battlesType == BattlesType.OVERALL) {
            list.addAll(
                getOverallSchedule(
                    statsTypeDeviceOne?.overall,
                    statsTypeDeviceTwo?.overall,
                    nameOne,
                    nameTwo
                )
            )
        } else if (battlesType == BattlesType.SOLO) {
            list.addAll(
                getOverallSchedule(
                    statsTypeDeviceOne?.solo,
                    statsTypeDeviceTwo?.solo,
                    nameOne,
                    nameTwo
                )
            )
        } else if (battlesType == BattlesType.DUO) {
            list.addAll(
                getOverallSchedule(
                    statsTypeDeviceOne?.duo,
                    statsTypeDeviceTwo?.duo,
                    nameOne,
                    nameTwo
                )
            )
        } else if (battlesType == BattlesType.TRIO) {
            list.addAll(
                getOverallSchedule(
                    statsTypeDeviceOne?.trio,
                    statsTypeDeviceTwo?.trio,
                    nameOne,
                    nameTwo
                )
            )
        } else if (battlesType == BattlesType.SQUAD) {
            list.addAll(
                getOverallSchedule(
                    statsTypeDeviceOne?.squad,
                    statsTypeDeviceTwo?.squad,
                    nameOne,
                    nameTwo
                )
            )
        } else if (battlesType == BattlesType.LTM) {
            list.addAll(
                getOverallSchedule(
                    statsTypeDeviceOne?.ltm,
                    statsTypeDeviceTwo?.ltm,
                    nameOne,
                    nameTwo
                )
            )
        }

        return list
    }

    private fun getOverallStatistics(
        playerOne: Overall?,
        playerTwo: Overall?
    ): List<ComparisonPlayer> {
        val list = arrayListOf<ComparisonPlayer>()

        if (playerOne == null && playerTwo == null) {
            list.add(ComparisonStatisticsHeaderViewModel(resourceProvider.getString(R.string.players_did_not_participate_in_battles)))
        } else {
            list.add(
                ComparisonStatisticsViewModel(
                    title = resourceProvider.getString(R.string.matches),
                    value = playerOne?.matches?.toDouble() ?: 0.0,
                    valueTwo = playerTwo?.matches?.toDouble() ?: 0.0
                )
            )

            list.add(
                ComparisonStatisticsViewModel(
                    title = resourceProvider.getString(R.string.wins),
                    value = playerOne?.wins?.toDouble() ?: 0.0,
                    valueTwo = playerTwo?.wins?.toDouble() ?: 0.0
                )
            )

            list.add(
                ComparisonStatisticsViewModel(
                    title = resourceProvider.getString(R.string.wins_percent),
                    value = playerOne?.winRate?.toDouble() ?: 0.0,
                    valueTwo = playerTwo?.winRate?.toDouble() ?: 0.0
                )
            )

            list.add(
                ComparisonStatisticsViewModel(
                    title = resourceProvider.getString(R.string.kd),
                    value = playerOne?.kd ?: 0.0,
                    valueTwo = playerTwo?.kd ?: 0.0
                )
            )

            list.add(
                ComparisonStatisticsViewModel(
                    title = resourceProvider.getString(R.string.kills),
                    value = playerOne?.kills?.toDouble() ?: 0.0,
                    valueTwo = playerTwo?.kills?.toDouble() ?: 0.0
                )
            )

            list.add(
                ComparisonStatisticsViewModel(
                    title = resourceProvider.getString(R.string.deaths),
                    value = playerOne?.deaths?.toDouble() ?: 0.0,
                    valueTwo = playerTwo?.deaths?.toDouble() ?: 0.0
                )
            )

            list.add(
                ComparisonStatisticsViewModel(
                    title = resourceProvider.getString(R.string.killsPerMin),
                    value = playerOne?.killsPerMin?.toDouble() ?: 0.0,
                    valueTwo = playerTwo?.killsPerMin?.toDouble() ?: 0.0
                )
            )

            list.add(
                ComparisonStatisticsViewModel(
                    title = resourceProvider.getString(R.string.killsPerMatch),
                    value = playerOne?.killsPerMatch?.toDouble() ?: 0.0,
                    valueTwo = playerTwo?.killsPerMatch?.toDouble() ?: 0.0
                )
            )

            list.add(ComparisonStatisticsHeaderViewModel(resourceProvider.getString(R.string.tops)))

            list.add(
                ComparisonStatisticsViewModel(
                    title = resourceProvider.getString(R.string.top_one),
                    value = playerOne?.wins?.toDouble() ?: 0.0,
                    valueTwo = playerTwo?.wins?.toDouble() ?: 0.0
                )
            )

            list.add(
                ComparisonStatisticsViewModel(
                    title = resourceProvider.getString(R.string.top_three),
                    value = playerOne?.top3?.toDouble() ?: 0.0,
                    valueTwo = playerTwo?.top3?.toDouble() ?: 0.0
                )
            )

            list.add(
                ComparisonStatisticsViewModel(
                    title = resourceProvider.getString(R.string.top_five),
                    value = playerOne?.top5?.toDouble() ?: 0.0,
                    valueTwo = playerTwo?.top5?.toDouble() ?: 0.0
                )
            )

            list.add(
                ComparisonStatisticsViewModel(
                    title = resourceProvider.getString(R.string.top_six),
                    value = playerOne?.top6?.toDouble() ?: 0.0,
                    valueTwo = playerTwo?.top6?.toDouble() ?: 0.0
                )
            )

            list.add(
                ComparisonStatisticsViewModel(
                    title = resourceProvider.getString(R.string.top_ten),
                    value = playerOne?.top10?.toDouble() ?: 0.0,
                    valueTwo = playerTwo?.top10?.toDouble() ?: 0.0
                )
            )

            list.add(
                ComparisonStatisticsViewModel(
                    title = resourceProvider.getString(R.string.top_twelve),
                    value = playerOne?.top12?.toDouble() ?: 0.0,
                    valueTwo = playerTwo?.top12?.toDouble() ?: 0.0
                )
            )

            list.add(
                ComparisonStatisticsViewModel(
                    title = resourceProvider.getString(R.string.top_twenty_five),
                    value = playerOne?.top25?.toDouble() ?: 0.0,
                    valueTwo = playerTwo?.top25?.toDouble() ?: 0.0
                )
            )

            list.add(ComparisonStatisticsHeaderViewModel(resourceProvider.getString(R.string.score)))

            list.add(
                ComparisonStatisticsViewModel(
                    title = resourceProvider.getString(R.string.score),
                    value = playerOne?.score?.toDouble() ?: 0.0,
                    valueTwo = playerTwo?.score?.toDouble() ?: 0.0
                )
            )

            list.add(
                ComparisonStatisticsViewModel(
                    title = resourceProvider.getString(R.string.avg_score),
                    value = playerOne?.getAvgScore()?.toDouble() ?: 0.0,
                    valueTwo = playerTwo?.getAvgScore()?.toDouble() ?: 0.0
                )
            )

            list.add(
                ComparisonStatisticsViewModel(
                    title = resourceProvider.getString(R.string.score_per_min),
                    value = playerOne?.scorePerMin?.toDouble() ?: 0.0,
                    valueTwo = playerTwo?.scorePerMin?.toDouble() ?: 0.0
                )
            )

            list.add(
                ComparisonStatisticsViewModel(
                    title = resourceProvider.getString(R.string.score_per_match),
                    value = playerOne?.scorePerMatch?.toDouble() ?: 0.0,
                    valueTwo = playerTwo?.scorePerMatch?.toDouble() ?: 0.0
                )
            )

            list.add(
                ComparisonStatisticsViewModel(
                    title = resourceProvider.getString(R.string.minutes_played),
                    value = playerOne?.minutesPlayed?.toDouble() ?: 0.0,
                    valueTwo = playerTwo?.minutesPlayed?.toDouble() ?: 0.0
                )
            )

            list.add(
                ComparisonStatisticsViewModel(
                    title = resourceProvider.getString(R.string.players_outlived),
                    value = playerOne?.playersOutlived?.toDouble() ?: 0.0,
                    valueTwo = playerTwo?.playersOutlived?.toDouble() ?: 0.0
                )
            )
        }

        return list
    }

    private fun getOverallStatistics(
        playerOne: SoloMatches?,
        playerTwo: SoloMatches?
    ): List<ComparisonPlayer> {
        val list = arrayListOf<ComparisonPlayer>()

        if (playerOne == null && playerTwo == null) {
            list.add(ComparisonStatisticsHeaderViewModel(resourceProvider.getString(R.string.players_did_not_participate_in_battles)))
        } else {
            list.add(
                ComparisonStatisticsViewModel(
                    title = resourceProvider.getString(R.string.matches),
                    value = playerOne?.matches?.toDouble() ?: 0.0,
                    valueTwo = playerTwo?.matches?.toDouble() ?: 0.0
                )
            )

            list.add(
                ComparisonStatisticsViewModel(
                    title = resourceProvider.getString(R.string.wins),
                    value = playerOne?.wins?.toDouble() ?: 0.0,
                    valueTwo = playerTwo?.wins?.toDouble() ?: 0.0
                )
            )

            list.add(
                ComparisonStatisticsViewModel(
                    title = resourceProvider.getString(R.string.wins_percent),
                    value = playerOne?.winRate?.toDouble() ?: 0.0,
                    valueTwo = playerTwo?.winRate?.toDouble() ?: 0.0
                )
            )

            list.add(
                ComparisonStatisticsViewModel(
                    title = resourceProvider.getString(R.string.kd),
                    value = playerOne?.kd ?: 0.0,
                    valueTwo = playerTwo?.kd ?: 0.0
                )
            )

            list.add(
                ComparisonStatisticsViewModel(
                    title = resourceProvider.getString(R.string.kills),
                    value = playerOne?.kills?.toDouble() ?: 0.0,
                    valueTwo = playerTwo?.kills?.toDouble() ?: 0.0
                )
            )

            list.add(
                ComparisonStatisticsViewModel(
                    title = resourceProvider.getString(R.string.deaths),
                    value = playerOne?.deaths?.toDouble() ?: 0.0,
                    valueTwo = playerTwo?.deaths?.toDouble() ?: 0.0
                )
            )

            list.add(
                ComparisonStatisticsViewModel(
                    title = resourceProvider.getString(R.string.killsPerMin),
                    value = playerOne?.killsPerMin?.toDouble() ?: 0.0,
                    valueTwo = playerTwo?.killsPerMin?.toDouble() ?: 0.0
                )
            )

            list.add(
                ComparisonStatisticsViewModel(
                    title = resourceProvider.getString(R.string.killsPerMatch),
                    value = playerOne?.killsPerMatch?.toDouble() ?: 0.0,
                    valueTwo = playerTwo?.killsPerMatch?.toDouble() ?: 0.0
                )
            )

            list.add(ComparisonStatisticsHeaderViewModel(resourceProvider.getString(R.string.tops)))

            list.add(
                ComparisonStatisticsViewModel(
                    title = resourceProvider.getString(R.string.top_one),
                    value = playerOne?.wins?.toDouble() ?: 0.0,
                    valueTwo = playerTwo?.wins?.toDouble() ?: 0.0
                )
            )

            list.add(
                ComparisonStatisticsViewModel(
                    title = resourceProvider.getString(R.string.top_ten),
                    value = playerOne?.top10?.toDouble() ?: 0.0,
                    valueTwo = playerTwo?.top10?.toDouble() ?: 0.0
                )
            )

            list.add(
                ComparisonStatisticsViewModel(
                    title = resourceProvider.getString(R.string.top_twenty_five),
                    value = playerOne?.top25?.toDouble() ?: 0.0,
                    valueTwo = playerTwo?.top25?.toDouble() ?: 0.0
                )
            )

            list.add(ComparisonStatisticsHeaderViewModel(resourceProvider.getString(R.string.score)))

            list.add(
                ComparisonStatisticsViewModel(
                    title = resourceProvider.getString(R.string.score),
                    value = playerOne?.score?.toDouble() ?: 0.0,
                    valueTwo = playerTwo?.score?.toDouble() ?: 0.0
                )
            )

            list.add(
                ComparisonStatisticsViewModel(
                    title = resourceProvider.getString(R.string.avg_score),
                    value = playerOne?.getAvgScore()?.toDouble() ?: 0.0,
                    valueTwo = playerTwo?.getAvgScore()?.toDouble() ?: 0.0
                )
            )

            list.add(
                ComparisonStatisticsViewModel(
                    title = resourceProvider.getString(R.string.score_per_min),
                    value = playerOne?.scorePerMin?.toDouble() ?: 0.0,
                    valueTwo = playerTwo?.scorePerMin?.toDouble() ?: 0.0
                )
            )

            list.add(
                ComparisonStatisticsViewModel(
                    title = resourceProvider.getString(R.string.score_per_match),
                    value = playerOne?.scorePerMatch?.toDouble() ?: 0.0,
                    valueTwo = playerTwo?.scorePerMatch?.toDouble() ?: 0.0
                )
            )

            list.add(
                ComparisonStatisticsViewModel(
                    title = resourceProvider.getString(R.string.minutes_played),
                    value = playerOne?.minutesPlayed?.toDouble() ?: 0.0,
                    valueTwo = playerTwo?.minutesPlayed?.toDouble() ?: 0.0
                )
            )

            list.add(
                ComparisonStatisticsViewModel(
                    title = resourceProvider.getString(R.string.players_outlived),
                    value = playerOne?.playersOutlived?.toDouble() ?: 0.0,
                    valueTwo = playerTwo?.playersOutlived?.toDouble() ?: 0.0
                )
            )
        }

        return list
    }

    private fun getOverallStatistics(
        playerOne: DuoMatches?,
        playerTwo: DuoMatches?
    ): List<ComparisonPlayer> {
        val list = arrayListOf<ComparisonPlayer>()

        if (playerOne == null && playerTwo == null) {
            list.add(ComparisonStatisticsHeaderViewModel(resourceProvider.getString(R.string.players_did_not_participate_in_battles)))
        } else {
            list.add(
                ComparisonStatisticsViewModel(
                    title = resourceProvider.getString(R.string.matches),
                    value = playerOne?.matches?.toDouble() ?: 0.0,
                    valueTwo = playerTwo?.matches?.toDouble() ?: 0.0
                )
            )

            list.add(
                ComparisonStatisticsViewModel(
                    title = resourceProvider.getString(R.string.wins),
                    value = playerOne?.wins?.toDouble() ?: 0.0,
                    valueTwo = playerTwo?.wins?.toDouble() ?: 0.0
                )
            )

            list.add(
                ComparisonStatisticsViewModel(
                    title = resourceProvider.getString(R.string.wins_percent),
                    value = playerOne?.winRate?.toDouble() ?: 0.0,
                    valueTwo = playerTwo?.winRate?.toDouble() ?: 0.0
                )
            )

            list.add(
                ComparisonStatisticsViewModel(
                    title = resourceProvider.getString(R.string.kd),
                    value = playerOne?.kd ?: 0.0,
                    valueTwo = playerTwo?.kd ?: 0.0
                )
            )

            list.add(
                ComparisonStatisticsViewModel(
                    title = resourceProvider.getString(R.string.kills),
                    value = playerOne?.kills?.toDouble() ?: 0.0,
                    valueTwo = playerTwo?.kills?.toDouble() ?: 0.0
                )
            )

            list.add(
                ComparisonStatisticsViewModel(
                    title = resourceProvider.getString(R.string.deaths),
                    value = playerOne?.deaths?.toDouble() ?: 0.0,
                    valueTwo = playerTwo?.deaths?.toDouble() ?: 0.0
                )
            )

            list.add(
                ComparisonStatisticsViewModel(
                    title = resourceProvider.getString(R.string.killsPerMin),
                    value = playerOne?.killsPerMin?.toDouble() ?: 0.0,
                    valueTwo = playerTwo?.killsPerMin?.toDouble() ?: 0.0
                )
            )

            list.add(
                ComparisonStatisticsViewModel(
                    title = resourceProvider.getString(R.string.killsPerMatch),
                    value = playerOne?.killsPerMatch?.toDouble() ?: 0.0,
                    valueTwo = playerTwo?.killsPerMatch?.toDouble() ?: 0.0
                )
            )

            list.add(ComparisonStatisticsHeaderViewModel(resourceProvider.getString(R.string.tops)))

            list.add(
                ComparisonStatisticsViewModel(
                    title = resourceProvider.getString(R.string.top_one),
                    value = playerOne?.wins?.toDouble() ?: 0.0,
                    valueTwo = playerTwo?.wins?.toDouble() ?: 0.0
                )
            )

            list.add(
                ComparisonStatisticsViewModel(
                    title = resourceProvider.getString(R.string.top_five),
                    value = playerOne?.top5?.toDouble() ?: 0.0,
                    valueTwo = playerTwo?.top5?.toDouble() ?: 0.0
                )
            )

            list.add(
                ComparisonStatisticsViewModel(
                    title = resourceProvider.getString(R.string.top_twelve),
                    value = playerOne?.top12?.toDouble() ?: 0.0,
                    valueTwo = playerTwo?.top12?.toDouble() ?: 0.0
                )
            )

            list.add(ComparisonStatisticsHeaderViewModel(resourceProvider.getString(R.string.score)))

            list.add(
                ComparisonStatisticsViewModel(
                    title = resourceProvider.getString(R.string.score),
                    value = playerOne?.score?.toDouble() ?: 0.0,
                    valueTwo = playerTwo?.score?.toDouble() ?: 0.0
                )
            )

            list.add(
                ComparisonStatisticsViewModel(
                    title = resourceProvider.getString(R.string.avg_score),
                    value = playerOne?.getAvgScore()?.toDouble() ?: 0.0,
                    valueTwo = playerTwo?.getAvgScore()?.toDouble() ?: 0.0
                )
            )

            list.add(
                ComparisonStatisticsViewModel(
                    title = resourceProvider.getString(R.string.score_per_min),
                    value = playerOne?.scorePerMin?.toDouble() ?: 0.0,
                    valueTwo = playerTwo?.scorePerMin?.toDouble() ?: 0.0
                )
            )

            list.add(
                ComparisonStatisticsViewModel(
                    title = resourceProvider.getString(R.string.score_per_match),
                    value = playerOne?.scorePerMatch?.toDouble() ?: 0.0,
                    valueTwo = playerTwo?.scorePerMatch?.toDouble() ?: 0.0
                )
            )

            list.add(
                ComparisonStatisticsViewModel(
                    title = resourceProvider.getString(R.string.minutes_played),
                    value = playerOne?.minutesPlayed?.toDouble() ?: 0.0,
                    valueTwo = playerTwo?.minutesPlayed?.toDouble() ?: 0.0
                )
            )

            list.add(
                ComparisonStatisticsViewModel(
                    title = resourceProvider.getString(R.string.players_outlived),
                    value = playerOne?.playersOutlived?.toDouble() ?: 0.0,
                    valueTwo = playerTwo?.playersOutlived?.toDouble() ?: 0.0
                )
            )
        }

        return list
    }

    private fun getOverallStatistics(
        playerOne: TrioMatches?,
        playerTwo: TrioMatches?
    ): List<ComparisonPlayer> {
        val list = arrayListOf<ComparisonPlayer>()

        if (playerOne == null && playerTwo == null) {
            list.add(ComparisonStatisticsHeaderViewModel(resourceProvider.getString(R.string.players_did_not_participate_in_battles)))
        } else {
            list.add(
                ComparisonStatisticsViewModel(
                    title = resourceProvider.getString(R.string.matches),
                    value = playerOne?.matches?.toDouble() ?: 0.0,
                    valueTwo = playerTwo?.matches?.toDouble() ?: 0.0
                )
            )

            list.add(
                ComparisonStatisticsViewModel(
                    title = resourceProvider.getString(R.string.wins),
                    value = playerOne?.wins?.toDouble() ?: 0.0,
                    valueTwo = playerTwo?.wins?.toDouble() ?: 0.0
                )
            )

            list.add(
                ComparisonStatisticsViewModel(
                    title = resourceProvider.getString(R.string.wins_percent),
                    value = playerOne?.winRate?.toDouble() ?: 0.0,
                    valueTwo = playerTwo?.winRate?.toDouble() ?: 0.0
                )
            )

            list.add(
                ComparisonStatisticsViewModel(
                    title = resourceProvider.getString(R.string.kd),
                    value = playerOne?.kd ?: 0.0,
                    valueTwo = playerTwo?.kd ?: 0.0
                )
            )

            list.add(
                ComparisonStatisticsViewModel(
                    title = resourceProvider.getString(R.string.kills),
                    value = playerOne?.kills?.toDouble() ?: 0.0,
                    valueTwo = playerTwo?.kills?.toDouble() ?: 0.0
                )
            )

            list.add(
                ComparisonStatisticsViewModel(
                    title = resourceProvider.getString(R.string.deaths),
                    value = playerOne?.deaths?.toDouble() ?: 0.0,
                    valueTwo = playerTwo?.deaths?.toDouble() ?: 0.0
                )
            )

            list.add(
                ComparisonStatisticsViewModel(
                    title = resourceProvider.getString(R.string.killsPerMin),
                    value = playerOne?.killsPerMin?.toDouble() ?: 0.0,
                    valueTwo = playerTwo?.killsPerMin?.toDouble() ?: 0.0
                )
            )

            list.add(
                ComparisonStatisticsViewModel(
                    title = resourceProvider.getString(R.string.killsPerMatch),
                    value = playerOne?.killsPerMatch?.toDouble() ?: 0.0,
                    valueTwo = playerTwo?.killsPerMatch?.toDouble() ?: 0.0
                )
            )

            list.add(ComparisonStatisticsHeaderViewModel(resourceProvider.getString(R.string.tops)))

            list.add(
                ComparisonStatisticsViewModel(
                    title = resourceProvider.getString(R.string.top_one),
                    value = playerOne?.wins?.toDouble() ?: 0.0,
                    valueTwo = playerTwo?.wins?.toDouble() ?: 0.0
                )
            )

            list.add(
                ComparisonStatisticsViewModel(
                    title = resourceProvider.getString(R.string.top_three),
                    value = playerOne?.top3?.toDouble() ?: 0.0,
                    valueTwo = playerTwo?.top3?.toDouble() ?: 0.0
                )
            )

            list.add(
                ComparisonStatisticsViewModel(
                    title = resourceProvider.getString(R.string.top_six),
                    value = playerOne?.top6?.toDouble() ?: 0.0,
                    valueTwo = playerTwo?.top6?.toDouble() ?: 0.0
                )
            )

            list.add(ComparisonStatisticsHeaderViewModel(resourceProvider.getString(R.string.score)))

            list.add(
                ComparisonStatisticsViewModel(
                    title = resourceProvider.getString(R.string.score),
                    value = playerOne?.score?.toDouble() ?: 0.0,
                    valueTwo = playerTwo?.score?.toDouble() ?: 0.0
                )
            )

            list.add(
                ComparisonStatisticsViewModel(
                    title = resourceProvider.getString(R.string.avg_score),
                    value = playerOne?.getAvgScore()?.toDouble() ?: 0.0,
                    valueTwo = playerTwo?.getAvgScore()?.toDouble() ?: 0.0
                )
            )

            list.add(
                ComparisonStatisticsViewModel(
                    title = resourceProvider.getString(R.string.score_per_min),
                    value = playerOne?.scorePerMin?.toDouble() ?: 0.0,
                    valueTwo = playerTwo?.scorePerMin?.toDouble() ?: 0.0
                )
            )

            list.add(
                ComparisonStatisticsViewModel(
                    title = resourceProvider.getString(R.string.score_per_match),
                    value = playerOne?.scorePerMatch?.toDouble() ?: 0.0,
                    valueTwo = playerTwo?.scorePerMatch?.toDouble() ?: 0.0
                )
            )

            list.add(
                ComparisonStatisticsViewModel(
                    title = resourceProvider.getString(R.string.minutes_played),
                    value = playerOne?.minutesPlayed?.toDouble() ?: 0.0,
                    valueTwo = playerTwo?.minutesPlayed?.toDouble() ?: 0.0
                )
            )

            list.add(
                ComparisonStatisticsViewModel(
                    title = resourceProvider.getString(R.string.players_outlived),
                    value = playerOne?.playersOutlived?.toDouble() ?: 0.0,
                    valueTwo = playerTwo?.playersOutlived?.toDouble() ?: 0.0
                )
            )
        }

        return list
    }

    private fun getOverallStatistics(
        playerOne: Ltm?,
        playerTwo: Ltm?
    ): List<ComparisonPlayer> {
        val list = arrayListOf<ComparisonPlayer>()

        if (playerOne == null && playerTwo == null) {
            list.add(ComparisonStatisticsHeaderViewModel(resourceProvider.getString(R.string.players_did_not_participate_in_battles)))
        } else {
            list.add(
                ComparisonStatisticsViewModel(
                    title = resourceProvider.getString(R.string.matches),
                    value = playerOne?.matches?.toDouble() ?: 0.0,
                    valueTwo = playerTwo?.matches?.toDouble() ?: 0.0
                )
            )

            list.add(
                ComparisonStatisticsViewModel(
                    title = resourceProvider.getString(R.string.wins),
                    value = playerOne?.wins?.toDouble() ?: 0.0,
                    valueTwo = playerTwo?.wins?.toDouble() ?: 0.0
                )
            )

            list.add(
                ComparisonStatisticsViewModel(
                    title = resourceProvider.getString(R.string.wins_percent),
                    value = playerOne?.winRate?.toDouble() ?: 0.0,
                    valueTwo = playerTwo?.winRate?.toDouble() ?: 0.0
                )
            )

            list.add(
                ComparisonStatisticsViewModel(
                    title = resourceProvider.getString(R.string.kd),
                    value = playerOne?.kd ?: 0.0,
                    valueTwo = playerTwo?.kd ?: 0.0
                )
            )

            list.add(
                ComparisonStatisticsViewModel(
                    title = resourceProvider.getString(R.string.kills),
                    value = playerOne?.kills?.toDouble() ?: 0.0,
                    valueTwo = playerTwo?.kills?.toDouble() ?: 0.0
                )
            )

            list.add(
                ComparisonStatisticsViewModel(
                    title = resourceProvider.getString(R.string.deaths),
                    value = playerOne?.deaths?.toDouble() ?: 0.0,
                    valueTwo = playerTwo?.deaths?.toDouble() ?: 0.0
                )
            )

            list.add(
                ComparisonStatisticsViewModel(
                    title = resourceProvider.getString(R.string.killsPerMin),
                    value = playerOne?.killsPerMin?.toDouble() ?: 0.0,
                    valueTwo = playerTwo?.killsPerMin?.toDouble() ?: 0.0
                )
            )

            list.add(
                ComparisonStatisticsViewModel(
                    title = resourceProvider.getString(R.string.killsPerMatch),
                    value = playerOne?.killsPerMatch?.toDouble() ?: 0.0,
                    valueTwo = playerTwo?.killsPerMatch?.toDouble() ?: 0.0
                )
            )

            list.add(ComparisonStatisticsHeaderViewModel(resourceProvider.getString(R.string.score)))

            list.add(
                ComparisonStatisticsViewModel(
                    title = resourceProvider.getString(R.string.score),
                    value = playerOne?.score?.toDouble() ?: 0.0,
                    valueTwo = playerTwo?.score?.toDouble() ?: 0.0
                )
            )

            list.add(
                ComparisonStatisticsViewModel(
                    title = resourceProvider.getString(R.string.avg_score),
                    value = playerOne?.getAvgScore()?.toDouble() ?: 0.0,
                    valueTwo = playerTwo?.getAvgScore()?.toDouble() ?: 0.0
                )
            )

            list.add(
                ComparisonStatisticsViewModel(
                    title = resourceProvider.getString(R.string.score_per_min),
                    value = playerOne?.scorePerMin?.toDouble() ?: 0.0,
                    valueTwo = playerTwo?.scorePerMin?.toDouble() ?: 0.0
                )
            )

            list.add(
                ComparisonStatisticsViewModel(
                    title = resourceProvider.getString(R.string.score_per_match),
                    value = playerOne?.scorePerMatch?.toDouble() ?: 0.0,
                    valueTwo = playerTwo?.scorePerMatch?.toDouble() ?: 0.0
                )
            )

            list.add(
                ComparisonStatisticsViewModel(
                    title = resourceProvider.getString(R.string.minutes_played),
                    value = playerOne?.minutesPlayed?.toDouble() ?: 0.0,
                    valueTwo = playerTwo?.minutesPlayed?.toDouble() ?: 0.0
                )
            )

            list.add(
                ComparisonStatisticsViewModel(
                    title = resourceProvider.getString(R.string.players_outlived),
                    value = playerOne?.playersOutlived?.toDouble() ?: 0.0,
                    valueTwo = playerTwo?.playersOutlived?.toDouble() ?: 0.0
                )
            )
        }

        return list
    }

    private fun getOverallSchedule(
        playerOne: Overall?,
        playerTwo: Overall?,
        nameOne: String,
        nameTwo: String
    ): List<ComparisonPlayer> {
        val list = arrayListOf<ComparisonPlayer>()

        if (playerOne == null && playerTwo == null) {
            list.add(ComparisonStatisticsHeaderViewModel(resourceProvider.getString(R.string.players_did_not_participate_in_battles)))
        } else {
            list.add(
                ComparisonScheduleViewModel(
                    title = resourceProvider.getString(R.string.matches),
                    value = playerOne?.matches?.toDouble() ?: 0.0,
                    valueTwo = playerTwo?.matches?.toDouble() ?: 0.0,
                    nickName = nameOne,
                    nickNameTwo = nameTwo
                )
            )

            list.add(
                ComparisonScheduleViewModel(
                    title = resourceProvider.getString(R.string.wins),
                    value = playerOne?.wins?.toDouble() ?: 0.0,
                    valueTwo = playerTwo?.wins?.toDouble() ?: 0.0,
                    nickName = nameOne,
                    nickNameTwo = nameTwo
                )
            )

            list.add(
                ComparisonScheduleViewModel(
                    title = resourceProvider.getString(R.string.wins_percent),
                    value = playerOne?.winRate?.toDouble() ?: 0.0,
                    valueTwo = playerTwo?.winRate?.toDouble() ?: 0.0,
                    nickName = nameOne,
                    nickNameTwo = nameTwo
                )
            )

            list.add(
                ComparisonScheduleViewModel(
                    title = resourceProvider.getString(R.string.kd),
                    value = playerOne?.kd ?: 0.0,
                    valueTwo = playerTwo?.kd ?: 0.0,
                    nickName = nameOne,
                    nickNameTwo = nameTwo
                )
            )

            list.add(
                ComparisonScheduleViewModel(
                    title = resourceProvider.getString(R.string.kills),
                    value = playerOne?.kills?.toDouble() ?: 0.0,
                    valueTwo = playerTwo?.kills?.toDouble() ?: 0.0,
                    nickName = nameOne,
                    nickNameTwo = nameTwo
                )
            )

            list.add(
                ComparisonScheduleViewModel(
                    title = resourceProvider.getString(R.string.deaths),
                    value = playerOne?.deaths?.toDouble() ?: 0.0,
                    valueTwo = playerTwo?.deaths?.toDouble() ?: 0.0,
                    nickName = nameOne,
                    nickNameTwo = nameTwo
                )
            )

            list.add(
                ComparisonScheduleViewModel(
                    title = resourceProvider.getString(R.string.killsPerMin),
                    value = playerOne?.killsPerMin?.toDouble() ?: 0.0,
                    valueTwo = playerTwo?.killsPerMin?.toDouble() ?: 0.0,
                    nickName = nameOne,
                    nickNameTwo = nameTwo
                )
            )

            list.add(
                ComparisonScheduleViewModel(
                    title = resourceProvider.getString(R.string.killsPerMatch),
                    value = playerOne?.killsPerMatch?.toDouble() ?: 0.0,
                    valueTwo = playerTwo?.killsPerMatch?.toDouble() ?: 0.0,
                    nickName = nameOne,
                    nickNameTwo = nameTwo
                )
            )

            list.add(ComparisonStatisticsHeaderViewModel(resourceProvider.getString(R.string.tops)))

            list.add(
                ComparisonScheduleViewModel(
                    title = resourceProvider.getString(R.string.top_one),
                    value = playerOne?.wins?.toDouble() ?: 0.0,
                    valueTwo = playerTwo?.wins?.toDouble() ?: 0.0,
                    nickName = nameOne,
                    nickNameTwo = nameTwo
                )
            )

            list.add(
                ComparisonScheduleViewModel(
                    title = resourceProvider.getString(R.string.top_three),
                    value = playerOne?.top3?.toDouble() ?: 0.0,
                    valueTwo = playerTwo?.top3?.toDouble() ?: 0.0,
                    nickName = nameOne,
                    nickNameTwo = nameTwo
                )
            )

            list.add(
                ComparisonScheduleViewModel(
                    title = resourceProvider.getString(R.string.top_five),
                    value = playerOne?.top5?.toDouble() ?: 0.0,
                    valueTwo = playerTwo?.top5?.toDouble() ?: 0.0,
                    nickName = nameOne,
                    nickNameTwo = nameTwo
                )
            )

            list.add(
                ComparisonScheduleViewModel(
                    title = resourceProvider.getString(R.string.top_six),
                    value = playerOne?.top6?.toDouble() ?: 0.0,
                    valueTwo = playerTwo?.top6?.toDouble() ?: 0.0,
                    nickName = nameOne,
                    nickNameTwo = nameTwo
                )
            )

            list.add(
                ComparisonScheduleViewModel(
                    title = resourceProvider.getString(R.string.top_ten),
                    value = playerOne?.top10?.toDouble() ?: 0.0,
                    valueTwo = playerTwo?.top10?.toDouble() ?: 0.0,
                    nickName = nameOne,
                    nickNameTwo = nameTwo
                )
            )

            list.add(
                ComparisonScheduleViewModel(
                    title = resourceProvider.getString(R.string.top_twelve),
                    value = playerOne?.top12?.toDouble() ?: 0.0,
                    valueTwo = playerTwo?.top12?.toDouble() ?: 0.0,
                    nickName = nameOne,
                    nickNameTwo = nameTwo
                )
            )

            list.add(
                ComparisonScheduleViewModel(
                    title = resourceProvider.getString(R.string.top_twenty_five),
                    value = playerOne?.top25?.toDouble() ?: 0.0,
                    valueTwo = playerTwo?.top25?.toDouble() ?: 0.0,
                    nickName = nameOne,
                    nickNameTwo = nameTwo
                )
            )

            list.add(ComparisonStatisticsHeaderViewModel(resourceProvider.getString(R.string.score)))

            list.add(
                ComparisonScheduleViewModel(
                    title = resourceProvider.getString(R.string.score),
                    value = playerOne?.score?.toDouble() ?: 0.0,
                    valueTwo = playerTwo?.score?.toDouble() ?: 0.0,
                    nickName = nameOne,
                    nickNameTwo = nameTwo
                )
            )

            list.add(
                ComparisonScheduleViewModel(
                    title = resourceProvider.getString(R.string.avg_score),
                    value = playerOne?.getAvgScore()?.toDouble() ?: 0.0,
                    valueTwo = playerTwo?.getAvgScore()?.toDouble() ?: 0.0,
                    nickName = nameOne,
                    nickNameTwo = nameTwo
                )
            )

            list.add(
                ComparisonScheduleViewModel(
                    title = resourceProvider.getString(R.string.score_per_min),
                    value = playerOne?.scorePerMin?.toDouble() ?: 0.0,
                    valueTwo = playerTwo?.scorePerMin?.toDouble() ?: 0.0,
                    nickName = nameOne,
                    nickNameTwo = nameTwo
                )
            )

            list.add(
                ComparisonScheduleViewModel(
                    title = resourceProvider.getString(R.string.score_per_match),
                    value = playerOne?.scorePerMatch?.toDouble() ?: 0.0,
                    valueTwo = playerTwo?.scorePerMatch?.toDouble() ?: 0.0,
                    nickName = nameOne,
                    nickNameTwo = nameTwo
                )
            )

            list.add(
                ComparisonScheduleViewModel(
                    title = resourceProvider.getString(R.string.minutes_played),
                    value = playerOne?.minutesPlayed?.toDouble() ?: 0.0,
                    valueTwo = playerTwo?.minutesPlayed?.toDouble() ?: 0.0,
                    nickName = nameOne,
                    nickNameTwo = nameTwo
                )
            )

            list.add(
                ComparisonScheduleViewModel(
                    title = resourceProvider.getString(R.string.players_outlived),
                    value = playerOne?.playersOutlived?.toDouble() ?: 0.0,
                    valueTwo = playerTwo?.playersOutlived?.toDouble() ?: 0.0,
                    nickName = nameOne,
                    nickNameTwo = nameTwo
                )
            )
        }

        return list
    }

    private fun getOverallSchedule(
        playerOne: SoloMatches?,
        playerTwo: SoloMatches?,
        nameOne: String,
        nameTwo: String
    ): List<ComparisonPlayer> {
        val list = arrayListOf<ComparisonPlayer>()

        if (playerOne == null && playerTwo == null) {
            list.add(ComparisonStatisticsHeaderViewModel(resourceProvider.getString(R.string.players_did_not_participate_in_battles)))
        } else {
            list.add(
                ComparisonScheduleViewModel(
                    title = resourceProvider.getString(R.string.matches),
                    value = playerOne?.matches?.toDouble() ?: 0.0,
                    valueTwo = playerTwo?.matches?.toDouble() ?: 0.0,
                    nickName = nameOne,
                    nickNameTwo = nameTwo
                )
            )

            list.add(
                ComparisonScheduleViewModel(
                    title = resourceProvider.getString(R.string.wins),
                    value = playerOne?.wins?.toDouble() ?: 0.0,
                    valueTwo = playerTwo?.wins?.toDouble() ?: 0.0,
                    nickName = nameOne,
                    nickNameTwo = nameTwo
                )
            )

            list.add(
                ComparisonScheduleViewModel(
                    title = resourceProvider.getString(R.string.wins_percent),
                    value = playerOne?.winRate?.toDouble() ?: 0.0,
                    valueTwo = playerTwo?.winRate?.toDouble() ?: 0.0,
                    nickName = nameOne,
                    nickNameTwo = nameTwo
                )
            )

            list.add(
                ComparisonScheduleViewModel(
                    title = resourceProvider.getString(R.string.kd),
                    value = playerOne?.kd ?: 0.0,
                    valueTwo = playerTwo?.kd ?: 0.0,
                    nickName = nameOne,
                    nickNameTwo = nameTwo
                )
            )

            list.add(
                ComparisonScheduleViewModel(
                    title = resourceProvider.getString(R.string.kills),
                    value = playerOne?.kills?.toDouble() ?: 0.0,
                    valueTwo = playerTwo?.kills?.toDouble() ?: 0.0,
                    nickName = nameOne,
                    nickNameTwo = nameTwo
                )
            )

            list.add(
                ComparisonScheduleViewModel(
                    title = resourceProvider.getString(R.string.deaths),
                    value = playerOne?.deaths?.toDouble() ?: 0.0,
                    valueTwo = playerTwo?.deaths?.toDouble() ?: 0.0,
                    nickName = nameOne,
                    nickNameTwo = nameTwo
                )
            )

            list.add(
                ComparisonScheduleViewModel(
                    title = resourceProvider.getString(R.string.killsPerMin),
                    value = playerOne?.killsPerMin?.toDouble() ?: 0.0,
                    valueTwo = playerTwo?.killsPerMin?.toDouble() ?: 0.0,
                    nickName = nameOne,
                    nickNameTwo = nameTwo
                )
            )

            list.add(
                ComparisonScheduleViewModel(
                    title = resourceProvider.getString(R.string.killsPerMatch),
                    value = playerOne?.killsPerMatch?.toDouble() ?: 0.0,
                    valueTwo = playerTwo?.killsPerMatch?.toDouble() ?: 0.0,
                    nickName = nameOne,
                    nickNameTwo = nameTwo
                )
            )

            list.add(ComparisonStatisticsHeaderViewModel(resourceProvider.getString(R.string.tops)))

            list.add(
                ComparisonScheduleViewModel(
                    title = resourceProvider.getString(R.string.top_one),
                    value = playerOne?.wins?.toDouble() ?: 0.0,
                    valueTwo = playerTwo?.wins?.toDouble() ?: 0.0,
                    nickName = nameOne,
                    nickNameTwo = nameTwo
                )
            )

            list.add(
                ComparisonScheduleViewModel(
                    title = resourceProvider.getString(R.string.top_ten),
                    value = playerOne?.top10?.toDouble() ?: 0.0,
                    valueTwo = playerTwo?.top10?.toDouble() ?: 0.0,
                    nickName = nameOne,
                    nickNameTwo = nameTwo
                )
            )

            list.add(
                ComparisonScheduleViewModel(
                    title = resourceProvider.getString(R.string.top_twenty_five),
                    value = playerOne?.top25?.toDouble() ?: 0.0,
                    valueTwo = playerTwo?.top25?.toDouble() ?: 0.0,
                    nickName = nameOne,
                    nickNameTwo = nameTwo
                )
            )

            list.add(ComparisonStatisticsHeaderViewModel(resourceProvider.getString(R.string.score)))

            list.add(
                ComparisonScheduleViewModel(
                    title = resourceProvider.getString(R.string.score),
                    value = playerOne?.score?.toDouble() ?: 0.0,
                    valueTwo = playerTwo?.score?.toDouble() ?: 0.0,
                    nickName = nameOne,
                    nickNameTwo = nameTwo
                )
            )

            list.add(
                ComparisonScheduleViewModel(
                    title = resourceProvider.getString(R.string.avg_score),
                    value = playerOne?.getAvgScore()?.toDouble() ?: 0.0,
                    valueTwo = playerTwo?.getAvgScore()?.toDouble() ?: 0.0,
                    nickName = nameOne,
                    nickNameTwo = nameTwo
                )
            )

            list.add(
                ComparisonScheduleViewModel(
                    title = resourceProvider.getString(R.string.score_per_min),
                    value = playerOne?.scorePerMin?.toDouble() ?: 0.0,
                    valueTwo = playerTwo?.scorePerMin?.toDouble() ?: 0.0,
                    nickName = nameOne,
                    nickNameTwo = nameTwo
                )
            )

            list.add(
                ComparisonScheduleViewModel(
                    title = resourceProvider.getString(R.string.score_per_match),
                    value = playerOne?.scorePerMatch?.toDouble() ?: 0.0,
                    valueTwo = playerTwo?.scorePerMatch?.toDouble() ?: 0.0,
                    nickName = nameOne,
                    nickNameTwo = nameTwo
                )
            )

            list.add(
                ComparisonScheduleViewModel(
                    title = resourceProvider.getString(R.string.minutes_played),
                    value = playerOne?.minutesPlayed?.toDouble() ?: 0.0,
                    valueTwo = playerTwo?.minutesPlayed?.toDouble() ?: 0.0,
                    nickName = nameOne,
                    nickNameTwo = nameTwo
                )
            )

            list.add(
                ComparisonScheduleViewModel(
                    title = resourceProvider.getString(R.string.players_outlived),
                    value = playerOne?.playersOutlived?.toDouble() ?: 0.0,
                    valueTwo = playerTwo?.playersOutlived?.toDouble() ?: 0.0,
                    nickName = nameOne,
                    nickNameTwo = nameTwo
                )
            )
        }

        return list
    }

    private fun getOverallSchedule(
        playerOne: DuoMatches?,
        playerTwo: DuoMatches?,
        nameOne: String,
        nameTwo: String
    ): List<ComparisonPlayer> {
        val list = arrayListOf<ComparisonPlayer>()

        if (playerOne == null && playerTwo == null) {
            list.add(ComparisonStatisticsHeaderViewModel(resourceProvider.getString(R.string.players_did_not_participate_in_battles)))
        } else {
            list.add(
                ComparisonScheduleViewModel(
                    title = resourceProvider.getString(R.string.matches),
                    value = playerOne?.matches?.toDouble() ?: 0.0,
                    valueTwo = playerTwo?.matches?.toDouble() ?: 0.0,
                    nickName = nameOne,
                    nickNameTwo = nameTwo
                )
            )

            list.add(
                ComparisonScheduleViewModel(
                    title = resourceProvider.getString(R.string.wins),
                    value = playerOne?.wins?.toDouble() ?: 0.0,
                    valueTwo = playerTwo?.wins?.toDouble() ?: 0.0,
                    nickName = nameOne,
                    nickNameTwo = nameTwo
                )
            )

            list.add(
                ComparisonScheduleViewModel(
                    title = resourceProvider.getString(R.string.wins_percent),
                    value = playerOne?.winRate?.toDouble() ?: 0.0,
                    valueTwo = playerTwo?.winRate?.toDouble() ?: 0.0,
                    nickName = nameOne,
                    nickNameTwo = nameTwo
                )
            )

            list.add(
                ComparisonScheduleViewModel(
                    title = resourceProvider.getString(R.string.kd),
                    value = playerOne?.kd ?: 0.0,
                    valueTwo = playerTwo?.kd ?: 0.0,
                    nickName = nameOne,
                    nickNameTwo = nameTwo
                )
            )

            list.add(
                ComparisonScheduleViewModel(
                    title = resourceProvider.getString(R.string.kills),
                    value = playerOne?.kills?.toDouble() ?: 0.0,
                    valueTwo = playerTwo?.kills?.toDouble() ?: 0.0,
                    nickName = nameOne,
                    nickNameTwo = nameTwo
                )
            )

            list.add(
                ComparisonScheduleViewModel(
                    title = resourceProvider.getString(R.string.deaths),
                    value = playerOne?.deaths?.toDouble() ?: 0.0,
                    valueTwo = playerTwo?.deaths?.toDouble() ?: 0.0,
                    nickName = nameOne,
                    nickNameTwo = nameTwo
                )
            )

            list.add(
                ComparisonScheduleViewModel(
                    title = resourceProvider.getString(R.string.killsPerMin),
                    value = playerOne?.killsPerMin?.toDouble() ?: 0.0,
                    valueTwo = playerTwo?.killsPerMin?.toDouble() ?: 0.0,
                    nickName = nameOne,
                    nickNameTwo = nameTwo
                )
            )

            list.add(
                ComparisonScheduleViewModel(
                    title = resourceProvider.getString(R.string.killsPerMatch),
                    value = playerOne?.killsPerMatch?.toDouble() ?: 0.0,
                    valueTwo = playerTwo?.killsPerMatch?.toDouble() ?: 0.0,
                    nickName = nameOne,
                    nickNameTwo = nameTwo
                )
            )

            list.add(ComparisonStatisticsHeaderViewModel(resourceProvider.getString(R.string.tops)))

            list.add(
                ComparisonScheduleViewModel(
                    title = resourceProvider.getString(R.string.top_one),
                    value = playerOne?.wins?.toDouble() ?: 0.0,
                    valueTwo = playerTwo?.wins?.toDouble() ?: 0.0,
                    nickName = nameOne,
                    nickNameTwo = nameTwo
                )
            )

            list.add(
                ComparisonScheduleViewModel(
                    title = resourceProvider.getString(R.string.top_five),
                    value = playerOne?.top5?.toDouble() ?: 0.0,
                    valueTwo = playerTwo?.top5?.toDouble() ?: 0.0,
                    nickName = nameOne,
                    nickNameTwo = nameTwo
                )
            )

            list.add(
                ComparisonScheduleViewModel(
                    title = resourceProvider.getString(R.string.top_twelve),
                    value = playerOne?.top12?.toDouble() ?: 0.0,
                    valueTwo = playerTwo?.top12?.toDouble() ?: 0.0,
                    nickName = nameOne,
                    nickNameTwo = nameTwo
                )
            )

            list.add(ComparisonStatisticsHeaderViewModel(resourceProvider.getString(R.string.score)))

            list.add(
                ComparisonScheduleViewModel(
                    title = resourceProvider.getString(R.string.score),
                    value = playerOne?.score?.toDouble() ?: 0.0,
                    valueTwo = playerTwo?.score?.toDouble() ?: 0.0,
                    nickName = nameOne,
                    nickNameTwo = nameTwo
                )
            )

            list.add(
                ComparisonScheduleViewModel(
                    title = resourceProvider.getString(R.string.avg_score),
                    value = playerOne?.getAvgScore()?.toDouble() ?: 0.0,
                    valueTwo = playerTwo?.getAvgScore()?.toDouble() ?: 0.0,
                    nickName = nameOne,
                    nickNameTwo = nameTwo
                )
            )

            list.add(
                ComparisonScheduleViewModel(
                    title = resourceProvider.getString(R.string.score_per_min),
                    value = playerOne?.scorePerMin?.toDouble() ?: 0.0,
                    valueTwo = playerTwo?.scorePerMin?.toDouble() ?: 0.0,
                    nickName = nameOne,
                    nickNameTwo = nameTwo
                )
            )

            list.add(
                ComparisonScheduleViewModel(
                    title = resourceProvider.getString(R.string.score_per_match),
                    value = playerOne?.scorePerMatch?.toDouble() ?: 0.0,
                    valueTwo = playerTwo?.scorePerMatch?.toDouble() ?: 0.0,
                    nickName = nameOne,
                    nickNameTwo = nameTwo
                )
            )

            list.add(
                ComparisonScheduleViewModel(
                    title = resourceProvider.getString(R.string.minutes_played),
                    value = playerOne?.minutesPlayed?.toDouble() ?: 0.0,
                    valueTwo = playerTwo?.minutesPlayed?.toDouble() ?: 0.0,
                    nickName = nameOne,
                    nickNameTwo = nameTwo
                )
            )

            list.add(
                ComparisonScheduleViewModel(
                    title = resourceProvider.getString(R.string.players_outlived),
                    value = playerOne?.playersOutlived?.toDouble() ?: 0.0,
                    valueTwo = playerTwo?.playersOutlived?.toDouble() ?: 0.0,
                    nickName = nameOne,
                    nickNameTwo = nameTwo
                )
            )
        }

        return list
    }

    private fun getOverallSchedule(
        playerOne: TrioMatches?,
        playerTwo: TrioMatches?,
        nameOne: String,
        nameTwo: String
    ): List<ComparisonPlayer> {
        val list = arrayListOf<ComparisonPlayer>()

        if (playerOne == null && playerTwo == null) {
            list.add(ComparisonStatisticsHeaderViewModel(resourceProvider.getString(R.string.players_did_not_participate_in_battles)))
        } else {
            list.add(
                ComparisonScheduleViewModel(
                    title = resourceProvider.getString(R.string.matches),
                    value = playerOne?.matches?.toDouble() ?: 0.0,
                    valueTwo = playerTwo?.matches?.toDouble() ?: 0.0,
                    nickName = nameOne,
                    nickNameTwo = nameTwo
                )
            )

            list.add(
                ComparisonScheduleViewModel(
                    title = resourceProvider.getString(R.string.wins),
                    value = playerOne?.wins?.toDouble() ?: 0.0,
                    valueTwo = playerTwo?.wins?.toDouble() ?: 0.0,
                    nickName = nameOne,
                    nickNameTwo = nameTwo
                )
            )

            list.add(
                ComparisonScheduleViewModel(
                    title = resourceProvider.getString(R.string.wins_percent),
                    value = playerOne?.winRate?.toDouble() ?: 0.0,
                    valueTwo = playerTwo?.winRate?.toDouble() ?: 0.0,
                    nickName = nameOne,
                    nickNameTwo = nameTwo
                )
            )

            list.add(
                ComparisonScheduleViewModel(
                    title = resourceProvider.getString(R.string.kd),
                    value = playerOne?.kd ?: 0.0,
                    valueTwo = playerTwo?.kd ?: 0.0,
                    nickName = nameOne,
                    nickNameTwo = nameTwo
                )
            )

            list.add(
                ComparisonScheduleViewModel(
                    title = resourceProvider.getString(R.string.kills),
                    value = playerOne?.kills?.toDouble() ?: 0.0,
                    valueTwo = playerTwo?.kills?.toDouble() ?: 0.0,
                    nickName = nameOne,
                    nickNameTwo = nameTwo
                )
            )

            list.add(
                ComparisonScheduleViewModel(
                    title = resourceProvider.getString(R.string.deaths),
                    value = playerOne?.deaths?.toDouble() ?: 0.0,
                    valueTwo = playerTwo?.deaths?.toDouble() ?: 0.0,
                    nickName = nameOne,
                    nickNameTwo = nameTwo
                )
            )

            list.add(
                ComparisonScheduleViewModel(
                    title = resourceProvider.getString(R.string.killsPerMin),
                    value = playerOne?.killsPerMin?.toDouble() ?: 0.0,
                    valueTwo = playerTwo?.killsPerMin?.toDouble() ?: 0.0,
                    nickName = nameOne,
                    nickNameTwo = nameTwo
                )
            )

            list.add(
                ComparisonScheduleViewModel(
                    title = resourceProvider.getString(R.string.killsPerMatch),
                    value = playerOne?.killsPerMatch?.toDouble() ?: 0.0,
                    valueTwo = playerTwo?.killsPerMatch?.toDouble() ?: 0.0,
                    nickName = nameOne,
                    nickNameTwo = nameTwo
                )
            )

            list.add(ComparisonStatisticsHeaderViewModel(resourceProvider.getString(R.string.tops)))

            list.add(
                ComparisonScheduleViewModel(
                    title = resourceProvider.getString(R.string.top_one),
                    value = playerOne?.wins?.toDouble() ?: 0.0,
                    valueTwo = playerTwo?.wins?.toDouble() ?: 0.0,
                    nickName = nameOne,
                    nickNameTwo = nameTwo
                )
            )

            list.add(
                ComparisonScheduleViewModel(
                    title = resourceProvider.getString(R.string.top_three),
                    value = playerOne?.top3?.toDouble() ?: 0.0,
                    valueTwo = playerTwo?.top3?.toDouble() ?: 0.0,
                    nickName = nameOne,
                    nickNameTwo = nameTwo
                )
            )

            list.add(
                ComparisonScheduleViewModel(
                    title = resourceProvider.getString(R.string.top_six),
                    value = playerOne?.top6?.toDouble() ?: 0.0,
                    valueTwo = playerTwo?.top6?.toDouble() ?: 0.0,
                    nickName = nameOne,
                    nickNameTwo = nameTwo
                )
            )

            list.add(ComparisonStatisticsHeaderViewModel(resourceProvider.getString(R.string.score)))

            list.add(
                ComparisonScheduleViewModel(
                    title = resourceProvider.getString(R.string.score),
                    value = playerOne?.score?.toDouble() ?: 0.0,
                    valueTwo = playerTwo?.score?.toDouble() ?: 0.0,
                    nickName = nameOne,
                    nickNameTwo = nameTwo
                )
            )

            list.add(
                ComparisonScheduleViewModel(
                    title = resourceProvider.getString(R.string.avg_score),
                    value = playerOne?.getAvgScore()?.toDouble() ?: 0.0,
                    valueTwo = playerTwo?.getAvgScore()?.toDouble() ?: 0.0,
                    nickName = nameOne,
                    nickNameTwo = nameTwo
                )
            )

            list.add(
                ComparisonScheduleViewModel(
                    title = resourceProvider.getString(R.string.score_per_min),
                    value = playerOne?.scorePerMin?.toDouble() ?: 0.0,
                    valueTwo = playerTwo?.scorePerMin?.toDouble() ?: 0.0,
                    nickName = nameOne,
                    nickNameTwo = nameTwo
                )
            )

            list.add(
                ComparisonScheduleViewModel(
                    title = resourceProvider.getString(R.string.score_per_match),
                    value = playerOne?.scorePerMatch?.toDouble() ?: 0.0,
                    valueTwo = playerTwo?.scorePerMatch?.toDouble() ?: 0.0,
                    nickName = nameOne,
                    nickNameTwo = nameTwo
                )
            )

            list.add(
                ComparisonScheduleViewModel(
                    title = resourceProvider.getString(R.string.minutes_played),
                    value = playerOne?.minutesPlayed?.toDouble() ?: 0.0,
                    valueTwo = playerTwo?.minutesPlayed?.toDouble() ?: 0.0,
                    nickName = nameOne,
                    nickNameTwo = nameTwo
                )
            )

            list.add(
                ComparisonScheduleViewModel(
                    title = resourceProvider.getString(R.string.players_outlived),
                    value = playerOne?.playersOutlived?.toDouble() ?: 0.0,
                    valueTwo = playerTwo?.playersOutlived?.toDouble() ?: 0.0,
                    nickName = nameOne,
                    nickNameTwo = nameTwo
                )
            )
        }

        return list
    }

    private fun getOverallSchedule(
        playerOne: Ltm?,
        playerTwo: Ltm?,
        nameOne: String,
        nameTwo: String
    ): List<ComparisonPlayer> {
        val list = arrayListOf<ComparisonPlayer>()

        if (playerOne == null && playerTwo == null) {
            list.add(ComparisonStatisticsHeaderViewModel(resourceProvider.getString(R.string.players_did_not_participate_in_battles)))
        } else {
            list.add(
                ComparisonScheduleViewModel(
                    title = resourceProvider.getString(R.string.matches),
                    value = playerOne?.matches?.toDouble() ?: 0.0,
                    valueTwo = playerTwo?.matches?.toDouble() ?: 0.0,
                    nickName = nameOne,
                    nickNameTwo = nameTwo
                )
            )

            list.add(
                ComparisonScheduleViewModel(
                    title = resourceProvider.getString(R.string.wins),
                    value = playerOne?.wins?.toDouble() ?: 0.0,
                    valueTwo = playerTwo?.wins?.toDouble() ?: 0.0,
                    nickName = nameOne,
                    nickNameTwo = nameTwo
                )
            )

            list.add(
                ComparisonScheduleViewModel(
                    title = resourceProvider.getString(R.string.wins_percent),
                    value = playerOne?.winRate?.toDouble() ?: 0.0,
                    valueTwo = playerTwo?.winRate?.toDouble() ?: 0.0,
                    nickName = nameOne,
                    nickNameTwo = nameTwo
                )
            )

            list.add(
                ComparisonScheduleViewModel(
                    title = resourceProvider.getString(R.string.kd),
                    value = playerOne?.kd ?: 0.0,
                    valueTwo = playerTwo?.kd ?: 0.0,
                    nickName = nameOne,
                    nickNameTwo = nameTwo
                )
            )

            list.add(
                ComparisonScheduleViewModel(
                    title = resourceProvider.getString(R.string.kills),
                    value = playerOne?.kills?.toDouble() ?: 0.0,
                    valueTwo = playerTwo?.kills?.toDouble() ?: 0.0,
                    nickName = nameOne,
                    nickNameTwo = nameTwo
                )
            )

            list.add(
                ComparisonScheduleViewModel(
                    title = resourceProvider.getString(R.string.deaths),
                    value = playerOne?.deaths?.toDouble() ?: 0.0,
                    valueTwo = playerTwo?.deaths?.toDouble() ?: 0.0,
                    nickName = nameOne,
                    nickNameTwo = nameTwo
                )
            )

            list.add(
                ComparisonScheduleViewModel(
                    title = resourceProvider.getString(R.string.killsPerMin),
                    value = playerOne?.killsPerMin?.toDouble() ?: 0.0,
                    valueTwo = playerTwo?.killsPerMin?.toDouble() ?: 0.0,
                    nickName = nameOne,
                    nickNameTwo = nameTwo
                )
            )

            list.add(
                ComparisonScheduleViewModel(
                    title = resourceProvider.getString(R.string.killsPerMatch),
                    value = playerOne?.killsPerMatch?.toDouble() ?: 0.0,
                    valueTwo = playerTwo?.killsPerMatch?.toDouble() ?: 0.0,
                    nickName = nameOne,
                    nickNameTwo = nameTwo
                )
            )

            list.add(ComparisonStatisticsHeaderViewModel(resourceProvider.getString(R.string.tops)))

            list.add(
                ComparisonScheduleViewModel(
                    title = resourceProvider.getString(R.string.top_one),
                    value = playerOne?.wins?.toDouble() ?: 0.0,
                    valueTwo = playerTwo?.wins?.toDouble() ?: 0.0,
                    nickName = nameOne,
                    nickNameTwo = nameTwo
                )
            )


            list.add(ComparisonStatisticsHeaderViewModel(resourceProvider.getString(R.string.score)))

            list.add(
                ComparisonScheduleViewModel(
                    title = resourceProvider.getString(R.string.score),
                    value = playerOne?.score?.toDouble() ?: 0.0,
                    valueTwo = playerTwo?.score?.toDouble() ?: 0.0,
                    nickName = nameOne,
                    nickNameTwo = nameTwo
                )
            )

            list.add(
                ComparisonScheduleViewModel(
                    title = resourceProvider.getString(R.string.avg_score),
                    value = playerOne?.getAvgScore()?.toDouble() ?: 0.0,
                    valueTwo = playerTwo?.getAvgScore()?.toDouble() ?: 0.0,
                    nickName = nameOne,
                    nickNameTwo = nameTwo
                )
            )

            list.add(
                ComparisonScheduleViewModel(
                    title = resourceProvider.getString(R.string.score_per_min),
                    value = playerOne?.scorePerMin?.toDouble() ?: 0.0,
                    valueTwo = playerTwo?.scorePerMin?.toDouble() ?: 0.0,
                    nickName = nameOne,
                    nickNameTwo = nameTwo
                )
            )

            list.add(
                ComparisonScheduleViewModel(
                    title = resourceProvider.getString(R.string.score_per_match),
                    value = playerOne?.scorePerMatch?.toDouble() ?: 0.0,
                    valueTwo = playerTwo?.scorePerMatch?.toDouble() ?: 0.0,
                    nickName = nameOne,
                    nickNameTwo = nameTwo
                )
            )

            list.add(
                ComparisonScheduleViewModel(
                    title = resourceProvider.getString(R.string.minutes_played),
                    value = playerOne?.minutesPlayed?.toDouble() ?: 0.0,
                    valueTwo = playerTwo?.minutesPlayed?.toDouble() ?: 0.0,
                    nickName = nameOne,
                    nickNameTwo = nameTwo
                )
            )

            list.add(
                ComparisonScheduleViewModel(
                    title = resourceProvider.getString(R.string.players_outlived),
                    value = playerOne?.playersOutlived?.toDouble() ?: 0.0,
                    valueTwo = playerTwo?.playersOutlived?.toDouble() ?: 0.0,
                    nickName = nameOne,
                    nickNameTwo = nameTwo
                )
            )
        }

        return list
    }
}