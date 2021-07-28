package robin.vitalij.fortniteassitant.api

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query
import robin.vitalij.fortniteassitant.model.network.*
import robin.vitalij.fortniteassitant.model.network.search.SearchResponse
import robin.vitalij.fortniteassitant.model.network.shop.ShopResponse
import robin.vitalij.fortniteassitant.model.network.shop.ShopUpcomingResponse
import robin.vitalij.fortniteassitant.model.network.stats.PlayerMatchesResponse

interface FortniteRequestsIOApi {

    @GET("/v1/lookup")
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
    fun getBattlesPassRewards(
        @Query("lang") language: String,
        @Query("season") season: String
    ): Single<BattlePassRewardsResponse>

    @GET("/v1/loot/list")
    fun getWeapons(@Query("lang") language: String): Single<WeaponResponse>

    @GET("/v1/loot/fish")
    fun getFish(@Query("lang") language: String): Single<FishResponse>

    @GET("/v1/stats/fish")
    fun getFishStats(
        @Query("lang") language: String,
        @Query("accountId") accountId: String
    ): Single<FishStatsResponse>

    @GET("/v1/achievements")
    fun getAchievements(@Query("lang") language: String): Single<AchievementResponse>

    @GET("/v1/news")
    fun getNews(@Query("lang") language: String, @Query("type") type: String): Single<NewsResponse>

    @GET("/v2/game/crew")
    fun getGameCrew(@Query("lang") language: String): Single<List<CrewModel>>

}