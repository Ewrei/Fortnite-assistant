package robin.vitalij.fortniteassitant.repository.db

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import robin.vitalij.fortniteassitant.common.extensions.getErrorModel
import robin.vitalij.fortniteassitant.db.dao.UserDao
import robin.vitalij.fortniteassitant.db.entity.UserEntity
import robin.vitalij.fortniteassitant.db.projection.UserHistory
import robin.vitalij.fortniteassitant.model.ErrorModelListItem
import robin.vitalij.fortniteassitant.model.FullHomeModel
import robin.vitalij.fortniteassitant.model.LoadingState
import robin.vitalij.fortniteassitant.utils.ResourceProvider
import robin.vitalij.fortniteassitant.utils.mapper.HomeMapper
import javax.inject.Inject

class HomeRepository @Inject constructor(
    private val userDao: UserDao,
    private val resourceProvider: ResourceProvider
) {

    fun loadData(playerId: String): Flow<LoadingState<FullHomeModel>> =
        getLastTwoUserEntities(playerId).combine(getUserHistory(playerId)) { users, userHistory ->
            if (users is LoadingState.Success && userHistory is LoadingState.Success) {
                return@combine LoadingState.Success(
                    HomeMapper(
                        resourceProvider,
                        userHistory.data
                    ).transform(users.data)
                )
            } else if (users is LoadingState.Error) {
                return@combine users
            } else if (userHistory is LoadingState.Error) {
                return@combine userHistory
            } else LoadingState.Loading

        }

    private fun getUserHistory(playerId: String): Flow<LoadingState<List<UserHistory>>> = flow {
        emit(LoadingState.Loading)
        kotlin.runCatching { userDao.getUserHistoryNewVersion(playerId) }
            .onSuccess { emit(LoadingState.Success(it)) }
            .onFailure { emit(LoadingState.Error(ErrorModelListItem.ErrorItem(it.getErrorModel()))) }
    }.flowOn(Dispatchers.IO)


    private fun getLastTwoUserEntities(playerId: String): Flow<LoadingState<List<UserEntity>>> =
        flow {
            emit(LoadingState.Loading)
            kotlin.runCatching { userDao.getLastTwoUserEntities(playerId) }
                .onSuccess { emit(LoadingState.Success((it))) }
                .onFailure { emit(LoadingState.Error(ErrorModelListItem.ErrorItem(it.getErrorModel()))) }
        }.flowOn(Dispatchers.IO)

}