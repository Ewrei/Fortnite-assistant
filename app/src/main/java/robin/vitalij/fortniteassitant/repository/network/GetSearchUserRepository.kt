package robin.vitalij.fortniteassitant.repository.network

import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import robin.vitalij.fortniteassitant.api.FortniteRequestsIOApi
import robin.vitalij.fortniteassitant.model.enums.AvatarType
import robin.vitalij.fortniteassitant.model.network.search.SearchSteamUser
import robin.vitalij.fortniteassitant.utils.mapper.SearchUserMapper
import javax.inject.Inject

class GetSearchUserRepository @Inject constructor(
    private val fortniteRequestsIOApi: FortniteRequestsIOApi
) {

    fun getSearch(username: String, strict: Boolean) =
        if (!strict) {
            fortniteRequestsIOApi.getSearch(username, strict).subscribeOn(Schedulers.io()).flatMap {
                return@flatMap Single.just(SearchUserMapper().transform(it))
            }
        } else {
            fortniteRequestsIOApi.getSearch(username).subscribeOn(Schedulers.io()).flatMap {
                return@flatMap Single.just(
                    if (it.result) {
                        arrayListOf<SearchSteamUser>().apply {
                            add(
                                SearchSteamUser(
                                    accountId = it.accountId,
                                    name = username,
                                    AvatarType.values().random().getImageUrl()
                                )
                            )
                        }
                    } else {
                        arrayListOf()
                    }
                )
            }
        }

}