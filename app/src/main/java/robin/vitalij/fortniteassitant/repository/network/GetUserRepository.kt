package robin.vitalij.fortniteassitant.repository.network

import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import robin.vitalij.fortniteassitant.api.FortniteRequestsComApi
import robin.vitalij.fortniteassitant.common.extensions.getErrorModel
import robin.vitalij.fortniteassitant.model.ErrorModelListItem
import robin.vitalij.fortniteassitant.model.LoadingState
import robin.vitalij.fortniteassitant.model.network.stats.FortniteProfileResponse
import javax.inject.Inject

private const val TIME_WINDOW = "lifetime"
private const val TYPE_IMAGE = "all"

class GetUserRepository @Inject constructor(
    private val fortniteRequestsComApi: FortniteRequestsComApi
) {

    fun getUser(playerId: String): Single<FortniteProfileResponse> {
        return fortniteRequestsComApi.getStats(playerId, TIME_WINDOW, TYPE_IMAGE)
            .subscribeOn(Schedulers.io()).flatMap {
                return@flatMap Single.just(
                    FortniteProfileResponse(
                        stats = it
                    )
                )
            }
    }

    fun getUserNewVersion(playerId: String): Flow<LoadingState<FortniteProfileResponse>> =
        flow {
            emit(LoadingState.Loading)
            kotlin.runCatching {
                fortniteRequestsComApi.getStatsNew(
                    playerId,
                    TIME_WINDOW,
                    TYPE_IMAGE
                )
            }.onSuccess {
                emit(
                    LoadingState.Success(
                        FortniteProfileResponse(
                            it
                        )
                    )
                )
            }
                .onFailure { emit(LoadingState.Error(ErrorModelListItem.ErrorItem(it.getErrorModel()))) }
        }.flowOn(Dispatchers.IO)
}