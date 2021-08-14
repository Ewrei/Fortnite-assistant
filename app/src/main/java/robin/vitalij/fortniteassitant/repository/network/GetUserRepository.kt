package robin.vitalij.fortniteassitant.repository.network

import io.reactivex.Single
import robin.vitalij.fortniteassitant.api.FortniteRequestsComApi
import robin.vitalij.fortniteassitant.model.network.stats.FortniteProfileResponse
import javax.inject.Inject

private const val TIME_WINDOW = "lifetime"
private const val TYPE_IMAGE = "all"

class GetUserRepository @Inject constructor(
    private val fortniteRequestsComApi: FortniteRequestsComApi
) {

    fun getUser(playerId: String): Single<FortniteProfileResponse> {
        return fortniteRequestsComApi.getStats(playerId, TIME_WINDOW, TYPE_IMAGE).flatMap {
            return@flatMap Single.just(
                FortniteProfileResponse(
                    stats = it
                )
            )
        }
    }
}