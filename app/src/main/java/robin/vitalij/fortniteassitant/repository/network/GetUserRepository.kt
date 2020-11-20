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

class GetUserRepository @Inject constructor(
    private val fortniteRequestsIOApi: FortniteRequestsIOApi,
    private val fortniteRequestsComApi: FortniteRequestsComApi
) {

    fun getUser(playerId: String): Single<FortniteProfileResponse> {
        val playerStats = fortniteRequestsComApi.getStats(playerId, "lifetime", "all")
        val matchesPlayerStats = fortniteRequestsIOApi.getMatches(playerId)

        return Single.zip(
            playerStats,
            matchesPlayerStats,
            handleResult()
        )
            .subscribeOn(Schedulers.io())

//            .flatMap { fortniteProfileResponse ->
//            return@flatMap fortniteRequestsComApi.getStats(playerId, "season", "all").flatMap {
//                return@flatMap Single.just(fortniteProfileResponse.apply { sessionStats = it })
//            }
//        }
    }

    private fun handleResult(): BiFunction<PlayerStatsResponse, PlayerMatchesResponse, FortniteProfileResponse> =
        BiFunction { stats, matches ->
            FortniteProfileResponse(
                stats = stats,
                sessionStats = stats,
                matches = matches
            )
        }
}