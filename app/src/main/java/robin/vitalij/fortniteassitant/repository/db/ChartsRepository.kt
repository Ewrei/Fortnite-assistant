package robin.vitalij.fortniteassitant.repository.db

import io.reactivex.Flowable
import robin.vitalij.fortniteassitant.db.dao.UserDao
import robin.vitalij.fortniteassitant.model.enums.BattlesType
import robin.vitalij.fortniteassitant.model.enums.ChartsType
import robin.vitalij.fortniteassitant.model.enums.GameType
import robin.vitalij.fortniteassitant.utils.mapper.ChartsMapper
import javax.inject.Inject

class ChartsRepository @Inject constructor(
    private val userDao: UserDao
) {

    fun loadData(playerId: String, chartsType: ChartsType, battlesType: BattlesType, gameType: GameType) =
        userDao.getUserHistory(playerId).flatMap {
            return@flatMap Flowable.just(ChartsMapper(chartsType, battlesType, gameType).transform(it))
        }
}