package robin.vitalij.fortniteassitant.repository.network

import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import robin.vitalij.fortniteassitant.api.FortniteRequestsComApi
import robin.vitalij.fortniteassitant.common.extensions.getErrorModel
import robin.vitalij.fortniteassitant.db.entity.UserEntity
import robin.vitalij.fortniteassitant.model.ErrorModelListItem
import robin.vitalij.fortniteassitant.model.LoadingState
import robin.vitalij.fortniteassitant.model.enums.BattlesType
import robin.vitalij.fortniteassitant.model.enums.GameType
import robin.vitalij.fortniteassitant.model.network.stats.PlayerStatsResponse
import robin.vitalij.fortniteassitant.ui.home.adapter.viewholder.statistics.adapter.HomeBodyStatsListItem
import robin.vitalij.fortniteassitant.utils.ResourceProvider
import robin.vitalij.fortniteassitant.utils.mapper.DetailsStatisticsMapper
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetSeasonStatisticsRepository @Inject constructor(
    private val fortniteRequestsComApi: FortniteRequestsComApi,
    private val resourceProvider: ResourceProvider
) {

    var seasonStats: BehaviorSubject<UserEntity> = BehaviorSubject.create()

    fun getSeasonStatsNewVersion(playerId: String): Flow<LoadingState<PlayerStatsResponse>> =
        flow {
            emit(LoadingState.Loading)
            kotlin.runCatching {
                fortniteRequestsComApi.getStatsNew(
                    playerId, TIME_WINDOW_QUERY, IMAGE_QUERY
                )
            }.onSuccess {
                emit(
                    LoadingState.Success(
                        it
                    )
                )
            }
                .onFailure { emit(LoadingState.Error(ErrorModelListItem.ErrorItem(it.getErrorModel()))) }
        }.flowOn(Dispatchers.IO)

    fun loadData(
        battlesType: BattlesType,
        gameType: GameType
    ): Observable<List<HomeBodyStatsListItem>> =
        seasonStats
            .subscribeOn(Schedulers.io())
            .flatMap {
                return@flatMap Observable.just(
                    DetailsStatisticsMapper(
                        resourceProvider,
                        battlesType,
                        gameType
                    ).transform(arrayListOf(it))
                )
            }

    companion object {
        private const val TIME_WINDOW_QUERY = "season"
        private const val IMAGE_QUERY = "all"
    }

}