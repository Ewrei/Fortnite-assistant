package robin.vitalij.fortniteassitant.repository.db

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import robin.vitalij.fortniteassitant.common.extensions.getErrorModel
import robin.vitalij.fortniteassitant.db.dao.UserDao
import robin.vitalij.fortniteassitant.model.ErrorModelListItem
import robin.vitalij.fortniteassitant.model.LoadingState
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

    fun getDetailsStatistics(
        playerId: String,
        battlesType: BattlesType,
        gameType: GameType
    ): Flow<LoadingState<List<HomeBodyStatsListItem>>> = flow {
        emit(LoadingState.Loading)
        kotlin.runCatching { userDao.getLastTwoUserEntities(playerId) }
            .onSuccess {
                emit(
                    LoadingState.Success(
                        DetailsStatisticsMapper(
                            resourceProvider,
                            battlesType,
                            gameType
                        ).transform(it)
                    )
                )
            }
            .onFailure { emit(LoadingState.Error(ErrorModelListItem.ErrorItem(it.getErrorModel()))) }
    }.flowOn(Dispatchers.IO)

}