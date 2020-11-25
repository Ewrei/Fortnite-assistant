package robin.vitalij.fortniteassitant.repository.db

import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import robin.vitalij.fortniteassitant.db.dao.SaveDao
import robin.vitalij.fortniteassitant.db.dao.UserDao
import robin.vitalij.fortniteassitant.db.projection.User
import robin.vitalij.fortniteassitant.model.SaveUserModel
import robin.vitalij.fortniteassitant.model.comparison.PlayerModel
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

    fun getUser(playerId: String) = userDao.getFlowableUserEntity(playerId)

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