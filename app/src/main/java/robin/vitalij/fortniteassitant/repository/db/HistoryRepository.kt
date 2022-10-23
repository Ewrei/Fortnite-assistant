package robin.vitalij.fortniteassitant.repository.db

import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import robin.vitalij.fortniteassitant.common.extensions.getErrorModel
import robin.vitalij.fortniteassitant.db.dao.UserDao
import robin.vitalij.fortniteassitant.db.entity.UserEntity
import robin.vitalij.fortniteassitant.model.ErrorModelListItem
import robin.vitalij.fortniteassitant.model.HistoryUserModel
import robin.vitalij.fortniteassitant.model.LoadingState
import robin.vitalij.fortniteassitant.utils.mapper.HistoryMapper
import javax.inject.Inject

class HistoryRepository @Inject constructor(private val userDao: UserDao) {

    fun loadData(playerId: String): Flowable<List<HistoryUserModel>> =
        userDao.getUserHistory(playerId)
            .subscribeOn(Schedulers.io())
            .flatMap {
            return@flatMap Flowable.just(HistoryMapper().transform(it))
        }

    fun getUserHistory(playerId: String): Flow<LoadingState<List<HistoryUserModel>>> = flow {
        emit(LoadingState.Loading)
        kotlin.runCatching { userDao.getUserHistoryNewVersion(playerId) }
            .onSuccess { emit(LoadingState.Success(HistoryMapper().transform(it))) }
            .onFailure { emit(LoadingState.Error(ErrorModelListItem.ErrorItem(it.getErrorModel()))) }
    }.flowOn(Dispatchers.IO)

}