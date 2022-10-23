package robin.vitalij.fortniteassitant.api

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query
import robin.vitalij.fortniteassitant.model.network.*
import robin.vitalij.fortniteassitant.model.network.search.SearchResponse
import robin.vitalij.fortniteassitant.model.network.search.SearchUserModel
import robin.vitalij.fortniteassitant.model.network.shop.ShopNewResponse
import robin.vitalij.fortniteassitant.model.network.shop.ShopUpcomingResponse

const val LOOKUP_PATCH = "lookup"
const val LOOT_PATCH = "loot"
const val FISH_PATCH = "fish"
const val GAME_PATCH = "game"
const val SHOP_PATCH = "shop"
const val ITEMS_PATCH = "items"
const val UPCOMING_PATCH = "upcoming"
const val BATTLE_PASS_PATCH = "battlepass"
const val NEWS_PATCH = "news"
const val ACHIEVEMENTS_PATCH = "achievements"
const val CREW_PATCH = "crew"
const val VEHICLES_PATCH = "vehicles"
const val LIST_PATCH = "list"

private const val LANG_QUERY = "lang"
private const val USERNAME_QUERY = "username"
private const val STRICT_QUERY = "strict"
private const val TYPE_QUERY = "type"
private const val SEASON_QUERY = "season"

interface FortniteRequestsIOApi {

    @GET("/$VERSION_FIRST_PATCH/$LOOKUP_PATCH")
    fun getSearch(
        @Query(USERNAME_QUERY) username: String,
        @Query(STRICT_QUERY) strict: Boolean
    ): Single<SearchResponse>

    @GET("/$VERSION_FIRST_PATCH/$LOOKUP_PATCH")
    fun getSearch(
        @Query(USERNAME_QUERY) username: String
    ): Single<SearchUserModel>

    @GET("/$VERSION_SECOND_PATCH/$SHOP_PATCH")
    suspend fun getCurrentShop(@Query(LANG_QUERY) language: String): ShopNewResponse

    @GET("/$VERSION_SECOND_PATCH/$ITEMS_PATCH/$UPCOMING_PATCH")
    suspend fun getUpcomingShop(@Query(LANG_QUERY) language: String): ShopUpcomingResponse

    @GET("/$VERSION_FIRST_PATCH/$BATTLE_PASS_PATCH")
    fun getBattlesPassRewards(
        @Query(LANG_QUERY) language: String,
        @Query(SEASON_QUERY) season: String
    ): Single<BattlePassRewardsResponse>

    @GET("/$VERSION_FIRST_PATCH/$LOOT_PATCH/$LIST_PATCH")
    fun getWeapons(@Query(LANG_QUERY) language: String): Single<WeaponResponse>

    @GET("/$VERSION_FIRST_PATCH/$LOOT_PATCH/$FISH_PATCH")
    fun getFish(@Query(LANG_QUERY) language: String): Single<FishResponse>

    @GET("/$VERSION_FIRST_PATCH/$STATS_PATCH/$FISH_PATCH")
    fun getFishStats(
        @Query(LANG_QUERY) language: String,
        @Query(ACCOUNT_ID_PATCH) accountId: String
    ): Single<FishStatsResponse>

    @GET("/$VERSION_FIRST_PATCH/$ACHIEVEMENTS_PATCH")
    fun getAchievements(@Query(LANG_QUERY) language: String): Single<AchievementResponse>

    @GET("/$VERSION_FIRST_PATCH/$NEWS_PATCH")
    suspend fun getNews(
        @Query(LANG_QUERY) language: String,
        @Query(TYPE_QUERY) type: String
    ): NewsResponse

    @GET("/$VERSION_SECOND_PATCH/$GAME_PATCH/$CREW_PATCH")
    suspend fun getGameCrew(@Query(LANG_QUERY) language: String): List<CrewModel>

    @GET("/$VERSION_SECOND_PATCH/$GAME_PATCH/$VEHICLES_PATCH")
    suspend fun getGameVehicles(@Query(LANG_QUERY) language: String): VehiclesResponse

}