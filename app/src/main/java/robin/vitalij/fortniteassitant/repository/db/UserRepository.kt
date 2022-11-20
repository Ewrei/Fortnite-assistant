package robin.vitalij.fortniteassitant.repository.db

import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import robin.vitalij.fortniteassitant.common.extensions.getErrorModel
import robin.vitalij.fortniteassitant.db.dao.SaveDao
import robin.vitalij.fortniteassitant.db.dao.UserDao
import robin.vitalij.fortniteassitant.db.entity.UserEntity
import robin.vitalij.fortniteassitant.db.projection.User
import robin.vitalij.fortniteassitant.model.ErrorModelListItem
import robin.vitalij.fortniteassitant.model.LoadingState
import robin.vitalij.fortniteassitant.model.SaveUserModel
import robin.vitalij.fortniteassitant.model.comparison.PlayerModel
import robin.vitalij.fortniteassitant.model.network.shop.ShopAdapterItem
import robin.vitalij.fortniteassitant.utils.LocaleUtils
import robin.vitalij.fortniteassitant.utils.mapper.CurrentShopMapper
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val userDao: UserDao,
    private val saveDao: SaveDao
) {

    fun saveUser(saveUserModel: SaveUserModel): Completable {
        return Completable.fromAction { saveDao.insertFullUser(saveUserModel) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getUser(playerId: String): Flow<LoadingState<UserEntity>> = flow {
        emit(LoadingState.Loading)
        kotlin.runCatching { userDao.getUserEntity(playerId) }
            .onSuccess { emit(LoadingState.Success(it)) }
            .onFailure { emit(LoadingState.Error(ErrorModelListItem.ErrorItem(it.getErrorModel()))) }
    }.flowOn(Dispatchers.IO)

    fun getFullUser(playerId: String): Maybe<User> =
        userDao.getUserFull(playerId).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    fun deleteProfile(playerId: String): Completable {
        return Completable.fromAction { userDao.deleteProfile(playerId) }
            .subscribeOn(Schedulers.io()).observeOn(
                AndroidSchedulers.mainThread()
            )
    }

    fun loadData(playerId: String): Maybe<PlayerModel> {
        return userDao.getUserFull(playerId).flatMap {
            return@flatMap Maybe.just(PlayerModel(it.userEntity))
        }
    }
}