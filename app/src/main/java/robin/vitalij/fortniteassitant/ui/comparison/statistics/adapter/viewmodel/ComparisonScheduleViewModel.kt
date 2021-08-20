package robin.vitalij.fortniteassitant.ui.comparison.statistics.adapter.viewmodel

class ComparisonScheduleViewModel(
    val title: String,
    val nickName: String,
    val nickNameTwo: String,
    val value: Double,
    val valueTwo: Double
) : ComparisonPlayer {

    override fun getType() = ComparisonPlayerType.SCHEDULE
}