package robin.vitalij.fortniteassitant.repository.db

import io.reactivex.Flowable
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import robin.vitalij.fortniteassitant.db.dao.UserDao
import robin.vitalij.fortniteassitant.db.entity.UserEntity
import robin.vitalij.fortniteassitant.db.projection.UserHistory
import robin.vitalij.fortniteassitant.model.FullHomeModel
import robin.vitalij.fortniteassitant.utils.mapper.HomeMapper
import robin.vitalij.fortniteassitant.utils.ResourceProvider
import javax.inject.Inject

class HomeRepository @Inject constructor(
    private val userDao: UserDao,
    private val resourceProvider: ResourceProvider
) {

    fun loadData(playerId: String): Flowable<FullHomeModel> =
        Flowable.combineLatest(
            userDao.getLastTwoUserEntities(playerId),
            userDao.getUserHistory(playerId),
            handleResult()
        ).subscribeOn(Schedulers.io())


    private fun handleResult(): BiFunction<List<UserEntity>, List<UserHistory>, FullHomeModel> =
        BiFunction { users, histories ->
            HomeMapper(resourceProvider, histories).transform(users)
        }
}