package robin.vitalij.fortniteassitant.ui.home.adapter.viewholder.statistics.adapter.viewmodel

data class HomeBodeDetailsStatisticsViewModel(
    val title: String
) : HomeBodyStats {
    override fun getType() = HomeBodyStatsType.DETAIL_STATISTICS
}