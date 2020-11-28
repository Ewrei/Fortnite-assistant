package robin.vitalij.fortniteassitant.utils.mapper

import robin.vitalij.fortniteassitant.db.entity.MatchEntity
import robin.vitalij.fortniteassitant.ui.historymatch.adapter.viewmodel.HistoryMatch
import robin.vitalij.fortniteassitant.ui.historymatch.adapter.viewmodel.HistoryMatchViewModel
import robin.vitalij.fortniteassitant.utils.mapper.base.Mapper

class HistoryMatchMapper : Mapper<List<MatchEntity>, List<HistoryMatch>> {

    override fun transform(obj: List<MatchEntity>): List<HistoryMatch> {
        val list = arrayListOf<HistoryMatch>()

        obj.forEach {
            list.add(HistoryMatchViewModel(it))
        }
        return list
    }
}