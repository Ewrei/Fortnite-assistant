package robin.vitalij.fortniteassitant.repository.network

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import robin.vitalij.fortniteassitant.api.SteamChartRequestsApi
import robin.vitalij.fortniteassitant.model.TopFullModel
import robin.vitalij.fortniteassitant.model.network.stats.PlayerStatsResponse
import robin.vitalij.fortniteassitant.utils.mapper.TopUserMapper
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TopRepository @Inject constructor(
    private val steamChartRequestsApi: SteamChartRequestsApi
) {
    val disposables = CompositeDisposable()

    fun updateTopUser(playerStatsResponse: PlayerStatsResponse) {
        steamChartRequestsApi.saveTop(playerStatsResponse)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({
                //do nothing
            }, {
                //do nothing
            })
            .let(disposables::add)
    }

    fun getTopUsers(topType: TopFullModel) = steamChartRequestsApi.getTopUsers(
        topType.topType.id,
        topType.battlesType.getServer(),
        topType.gameType.getServer()
    ).flatMap {
        return@flatMap Observable.just(TopUserMapper(topType).transform(it))
    }
}