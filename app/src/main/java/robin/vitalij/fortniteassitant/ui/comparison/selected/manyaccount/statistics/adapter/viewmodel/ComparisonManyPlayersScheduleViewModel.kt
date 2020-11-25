package robin.vitalij.fortniteassitant.ui.comparison.selected.manyaccount.statistics.adapter.viewmodel

import robin.vitalij.fortniteassitant.model.comparison.ManyPlayerSchedule

class ComparisonManyPlayersScheduleViewModel(
    val title: String,
    val manyPlayers: List<ManyPlayerSchedule>
) : ComparisonManyPlayers {

    override fun getType() = ComparisonManyPlayersType.SCHEDULE
}