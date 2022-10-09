package robin.vitalij.fortniteassitant.repository.network

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import robin.vitalij.fortniteassitant.api.FortniteRequestsIOApi
import robin.vitalij.fortniteassitant.common.extensions.getErrorModel
import robin.vitalij.fortniteassitant.model.ErrorModelListItem
import robin.vitalij.fortniteassitant.model.LoadingState
import robin.vitalij.fortniteassitant.model.network.shop.ItemShopUpcoming
import robin.vitalij.fortniteassitant.utils.LocaleUtils
import javax.inject.Inject

class GetUpcomingShopRepository @Inject constructor(
    private val fortniteRequestsIOApi: FortniteRequestsIOApi
) {

    fun getUpcomingShop(): Flow<LoadingState<List<ItemShopUpcoming>>> = flow {
        emit(LoadingState.Loading)
        kotlin.runCatching { fortniteRequestsIOApi.getUpcomingShop(LocaleUtils.locale) }
            .onSuccess { emit(LoadingState.Success(it.items)) }
            .onFailure { emit(LoadingState.Error(ErrorModelListItem.ErrorItem(it.getErrorModel()))) }
    }.flowOn(Dispatchers.IO)

}