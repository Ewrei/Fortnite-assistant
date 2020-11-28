package robin.vitalij.fortniteassitant.api

import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*
import robin.vitalij.fortniteassitant.repository.network.TopUser

private const val PATCH_CS_GO = "csgo"
private const val PATCH_SAVE_USER_STAT = "save-user-stat"
private const val PATCH_TOP_USERS = "top-users"
private const val QUERY_ATTRIBUTE = "attribute"

interface SteamChartRequestsApi {

    @FormUrlEncoded
    @POST("/$PATCH_CS_GO/$PATCH_SAVE_USER_STAT/")
    fun saveTop(@FieldMap hashMap: HashMap<String, Any>): Observable<Response<ResponseBody>>

    @GET("/$PATCH_CS_GO/$PATCH_TOP_USERS?period=mount&limit=1000")
    fun getTopUsers(@Query(QUERY_ATTRIBUTE) text: String): Observable<List<TopUser>>

}