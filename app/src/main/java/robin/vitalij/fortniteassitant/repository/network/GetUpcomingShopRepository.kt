package robin.vitalij.fortniteassitant.repository.network

import io.reactivex.schedulers.Schedulers
import robin.vitalij.fortniteassitant.api.FortniteRequestsIOApi
import robin.vitalij.fortniteassitant.utils.LocaleUtils
import javax.inject.Inject

class GetUpcomingShopRepository @Inject constructor(
    private val fortniteRequestsIOApi: FortniteRequestsIOApi
) {

    fun getUpcomingShop() =
        fortniteRequestsIOApi.getUpcomingShop(LocaleUtils.locale).subscribeOn(Schedulers.io())

}