package robin.vitalij.fortniteassitant.repository.network

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import robin.vitalij.fortniteassitant.api.FortniteRequestsIOApi
import robin.vitalij.fortniteassitant.common.extensions.getErrorModel
import robin.vitalij.fortniteassitant.model.ErrorModelListItem
import robin.vitalij.fortniteassitant.model.LoadingState
import robin.vitalij.fortniteassitant.model.network.shop.ShopAdapterItem
import robin.vitalij.fortniteassitant.utils.LocaleUtils
import robin.vitalij.fortniteassitant.utils.mapper.CurrentShopMapper
import javax.inject.Inject

class GetCurrentShopRepository @Inject constructor(
    private val fortniteRequestsIOApi: FortniteRequestsIOApi
) {

    fun getCurrentShop(): Flow<LoadingState<List<ShopAdapterItem>>> = flow {
        emit(LoadingState.Loading)
        kotlin.runCatching { fortniteRequestsIOApi.getCurrentShop(LocaleUtils.locale) }
            .onSuccess { emit(LoadingState.Success(CurrentShopMapper().transform(it))) }
            .onFailure { emit(LoadingState.Error(ErrorModelListItem.ErrorItem(it.getErrorModel()))) }
    }.flowOn(Dispatchers.IO)

}