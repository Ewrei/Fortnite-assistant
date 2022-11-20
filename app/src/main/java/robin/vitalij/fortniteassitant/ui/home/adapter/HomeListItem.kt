package robin.vitalij.fortniteassitant.ui.home.adapter

import robin.vitalij.fortniteassitant.ui.home.adapter.viewholder.session.adapter.HomeSessionListItem
import robin.vitalij.fortniteassitant.ui.home.adapter.viewholder.statistics.adapter.HomeBodyStatsListItem

sealed class HomeListItem {

    data class HeaderItem(
        val avatarUrl: String,
        val userName: String,
        val playerId: String,
        val matchesPlayed: Int,
        val timeHours: Double,
        val level: Int,
        val progress: Int
    ) : HomeListItem()

    data class SessionItem(val sessions: List<HomeSessionListItem>) : HomeListItem()

    data class StatisticsItem(
        val all: List<HomeBodyStatsListItem>,
        val keyboardMouse: List<HomeBodyStatsListItem>,
        val gamepad: List<HomeBodyStatsListItem>,
        val touch: List<HomeBodyStatsListItem>
    ) : HomeListItem()

    data class TitleItem(val title: String) : HomeListItem()

}