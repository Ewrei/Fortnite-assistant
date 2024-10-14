package robin.vitalij.fortniteassitant.utils.mapper

import robin.vitalij.fortniteassitant.model.enums.AvatarType
import robin.vitalij.fortniteassitant.model.network.search.SearchResponse
import robin.vitalij.fortniteassitant.model.network.search.SearchSteamUserModel
import robin.vitalij.fortniteassitant.utils.mapper.base.Mapper

class SearchUserMapper :
    Mapper<SearchResponse, List<SearchSteamUserModel>> {

    override fun transform(obj: SearchResponse): List<SearchSteamUserModel> {
        val list = mutableListOf<SearchSteamUserModel>()

        if (obj.result) {
            obj.allMatches.forEach { matches ->
                matches.matches.firstOrNull()?.value?.let {

                    list.add(
                        SearchSteamUserModel(
                            matches.accountId,
                            it,
                            AvatarType.values().random().getImageUrl()
                        )
                    )
                }
            }
        }
        return list
    }
}