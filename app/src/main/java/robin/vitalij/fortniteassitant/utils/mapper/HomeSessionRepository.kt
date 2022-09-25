package robin.vitalij.fortniteassitant.utils.mapper

import io.reactivex.Single
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import robin.vitalij.fortniteassitant.common.extensions.differenceUser
import robin.vitalij.fortniteassitant.db.dao.UserDao
import robin.vitalij.fortniteassitant.db.entity.UserEntity
import robin.vitalij.fortniteassitant.model.enums.BattlesType
import robin.vitalij.fortniteassitant.model.enums.GameType
import robin.vitalij.fortniteassitant.ui.home.adapter.viewholder.statistics.adapter.HomeBodyStatsListItem
import robin.vitalij.fortniteassitant.utils.ResourceProvider
import javax.inject.Inject

class HomeSessionRepository @Inject constructor(
    private val userDao: UserDao,
    private val resourceProvider: ResourceProvider
) {

    fun loadData(
        battlesType: BattlesType,
        gameType: GameType,
        sessionId: Long,
        sessionLastId: Long
    ): Single<List<HomeBodyStatsListItem>> {
        val sessionMap = userDao.getUserEntitySessionId(sessionId)
        val lastSessionMap = userDao.getUserEntitySessionId(sessionLastId)

        return Single.zip(sessionMap, lastSessionMap, handleResult()).subscribeOn(Schedulers.io())
            .flatMap {
                return@flatMap Single.just(
                    DetailsStatisticsMapper(
                        resourceProvider,
                        battlesType,
                        gameType
                    ).transform(arrayListOf(it.userEntity.differenceUser(it.userEntityLast)))
                )
            }
    }

    private fun handleResult(): BiFunction<UserEntity, UserEntity, SessionHomeModel> =
        BiFunction { sessionMap, sessionLastMap ->
            SessionHomeModel(sessionMap, sessionLastMap)
        }

    data class SessionHomeModel(
        val userEntity: UserEntity,
        val userEntityLast: UserEntity
    )
}