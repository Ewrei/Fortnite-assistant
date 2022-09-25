package robin.vitalij.fortniteassitant.repository.db

import io.reactivex.Flowable
import robin.vitalij.fortniteassitant.db.dao.UserDao
import robin.vitalij.fortniteassitant.model.enums.BattlesType
import robin.vitalij.fortniteassitant.model.enums.GameType
import robin.vitalij.fortniteassitant.ui.home.adapter.viewholder.statistics.adapter.HomeBodyStatsListItem
import robin.vitalij.fortniteassitant.utils.ResourceProvider
import robin.vitalij.fortniteassitant.utils.mapper.DetailsStatisticsMapper
import javax.inject.Inject

class DetailsStatisticsRepository @Inject constructor(
    private val userDao: UserDao,
    private val resourceProvider: ResourceProvider
) {

    fun loadData(
        playerId: String,
        battlesType: BattlesType,
        gameType: GameType
    ): Flowable<List<HomeBodyStatsListItem>> =
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