package robin.vitalij.fortniteassitant.repository.db

import io.reactivex.Flowable
import robin.vitalij.fortniteassitant.db.dao.UserDao
import robin.vitalij.fortniteassitant.ui.users.adapter.UserModel
import robin.vitalij.fortniteassitant.utils.mapper.UsersMapper
import javax.inject.Inject

class UsersRepository @Inject constructor(private val userDao: UserDao) {

    fun loadData(playerId: String): Flowable<List<UserModel>> =
        userDao.getUsers().flatMap {
            return@flatMap Flowable.just(UsersMapper(playerId).transform(it))
        }
}