package robin.vitalij.fortniteassitant.api

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query
import robin.vitalij.fortniteassitant.model.network.NewsResponse

interface FortniteRequestsApi {

    @GET("/v2/news")
    fun getNews(): Observable<NewsResponse>

}