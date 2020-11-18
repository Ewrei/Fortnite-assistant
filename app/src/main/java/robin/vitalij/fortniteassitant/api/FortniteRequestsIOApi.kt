package robin.vitalij.fortniteassitant.api

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query
import robin.vitalij.fortniteassitant.model.network.search.SearchResponse

interface FortniteRequestsIOApi {

    @GET("/lookup")
    fun getSearch(
        @Query("username") username: String,
        @Query("strict") strict: Boolean
    ): Single<SearchResponse>

}