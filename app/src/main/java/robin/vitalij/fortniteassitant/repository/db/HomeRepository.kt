package robin.vitalij.fortniteassitant.repository.db

import io.reactivex.Flowable
import robin.vitalij.fortniteassitant.db.dao.UserDao
import robin.vitalij.fortniteassitant.ui.home.adapter.viewmodel.Home
import robin.vitalij.fortniteassitant.utils.mapper.HomeMapper
import robin.vitalij.fortniteassitant.utils.view.ResourceProvider
import javax.inject.Inject

class HomeRepository @Inject constructor(
    private val userDao: UserDao,
    private val resourceProvider: ResourceProvider
) {

    fun loadData(playerId: String): Flowable<List<Home>> =
        userDao.getLastTwoUserEntities(playerId).flatMap {
            return@flatMap Flowable.just(HomeMapper(resourceProvider).transform(it))
        }
}