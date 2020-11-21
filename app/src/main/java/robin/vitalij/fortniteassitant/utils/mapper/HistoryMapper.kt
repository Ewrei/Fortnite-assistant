package robin.vitalij.fortniteassitant.utils.mapper

import robin.vitalij.fortniteassitant.db.entity.MatchEntity
import robin.vitalij.fortniteassitant.ui.history.adapter.viewmodel.HistoryMatch
import robin.vitalij.fortniteassitant.ui.history.adapter.viewmodel.HistoryMatchViewModel
import robin.vitalij.fortniteassitant.utils.mapper.base.Mapper

class HistoryMapper : Mapper<List<MatchEntity>, List<HistoryMatch>> {

    override fun transform(obj: List<MatchEntity>): List<HistoryMatch> {
        val list = arrayListOf<HistoryMatch>()

        obj.forEach {
            list.add(HistoryMatchViewModel(it))
        }
        return list
    }
}