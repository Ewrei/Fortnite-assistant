package robin.vitalij.fortniteassitant.ui.bottomsheet.profile.adapter

import robin.vitalij.fortniteassitant.ui.bottomsheet.profile.adapter.stats_adapter.BodyStatsListItem

sealed class ProfileListItem {

    data class HeaderItem(
        val avatarUrl: String,
        val userName: String,
        val playerId: String,
        val level: Int,
        val progress: Int,
        val matches: Int,
        val kd: Double,
        val winRate: Double,
        val playTime: String,
        val totalMatches: String
    ) : ProfileListItem()

    data class BodyItem(
        val all: List<BodyStatsListItem>,
        val keyboardMouse: List<BodyStatsListItem>,
        val gamepad: List<BodyStatsListItem>,
        val touch: List<BodyStatsListItem>
    ) : ProfileListItem()

}