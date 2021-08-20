package robin.vitalij.fortniteassitant.ui.comparison.statistics.adapter.viewmodel

class ComparisonStatisticsViewModel(
    val title: String,
    val value: Double,
    val valueTwo: Double
) : ComparisonPlayer {

    override fun getType() = ComparisonPlayerType.STATISTICS
}