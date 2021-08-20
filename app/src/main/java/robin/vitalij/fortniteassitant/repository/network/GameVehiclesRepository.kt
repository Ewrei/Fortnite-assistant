package robin.vitalij.fortniteassitant.repository.network

import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import robin.vitalij.fortniteassitant.api.FortniteRequestsIOApi
import robin.vitalij.fortniteassitant.utils.LocaleUtils
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GameVehiclesRepository @Inject constructor(private val fortniteRequestsIOApi: FortniteRequestsIOApi) {

    fun getGameCrew() = fortniteRequestsIOApi.getGameVehicles(LocaleUtils.locale)
        .subscribeOn(Schedulers.io()).flatMap {
            return@flatMap Single.just(it.vehicles)
        }

}