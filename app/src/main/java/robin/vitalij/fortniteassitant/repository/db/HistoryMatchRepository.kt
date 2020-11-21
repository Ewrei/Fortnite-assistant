package robin.vitalij.fortniteassitant.repository.db

import io.reactivex.Flowable
import robin.vitalij.fortniteassitant.db.dao.UserDao
import robin.vitalij.fortniteassitant.ui.history.adapter.viewmodel.HistoryMatch
import robin.vitalij.fortniteassitant.utils.mapper.HistoryMapper
import javax.inject.Inject

class HistoryMatchRepository @Inject constructor(private val userDao: UserDao) {

    fun loadData(playerId: String): Flowable<List<HistoryMatch>> =
        userDao.getHistoryMatch(playerId).flatMap {
            return@flatMap Flowable.just(HistoryMapper().transform(it))
        }
}