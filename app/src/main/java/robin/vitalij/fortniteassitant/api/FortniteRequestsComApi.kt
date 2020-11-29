package robin.vitalij.fortniteassitant.api

import com.squareup.okhttp.ResponseBody
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import robin.vitalij.fortniteassitant.model.network.NewsResponse
import robin.vitalij.fortniteassitant.model.network.stats.PlayerStatsResponse

interface FortniteRequestsComApi {

    @GET("/v2/news")
    fun getNews(): Observable<NewsResponse>

    @GET("v1/stats/br/v2/{accountId}")
    fun getStats(
        @Path("accountId") accountId: String,
        @Query("timeWindow") timeWindow: String,
        @Query("image") image: String
    ): Single<PlayerStatsResponse>

    @GET("/v2/shop/br/combined")
    fun getShop( @Query("language") language: String): Single<ResponseBody>

}