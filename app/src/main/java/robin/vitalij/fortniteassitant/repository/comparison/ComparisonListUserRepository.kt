package robin.vitalij.fortniteassitant.repository.comparison

import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import robin.vitalij.fortniteassitant.model.comparison.PlayerModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ComparisonListUserRepository @Inject constructor() {

    private var playersSubject: BehaviorSubject<ArrayList<PlayerModel>> = BehaviorSubject.create()
    private var players: ArrayList<PlayerModel> = arrayListOf()

    init {
        playersSubject.onNext(arrayListOf())
    }

    fun addUserModel(playerModel: PlayerModel) {
        players.add(playerModel)
        playersSubject.onNext(players)
    }

    fun removeUserModel(playerModel: PlayerModel) {
        //TODO
    }

    fun updateUserModel(userModel: PlayerModel) {
        players.find { it.userEntity.playerId == userModel.userEntity.playerId }
            ?.isSelected = userModel.isSelected
        playersSubject.onNext(players)
    }

    fun getData(): Observable<ArrayList<PlayerModel>> = playersSubject

    fun getPlayerSize(): Observable<Int> =
        playersSubject.flatMap { return@flatMap Observable.just(it.size) }

}