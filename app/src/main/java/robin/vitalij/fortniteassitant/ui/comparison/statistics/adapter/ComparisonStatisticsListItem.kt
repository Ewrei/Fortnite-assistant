package robin.vitalij.fortniteassitant.ui.comparison.statistics.adapter

sealed class ComparisonStatisticsListItem {

    data class ScheduleItem(
        val title: String,
        val nickName: String,
        val nickNameTwo: String,
        val value: Double,
        val valueTwo: Double
    ) : ComparisonStatisticsListItem()

    data class HeaderItem(
        val title: String
    ) : ComparisonStatisticsListItem()

    data class StatisticsItem(
        val title: String,
        val value: Double,
        val valueTwo: Double
    ) : ComparisonStatisticsListItem()

}