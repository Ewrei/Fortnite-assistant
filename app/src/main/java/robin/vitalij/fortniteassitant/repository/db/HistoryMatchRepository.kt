package robin.vitalij.fortniteassitant.repository.db

import io.reactivex.Flowable
import robin.vitalij.fortniteassitant.db.dao.MatchDao
import robin.vitalij.fortniteassitant.ui.historymatch.adapter.viewmodel.HistoryMatch
import robin.vitalij.fortniteassitant.utils.mapper.HistoryMatchMapper
import javax.inject.Inject

class HistoryMatchRepository @Inject constructor(private val matchDao: MatchDao) {

    fun loadData(playerId: String): Flowable<List<HistoryMatch>> =
        matchDao.getHistoryMatch(playerId).flatMap {
            return@flatMap Flowable.just(HistoryMatchMapper().transform(it))
        }
}