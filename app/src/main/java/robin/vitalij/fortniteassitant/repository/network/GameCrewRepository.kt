package robin.vitalij.fortniteassitant.repository.network

import io.reactivex.schedulers.Schedulers
import robin.vitalij.fortniteassitant.api.FortniteRequestsIOApi
import robin.vitalij.fortniteassitant.utils.LocaleUtils
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GameCrewRepository @Inject constructor(private val fortniteRequestsIOApi: FortniteRequestsIOApi) {

    fun getGameCrew() = fortniteRequestsIOApi.getGameCrew(LocaleUtils.locale)
        .subscribeOn(Schedulers.io())

}