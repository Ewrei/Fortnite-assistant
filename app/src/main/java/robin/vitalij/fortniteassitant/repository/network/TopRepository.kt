package robin.vitalij.fortniteassitant.repository.network

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import robin.vitalij.fortniteassitant.api.SteamChartRequestsApi
import robin.vitalij.fortniteassitant.db.entity.UserEntity
import robin.vitalij.fortniteassitant.model.enums.TopType
import robin.vitalij.fortniteassitant.utils.mapper.TopUserMapper
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TopRepository @Inject constructor(
    private val steamChartRequestsApi: SteamChartRequestsApi
) {
    val disposables = CompositeDisposable()

    fun updateTopUser(userEntity: UserEntity) {
        val hashMap: HashMap<String, Any> = hashMapOf()
//        hashMap["playerId"] = userEntity.playerId
//        hashMap["kd"] = userEntity.kd
//        hashMap["kr"] = userEntity.getKR()
//        hashMap["timePlayed"] = userEntity.timePlayed
//        hashMap["score"] = userEntity.score.toInt()
//        hashMap["kills"] = userEntity.kills.toInt()
//        hashMap["deaths"] = userEntity.deaths.toInt()
//        hashMap["headshots"] = userEntity.headshots.toInt()
//        hashMap["headshotsPercent"] = userEntity.headshotPct
//        hashMap["bombsPlaned"] = userEntity.bombsPlanted.toInt()
//        hashMap["bombsDefused"] = userEntity.bombsDefused.toInt()
//        hashMap["mvp"] = userEntity.mvp.toInt()
//        hashMap["wins"] = userEntity.wins.toInt()
//        hashMap["wlPrecentage"] = userEntity.wlPercentage
//        hashMap["matchesPlayed"] = userEntity.matchesPlayed.toInt()
//        hashMap["roundsPlayers"] = userEntity.roundsPlayed.toInt()
//        hashMap["roundsWon"] = userEntity.roundsWon.toInt()
//        hashMap["roundsPercentage"] = userEntity.getAverageRoundsWins()
//        hashMap["timeUpdate"] = Date().time
//        hashMap["userName"] = userEntity.userName
//        hashMap["avatarUrl"] = userEntity.avatarUrl

        steamChartRequestsApi.saveTop(hashMap)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({
                //do nothing
            }, {
                //do nothing
            })
            .let(disposables::add)
    }

    fun getTopUsers(topType: TopType) = steamChartRequestsApi.getTopUsers(topType.id).flatMap {
        return@flatMap Observable.just(TopUserMapper(topType).transform(it))
    }
}