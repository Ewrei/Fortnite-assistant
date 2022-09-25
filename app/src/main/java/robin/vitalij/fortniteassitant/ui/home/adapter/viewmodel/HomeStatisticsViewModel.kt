package robin.vitalij.fortniteassitant.ui.home.adapter.viewmodel

import robin.vitalij.fortniteassitant.ui.home.adapter.viewholder.statistics.adapter.HomeBodyStatsListItem

data class HomeStatisticsViewModel(
    val all: List<HomeBodyStatsListItem>,
    val keyboardMouse: List<HomeBodyStatsListItem>,
    val gamepad: List<HomeBodyStatsListItem>,
    val touch: List<HomeBodyStatsListItem>
) : Home {
    override fun getType() = HomeType.STATISTICS
}