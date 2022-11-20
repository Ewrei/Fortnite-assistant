package robin.vitalij.fortniteassitant.ui.bottomsheet.profile.adapter.stats_adapter

import robin.vitalij.fortniteassitant.model.network.stats.Ltm
import robin.vitalij.fortniteassitant.model.network.stats.Overall

sealed class BodyStatsListItem {

    data class EmptyItem(val emptyTitle: String) : BodyStatsListItem()

    data class LtmItem(
        val title: String,
        val matches: Ltm
    ) : BodyStatsListItem()

    data class OverallItem(
        val title: String,
        val matches: Overall
    ) : BodyStatsListItem()

    data class StatsItem(
        val title: String,
        val score: Long,
        val wins: Int,
        val kills: Int,
        val killsPerMin: Double,
        val killsPerMatch: Double,
        val deaths: Int,
        val kd: Double,
        val matches: Int,
        val winRate: Double,
        val minutesPlayed: Int,
        val titleOne: String,
        val valueOne: String,
        val titleTwo: String,
        val valueTwo: String
    ) : BodyStatsListItem()

}