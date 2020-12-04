package robin.vitalij.fortniteassitant.repository

import io.reactivex.schedulers.Schedulers
import robin.vitalij.fortniteassitant.api.FortniteRequestsIOApi
import robin.vitalij.fortniteassitant.utils.LocaleUtils
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class FishStatsRepository @Inject constructor(
    private val fortniteRequestsIOApi: FortniteRequestsIOApi
) {

    fun loadData(accountId: String) =
        fortniteRequestsIOApi.getFishStats(LocaleUtils.locale, accountId)
            .subscribeOn(Schedulers.io())
}