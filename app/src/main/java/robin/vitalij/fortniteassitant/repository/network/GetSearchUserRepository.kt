package robin.vitalij.fortniteassitant.repository.network

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import robin.vitalij.fortniteassitant.api.FortniteProdRequestApi
import robin.vitalij.fortniteassitant.api.FortniteRequestsComApi
import robin.vitalij.fortniteassitant.common.extensions.getErrorMessage
import robin.vitalij.fortniteassitant.model.LoadingState
import robin.vitalij.fortniteassitant.model.enums.AvatarType
import robin.vitalij.fortniteassitant.model.network.search.SearchSteamUserModel
import robin.vitalij.fortniteassitant.network.ApiError
import robin.vitalij.fortniteassitant.utils.ResourceProvider
import javax.inject.Inject

class GetSearchUserRepository @Inject constructor(
    private val fortniteProdRequestApi: FortniteProdRequestApi,
    private val resourceProvider: ResourceProvider,
    private val fortniteRequestsComApi: FortniteRequestsComApi
) {

    fun getSearch(username: String): Flow<LoadingState<List<SearchSteamUserModel>>> =
        flow {
            emit(LoadingState.Loading)

            kotlin.runCatching { fortniteProdRequestApi.getSearch(username) }
                .onSuccess { searchResult ->
                    kotlin.runCatching {
                        fortniteRequestsComApi.getStatsNew(
                            searchResult.id,
                            TIME_WINDOW,
                            TYPE_IMAGE
                        )
                    }
                        .onSuccess {
                            emit(
                                LoadingState.Success(
                                    mutableListOf<SearchSteamUserModel>(
                                        SearchSteamUserModel(
                                            accountId = searchResult.id,
                                            name = searchResult.displayName,
                                            avatarImage = AvatarType.entries.toTypedArray().random()
                                                .getImageUrl(),
                                            playerStatsData = it.playerStatsData
                                        )
                                    )
                                )
                            )
                        }
                        .onFailure {
                            emit(LoadingState.Error(it.getErrorMessage(false, resourceProvider)))
                        }
                }
                .onFailure { error ->
                    val is404 = (error as? HttpException)?.code() == ApiError.NOT_FOUND.code
                    if (is404) {
                        emit(LoadingState.Success(emptyList<SearchSteamUserModel>()))
                    } else {
                        emit(LoadingState.Error(error.getErrorMessage(false, resourceProvider)))
                    }
                }

        }.flowOn(Dispatchers.IO)

}