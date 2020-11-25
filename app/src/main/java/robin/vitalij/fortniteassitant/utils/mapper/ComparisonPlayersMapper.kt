package robin.vitalij.fortniteassitant.utils.mapper

import robin.vitalij.fortniteassitant.model.comparison.ComparisonProfileResponse
import robin.vitalij.fortniteassitant.ui.comparison.statistics.adapter.viewmodel.ComparisonPlayer
import robin.vitalij.fortniteassitant.utils.mapper.base.Mapper
import robin.vitalij.fortniteassitant.utils.view.ResourceProvider

class ComparisonPlayersMapper(
    private val resourceProvider: ResourceProvider,
    private val isSchedule: Boolean
) : Mapper<ComparisonProfileResponse, List<ComparisonPlayer>> {

    override fun transform(obj: ComparisonProfileResponse): List<ComparisonPlayer> {
        val list = arrayListOf<ComparisonPlayer>()

        if (isSchedule) {
          // list.addAll(getSchedule(obj))
        } else {
          //  list.addAll(getStatistics(obj))
        }
        return list
    }
}