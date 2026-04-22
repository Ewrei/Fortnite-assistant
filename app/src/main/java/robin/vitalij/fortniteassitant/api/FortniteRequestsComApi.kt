package robin.vitalij.fortniteassitant.api

import io.reactivex.Single
import okhttp3.Response
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import robin.vitalij.fortniteassitant.model.network.BannerResponse
import robin.vitalij.fortniteassitant.model.network.CosmeticsNewResponse
import robin.vitalij.fortniteassitant.model.network.CosmeticsResponse
import robin.vitalij.fortniteassitant.model.network.NewsResponse
import robin.vitalij.fortniteassitant.model.network.shop.ShopNewResponse
import robin.vitalij.fortniteassitant.model.network.stats.PlayerStatsResponse

const val VERSION_SECOND_PATCH = "v2"
const val VERSION_FIRST_PATCH = "v1"
const val BR_PATCH = "br"
const val ACCOUNT_ID_PATCH = "accountId"
const val COSMETICS_PATCH = "cosmetics"
const val BANNERS_PATCH = "banners"
const val NEW_PATCH = "new"
const val STATS_PATCH = "stats"

const val LANGUAGE_QUERY = "language"
const val TIME_WINDOW_QUERY = "timeWindow"
const val IMAGE_QUERY = "image"

interface FortniteRequestsComApi {

    @GET("/v2/shop")
    suspend fun getCurrentShop(@Query("language") language: String): ShopNewResponse

    @GET("/v2/news")
    suspend fun getNews(
        @Query("language") language: String
    ): NewsResponse

    @GET("/$VERSION_SECOND_PATCH/$STATS_PATCH/$BR_PATCH/$VERSION_SECOND_PATCH/{$ACCOUNT_ID_PATCH}")
    fun getStats(
        @Path(ACCOUNT_ID_PATCH) accountId: String,
        @Query(TIME_WINDOW_QUERY) timeWindow: String,
        @Query(IMAGE_QUERY) image: String
    ): Single<PlayerStatsResponse>

    @GET("/$VERSION_SECOND_PATCH/$STATS_PATCH/$BR_PATCH/$VERSION_SECOND_PATCH/{$ACCOUNT_ID_PATCH}")
    suspend fun getStatsNew(
        @Path(ACCOUNT_ID_PATCH) accountId: String,
        @Query(TIME_WINDOW_QUERY) timeWindow: String,
        @Query(IMAGE_QUERY) image: String
    ): PlayerStatsResponse

    @GET("/$VERSION_SECOND_PATCH/$COSMETICS_PATCH/$BR_PATCH/$NEW_PATCH")
    suspend fun getCosmeticsNew(@Query(LANGUAGE_QUERY) language: String): CosmeticsNewResponse

    @GET("/$VERSION_SECOND_PATCH/$COSMETICS_PATCH/$BR_PATCH")
    suspend fun getCosmetics(@Query(LANGUAGE_QUERY) language: String): CosmeticsResponse

    @GET("/$VERSION_FIRST_PATCH/$BANNERS_PATCH")
    suspend fun getBanners(@Query(LANGUAGE_QUERY) language: String): BannerResponse

}