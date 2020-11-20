package robin.vitalij.fortniteassitant.api

import io.reactivex.Single
import okhttp3.Response
import retrofit2.http.GET
import retrofit2.http.Query
import robin.vitalij.fortniteassitant.model.network.search.SearchResponse
import robin.vitalij.fortniteassitant.model.network.stats.PlayerMatchesResponse

interface FortniteRequestsIOApi {

    @GET("/lookup")
    fun getSearch(
        @Query("username") username: String,
        @Query("strict") strict: Boolean
    ): Single<SearchResponse>


    @GET("v1/matches?")
    fun getMatches(@Query("account") account: String): Single<PlayerMatchesResponse>
}