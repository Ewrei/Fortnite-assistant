package robin.vitalij.fortniteassitant.api

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query
import robin.vitalij.fortniteassitant.model.network.BattlePassRewardsResponse
import robin.vitalij.fortniteassitant.model.network.FishResponse
import robin.vitalij.fortniteassitant.model.network.FishStatsResponse
import robin.vitalij.fortniteassitant.model.network.WeaponResponse
import robin.vitalij.fortniteassitant.model.network.search.SearchResponse
import robin.vitalij.fortniteassitant.model.network.shop.ShopResponse
import robin.vitalij.fortniteassitant.model.network.shop.ShopUpcomingResponse
import robin.vitalij.fortniteassitant.model.network.stats.PlayerMatchesResponse

interface FortniteRequestsIOApi {

    @GET("/lookup")
    fun getSearch(
        @Query("username") username: String,
        @Query("strict") strict: Boolean
    ): Single<SearchResponse>


    @GET("v1/matches")
    fun getMatches(@Query("account") account: String): Single<PlayerMatchesResponse>

    @GET("/v1/shop")
    fun getCurrentShop(@Query("lang") language: String): Single<ShopResponse>

    @GET("/v1/items/upcoming")
    fun getUpcomingShop(@Query("lang") language: String): Single<ShopUpcomingResponse>

    @GET("/v1/battlepass")
    fun getBattlesPassRewards(@Query("lang") language: String, @Query("season") season: String): Single<BattlePassRewardsResponse>

    @GET("/v1/loot/list")
    fun getWeapons(@Query("lang") language: String): Single<WeaponResponse>

    @GET("/v1/loot/fish")
    fun getFish(@Query("lang") language: String): Single<FishResponse>

    @GET("/v1/stats/fish")
    fun getFishStats(@Query("lang") language: String, @Query("accountId") accountId: String): Single<FishStatsResponse>
}