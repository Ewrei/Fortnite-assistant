package robin.vitalij.fortniteassitant.repository.network

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import robin.vitalij.fortniteassitant.api.FortniteRequestsIOApi
import robin.vitalij.fortniteassitant.common.extensions.getErrorModel
import robin.vitalij.fortniteassitant.model.ErrorModelListItem
import robin.vitalij.fortniteassitant.model.LoadingState
import robin.vitalij.fortniteassitant.model.enums.AvatarType
import robin.vitalij.fortniteassitant.model.network.search.SearchSteamUser
import robin.vitalij.fortniteassitant.utils.mapper.SearchUserMapper
import javax.inject.Inject

class GetSearchUserRepository @Inject constructor(
    private val fortniteRequestsIOApi: FortniteRequestsIOApi
) {

    fun getSearch(username: String, strict: Boolean) = if (!strict) {
        getSearchWithStrict(username, strict)
    } else {
        getSearchWithoutStrict(username)
    }

    private fun getSearchWithStrict(
        username: String,
        strict: Boolean
    ): Flow<LoadingState<List<SearchSteamUser>>> = flow {
        emit(LoadingState.Loading)
        kotlin.runCatching { fortniteRequestsIOApi.getSearch(username, strict) }
            .onSuccess {
                emit(LoadingState.Success(SearchUserMapper().transform(it)))
            }
            .onFailure { emit(LoadingState.Error(ErrorModelListItem.ErrorItem(it.getErrorModel()))) }
    }.flowOn(Dispatchers.IO)

    private fun getSearchWithoutStrict(username: String): Flow<LoadingState<List<SearchSteamUser>>> =
        flow {
            emit(LoadingState.Loading)
            kotlin.runCatching { fortniteRequestsIOApi.getSearch(username) }
                .onSuccess {
                    emit(LoadingState.Success(if (it.result) {
                        mutableListOf<SearchSteamUser>().apply {
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
                    }))
                }
                .onFailure { emit(LoadingState.Error(ErrorModelListItem.ErrorItem(it.getErrorModel()))) }
        }.flowOn(Dispatchers.IO)

}