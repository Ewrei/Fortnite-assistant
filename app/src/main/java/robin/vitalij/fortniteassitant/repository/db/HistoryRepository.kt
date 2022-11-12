package robin.vitalij.fortniteassitant.repository.db

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import robin.vitalij.fortniteassitant.common.extensions.getErrorModel
import robin.vitalij.fortniteassitant.db.dao.UserDao
import robin.vitalij.fortniteassitant.model.ErrorModelListItem
import robin.vitalij.fortniteassitant.model.HistoryUserModel
import robin.vitalij.fortniteassitant.model.LoadingState
import robin.vitalij.fortniteassitant.utils.mapper.HistoryMapper
import javax.inject.Inject

class HistoryRepository @Inject constructor(private val userDao: UserDao) {

    fun getUserHistory(playerId: String): Flow<LoadingState<List<HistoryUserModel>>> = flow {
        emit(LoadingState.Loading)
        kotlin.runCatching { userDao.getUserHistory(playerId) }
            .onSuccess { emit(LoadingState.Success(HistoryMapper().transform(it))) }
            .onFailure { emit(LoadingState.Error(ErrorModelListItem.ErrorItem(it.getErrorModel()))) }
    }.flowOn(Dispatchers.IO)

}