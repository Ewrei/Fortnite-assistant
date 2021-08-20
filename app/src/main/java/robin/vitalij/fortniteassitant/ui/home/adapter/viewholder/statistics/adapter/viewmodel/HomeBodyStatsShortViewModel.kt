package robin.vitalij.fortniteassitant.ui.home.adapter.viewholder.statistics.adapter.viewmodel

data class HomeBodyStatsShortViewModel(
    val leftTop: String,
    val leftTopTitle: String,
    val rightTop: String,
    val rightTopTitle: String,
    val leftBottom: String,
    val leftBottomTitle: String,
    val rightBottom: String,
    val rightBottomTitle: String
) : HomeBodyStats {
    override fun getType() = HomeBodyStatsType.STATISTICS_SHORT
}