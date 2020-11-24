package robin.vitalij.fortniteassitant.repository.db

import io.reactivex.Flowable
import robin.vitalij.fortniteassitant.db.dao.UserDao
import robin.vitalij.fortniteassitant.model.FullHomeModel
import robin.vitalij.fortniteassitant.model.enums.BattlesType
import robin.vitalij.fortniteassitant.model.enums.GameType
import robin.vitalij.fortniteassitant.ui.home.adapter.viewholder.statistics.adapter.viewmodel.HomeBodyStats
import robin.vitalij.fortniteassitant.utils.mapper.DetailsStatisticsMapper
import robin.vitalij.fortniteassitant.utils.view.ResourceProvider
import javax.inject.Inject

class DetailsStatisticsRepository @Inject constructor(
    private val userDao: UserDao,
    private val resourceProvider: ResourceProvider
) {

    fun loadData(
        playerId: String,
        battlesType: BattlesType,
        gameType: GameType
    ): Flowable<List<HomeBodyStats>> =
        userDao.getLastTwoUserEntities(playerId).flatMap {
            return@flatMap Flowable.just(
                DetailsStatisticsMapper(
                    resourceProvider,
                    battlesType,
                    gameType
                ).transform(it)
            )
        }
}