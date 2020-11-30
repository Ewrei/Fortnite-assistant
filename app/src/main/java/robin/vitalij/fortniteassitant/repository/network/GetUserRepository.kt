package robin.vitalij.fortniteassitant.repository.network

import io.reactivex.Single
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import robin.vitalij.fortniteassitant.api.FortniteRequestsComApi
import robin.vitalij.fortniteassitant.api.FortniteRequestsIOApi
import robin.vitalij.fortniteassitant.model.network.stats.FortniteProfileResponse
import robin.vitalij.fortniteassitant.model.network.stats.PlayerMatchesResponse
import robin.vitalij.fortniteassitant.model.network.stats.PlayerStatsResponse
import javax.inject.Inject

private const val TIME_WINDOW = "lifetime"
private const val TYPE_IMAGE = "all"

class GetUserRepository @Inject constructor(
    private val fortniteRequestsIOApi: FortniteRequestsIOApi,
    private val fortniteRequestsComApi: FortniteRequestsComApi
) {

    fun getUser(playerId: String): Single<FortniteProfileResponse> {
        val playerStats = fortniteRequestsComApi.getStats(playerId, TIME_WINDOW, TYPE_IMAGE)
        val matchesPlayerStats = fortniteRequestsIOApi.getMatches(playerId)

        return Single.zip(
            playerStats,
            matchesPlayerStats,
            handleResult()
        ).subscribeOn(Schedulers.io())
    }

    private fun handleResult(): BiFunction<PlayerStatsResponse, PlayerMatchesResponse, FortniteProfileResponse> =
        BiFunction { stats, matches ->
            FortniteProfileResponse(
                stats = stats,
                matches = matches
            )
        }
}