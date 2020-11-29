package robin.vitalij.fortniteassitant.repository.network

import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import robin.vitalij.fortniteassitant.api.FortniteRequestsIOApi
import robin.vitalij.fortniteassitant.utils.LocaleUtils
import robin.vitalij.fortniteassitant.utils.mapper.InventoryMapperMapper
import robin.vitalij.fortniteassitant.utils.view.ResourceProvider
import javax.inject.Inject

class GetCurrentShopRepository @Inject constructor(
    private val fortniteRequestsIOApi: FortniteRequestsIOApi,
    private val resourceProvider: ResourceProvider
) {

    fun getCurrentShop() =
        fortniteRequestsIOApi.getCurrentShop(LocaleUtils.locale).subscribeOn(Schedulers.io())
            .flatMap {
                return@flatMap Single.just(InventoryMapperMapper(resourceProvider).transform(it))
            }

}