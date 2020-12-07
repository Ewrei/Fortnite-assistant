package robin.vitalij.fortniteassitant.repository.network

import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import robin.vitalij.fortniteassitant.api.FortniteRequestsComApi
import robin.vitalij.fortniteassitant.db.entity.UserEntity
import robin.vitalij.fortniteassitant.model.enums.BattlesType
import robin.vitalij.fortniteassitant.model.enums.GameType
import robin.vitalij.fortniteassitant.ui.home.adapter.viewholder.statistics.adapter.viewmodel.HomeBodyStats
import robin.vitalij.fortniteassitant.utils.mapper.DetailsStatisticsMapper
import robin.vitalij.fortniteassitant.utils.ResourceProvider
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetSeasonStatisticsRepository @Inject constructor(
    private val fortniteRequestsComApi: FortniteRequestsComApi,
    private val resourceProvider: ResourceProvider
) {

    var seasonStats: BehaviorSubject<UserEntity> = BehaviorSubject.create()

    fun getSeasonStats(playerId: String) =
        fortniteRequestsComApi.getStats(playerId, "season", "all")
            .subscribeOn(Schedulers.io())

    fun loadData(battlesType: BattlesType, gameType: GameType): Observable<List<HomeBodyStats>> =
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

}