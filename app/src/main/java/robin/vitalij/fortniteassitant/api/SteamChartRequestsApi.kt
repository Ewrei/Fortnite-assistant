package robin.vitalij.fortniteassitant.api

import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*
import robin.vitalij.fortniteassitant.model.network.TopUser
import robin.vitalij.fortniteassitant.model.network.stats.PlayerStatsResponse

private const val PATCH_FORTNITE = "fortnite"
private const val PATCH_SAVE_USER_STAT = "save-user-stat"
private const val PATCH_TOP_USERS = "top-users"
private const val QUERY_ATTRIBUTE = "attribute"
private const val QUERY_TYPE = "type"
private const val QUERY_PLATFORM = "platform"

interface SteamChartRequestsApi {

    @Headers("Content-Type: application/json")
    @POST("/$PATCH_FORTNITE/$PATCH_SAVE_USER_STAT")
    fun saveTop(@Body body: PlayerStatsResponse): Observable<Response<ResponseBody>>

    @GET("/$PATCH_FORTNITE/$PATCH_TOP_USERS?period=mount&limit=1000")
    suspend fun getTopUsers(
        @Query(QUERY_ATTRIBUTE) attribute: String,
        @Query(QUERY_TYPE) type: String,
        @Query(QUERY_PLATFORM) platform: String
    ): List<TopUser>

}