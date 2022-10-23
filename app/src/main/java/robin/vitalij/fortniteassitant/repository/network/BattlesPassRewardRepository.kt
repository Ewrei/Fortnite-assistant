package robin.vitalij.fortniteassitant.repository.network

import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import robin.vitalij.fortniteassitant.api.FortniteRequestsIOApi
import robin.vitalij.fortniteassitant.utils.LocaleUtils
import robin.vitalij.fortniteassitant.utils.mapper.BattlesPassRewardsMapper
import robin.vitalij.fortniteassitant.utils.ResourceProvider
import javax.inject.Inject

class BattlesPassRewardRepository @Inject constructor(
    private val fortniteRequestsIOApi: FortniteRequestsIOApi,
    private val resourceProvider: ResourceProvider
) {

    fun getBattlesPassReward(season: String) =
        fortniteRequestsIOApi.getBattlesPassRewards(LocaleUtils.locale, season)
            .subscribeOn(Schedulers.io())
            .flatMap {
                return@flatMap Single.just(BattlesPassRewardsMapper(resourceProvider).transform(it))
            }

}