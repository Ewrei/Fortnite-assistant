package robin.vitalij.fortniteassitant.repository.network

import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import robin.vitalij.fortniteassitant.api.FortniteRequestsComApi
import robin.vitalij.fortniteassitant.api.FortniteRequestsIOApi
import robin.vitalij.fortniteassitant.utils.mapper.SearchUserMapper
import javax.inject.Inject

class GetSearchUserRepository @Inject constructor(
    private val fortniteRequestsIOApi: FortniteRequestsIOApi
) {

    fun getSearch(username: String) =
        fortniteRequestsIOApi.getSearch(username, false).subscribeOn(Schedulers.io()).flatMap {
            return@flatMap Single.just(SearchUserMapper().transform(it))
        }

}