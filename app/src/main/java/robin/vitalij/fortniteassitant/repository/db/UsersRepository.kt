package robin.vitalij.fortniteassitant.repository.db

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import robin.vitalij.fortniteassitant.common.extensions.getErrorModel
import robin.vitalij.fortniteassitant.db.dao.UserDao
import robin.vitalij.fortniteassitant.model.ErrorModelListItem
import robin.vitalij.fortniteassitant.model.LoadingState
import robin.vitalij.fortniteassitant.model.UserModel
import robin.vitalij.fortniteassitant.utils.mapper.UsersMapper
import javax.inject.Inject

class UsersRepository @Inject constructor(private val userDao: UserDao) {

    fun getUsers(playerId: String): Flow<LoadingState<List<UserModel>>> = flow {
        emit(LoadingState.Loading)
        kotlin.runCatching { userDao.getUsers() }
            .onSuccess { emit(LoadingState.Success(UsersMapper(playerId).transform(it))) }
            .onFailure { emit(LoadingState.Error(ErrorModelListItem.ErrorItem(it.getErrorModel()))) }
    }.flowOn(Dispatchers.IO)

}