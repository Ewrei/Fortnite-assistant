package robin.vitalij.fortniteassitant.utils.mapper

import robin.vitalij.fortniteassitant.model.comparison.PlayerModel
import robin.vitalij.fortniteassitant.ui.comparison.selected.manyaccount.statistics.adapter.viewmodel.ComparisonManyPlayers
import robin.vitalij.fortniteassitant.utils.mapper.base.Mapper
import robin.vitalij.fortniteassitant.utils.view.ResourceProvider

class ComparisonManyAccountsStatisticsMapper(
    private val resourceProvider: ResourceProvider,
    private val isOther: Boolean
) : Mapper<List<PlayerModel>, List<ComparisonManyPlayers>> {

    override fun transform(obj: List<PlayerModel>): List<ComparisonManyPlayers> {
        val list = arrayListOf<ComparisonManyPlayers>()

       // list.addAll(getStatistics(obj))
        return list
    }
}