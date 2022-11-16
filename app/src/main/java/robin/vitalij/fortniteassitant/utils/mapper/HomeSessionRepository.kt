package robin.vitalij.fortniteassitant.utils.mapper

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import robin.vitalij.fortniteassitant.common.extensions.differenceUser
import robin.vitalij.fortniteassitant.common.extensions.getErrorModel
import robin.vitalij.fortniteassitant.db.dao.UserDao
import robin.vitalij.fortniteassitant.db.entity.UserEntity
import robin.vitalij.fortniteassitant.model.ErrorModelListItem
import robin.vitalij.fortniteassitant.model.LoadingState
import robin.vitalij.fortniteassitant.model.enums.BattlesType
import robin.vitalij.fortniteassitant.model.enums.GameType
import robin.vitalij.fortniteassitant.ui.home.adapter.viewholder.statistics.adapter.HomeBodyStatsListItem
import robin.vitalij.fortniteassitant.utils.ResourceProvider
import javax.inject.Inject

class HomeSessionRepository @Inject constructor(
    private val userDao: UserDao,
    private val resourceProvider: ResourceProvider
) {

    fun getDetailsStatistics(
        battlesType: BattlesType,
        gameType: GameType,
        sessionId: Long,
        sessionLastId: Long
    ): Flow<LoadingState<List<HomeBodyStatsListItem>>> =
        getSessionId(sessionId).combine(getSessionId(sessionLastId)) { session, lastSession ->
            if (session is LoadingState.Success && lastSession is LoadingState.Success) {
                return@combine LoadingState.Success(
                    DetailsStatisticsMapper(
                        resourceProvider,
                        battlesType,
                        gameType
                    ).transform(mutableListOf(session.data.differenceUser(lastSession.data)))
                )
            } else if (session is LoadingState.Error) {
                return@combine session
            } else if (lastSession is LoadingState.Error) {
                return@combine lastSession
            } else LoadingState.Loading

        }

    private fun getSessionId(sessionId: Long): Flow<LoadingState<UserEntity>> = flow {
        emit(LoadingState.Loading)
        kotlin.runCatching { userDao.getUserEntitySessionIdNewVersion(sessionId) }
            .onSuccess { emit(LoadingState.Success(it)) }
            .onFailure { emit(LoadingState.Error(ErrorModelListItem.ErrorItem(it.getErrorModel()))) }
    }.flowOn(Dispatchers.IO)

}