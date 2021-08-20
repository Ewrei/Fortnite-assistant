package robin.vitalij.fortniteassitant.repository.db

import io.reactivex.Flowable
import robin.vitalij.fortniteassitant.db.dao.UserDao
import robin.vitalij.fortniteassitant.model.HistoryUserModel
import robin.vitalij.fortniteassitant.utils.mapper.HistoryMapper
import javax.inject.Inject

class HistoryRepository @Inject constructor(private val userDao: UserDao) {

    fun loadData(playerId: String): Flowable<List<HistoryUserModel>> =
        userDao.getUserHistory(playerId).flatMap {
            return@flatMap Flowable.just(HistoryMapper().transform(it))
        }
}