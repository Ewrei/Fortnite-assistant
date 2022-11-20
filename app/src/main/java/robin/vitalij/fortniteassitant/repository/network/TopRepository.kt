package robin.vitalij.fortniteassitant.repository.network

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import robin.vitalij.fortniteassitant.api.SteamChartRequestsApi
import robin.vitalij.fortniteassitant.common.extensions.getErrorModel
import robin.vitalij.fortniteassitant.model.ErrorModelListItem
import robin.vitalij.fortniteassitant.model.LoadingState
import robin.vitalij.fortniteassitant.model.TopFullModel
import robin.vitalij.fortniteassitant.model.network.stats.PlayerStatsResponse
import robin.vitalij.fortniteassitant.repository.storage.PreferenceManager
import robin.vitalij.fortniteassitant.ui.top.adapter.TopListItem
import robin.vitalij.fortniteassitant.utils.ResourceProvider
import robin.vitalij.fortniteassitant.utils.mapper.TopUserMapper
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TopRepository @Inject constructor(
    private val steamChartRequestsApi: SteamChartRequestsApi,
    private val resourceProvider: ResourceProvider,
    private val preferenceManager: PreferenceManager
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

    fun getTopUsers(topType: TopFullModel): Flow<LoadingState<List<TopListItem>>> = flow {
        emit(LoadingState.Loading)
        kotlin.runCatching {
            steamChartRequestsApi.getTopUsers(
                topType.topType.id,
                topType.battlesType.getServer(),
                topType.gameType.getServer()
            )
        }.onSuccess {
            emit(
                LoadingState.Success(
                    TopUserMapper(
                        topType,
                        preferenceManager.getPlayerId(),
                        resourceProvider
                    ).transform(it)
                )
            )
        }.onFailure { emit(LoadingState.Error(ErrorModelListItem.ErrorItem(it.getErrorModel()))) }
    }.flowOn(Dispatchers.IO)

}