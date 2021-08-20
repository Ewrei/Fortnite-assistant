package robin.vitalij.fortniteassitant.ui.home.adapter.viewmodel

import robin.vitalij.fortniteassitant.ui.home.adapter.viewholder.statistics.adapter.viewmodel.HomeBodyStats

data class HomeStatisticsViewModel(
    val all: List<HomeBodyStats>,
    val keyboardMouse: List<HomeBodyStats>,
    val gamepad: List<HomeBodyStats>,
    val touch: List<HomeBodyStats>
) : Home {
    override fun getType() = HomeType.STATISTICS
}