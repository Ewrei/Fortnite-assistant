package robin.vitalij.fortniteassitant.api

import retrofit2.http.GET
import retrofit2.http.Path
import robin.vitalij.fortniteassitant.model.network.search.SearchNewUserModel

interface FortniteProdRequestApi {

    @GET("/api/v1/account/displayName/{username}")
    suspend fun getSearch(@Path("username") username: String): SearchNewUserModel

}