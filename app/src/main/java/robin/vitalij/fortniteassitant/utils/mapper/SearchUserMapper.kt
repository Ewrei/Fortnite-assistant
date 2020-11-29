package robin.vitalij.fortniteassitant.utils.mapper

import robin.vitalij.fortniteassitant.model.network.search.SearchResponse
import robin.vitalij.fortniteassitant.model.network.search.SearchSteamUser
import robin.vitalij.fortniteassitant.utils.mapper.base.Mapper

class SearchUserMapper() :
    Mapper<SearchResponse, List<SearchSteamUser>> {

    override fun transform(obj: SearchResponse): List<SearchSteamUser> {
        val list = arrayListOf<SearchSteamUser>()
        obj.allMatches.forEach { matches ->
            matches.matches.firstOrNull()?.value?.let {
                list.add(SearchSteamUser(matches.accountId, it))
            }
        }
        return list
    }


}