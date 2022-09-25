package robin.vitalij.fortniteassitant.ui.home.adapter.viewholder.statistics.adapter

sealed class HomeBodyStatsListItem {

    data class EmptyItem(val title: String) : HomeBodyStatsListItem()

    data class HeaderItem(val title: String) : HomeBodyStatsListItem()

    data class StatsShortItem(
        val leftTop: String,
        val leftTopTitle: String,
        val rightTop: String,
        val rightTopTitle: String,
        val leftBottom: String,
        val leftBottomTitle: String,
        val rightBottom: String,
        val rightBottomTitle: String
    ) : HomeBodyStatsListItem()

    data class StatsSmallItem(
        val leftTop: String,
        val leftTopTitle: String,
        val rightTop: String,
        val rightTopTitle: String
    ) : HomeBodyStatsListItem()

    data class StatsItem(
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
    ) : HomeBodyStatsListItem()

    object DetailStatisticsItem : HomeBodyStatsListItem()

}