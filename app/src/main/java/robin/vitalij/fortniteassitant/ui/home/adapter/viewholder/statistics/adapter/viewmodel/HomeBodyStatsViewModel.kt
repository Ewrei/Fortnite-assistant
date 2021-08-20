package robin.vitalij.fortniteassitant.ui.home.adapter.viewholder.statistics.adapter.viewmodel

data class HomeBodyStatsViewModel(
    val leftTop: String,
    val leftTopTitle: String,
    val rightTop: String,
    val rightTopTitle: String,
    val leftBottom: String,
    val leftBottomTitle: String,
    val rightBottom: String,
    val rightBottomTitle: String,
    val center: Double,
    val centerTitle: String
) : HomeBodyStats {
    override fun getType() = HomeBodyStatsType.STATISTICS
}