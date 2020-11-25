package robin.vitalij.fortniteassitant.ui.home.adapter.viewholder.statistics.adapter.viewmodel

data class HomeBodyStatsSmallViewModel(
    val leftTop: String,
    val leftTopTitle: String,
    val rightTop: String,
    val rightTopTitle: String
) : HomeBodyStats {
    override fun getType() = HomeBodyStatsType.STATISTICS_SMALL
}