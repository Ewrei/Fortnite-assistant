package robin.vitalij.fortniteassitant.repository.db

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import robin.vitalij.fortniteassitant.common.extensions.getErrorModel
import robin.vitalij.fortniteassitant.db.dao.UserDao
import robin.vitalij.fortniteassitant.model.ChartsModel
import robin.vitalij.fortniteassitant.model.ErrorModelListItem
import robin.vitalij.fortniteassitant.model.LoadingState
import robin.vitalij.fortniteassitant.model.enums.BattlesType
import robin.vitalij.fortniteassitant.model.enums.ChartsType
import robin.vitalij.fortniteassitant.model.enums.GameType
import robin.vitalij.fortniteassitant.utils.mapper.ChartsMapper
import javax.inject.Inject

class ChartsRepository @Inject constructor(private val userDao: UserDao) {

    fun loadData(
        playerId: String,
        chartsType: ChartsType,
        battlesType: BattlesType,
        gameType: GameType
    ): Flow<LoadingState<ChartsModel>> = flow {
        emit(LoadingState.Loading)
        kotlin.runCatching { userDao.getUserHistory(playerId) }
            .onSuccess {
                emit(
                    LoadingState.Success(
                        ChartsMapper(
                            chartsType,
                            battlesType,
                            gameType
                        ).transform(it)
                    )
                )
            }
            .onFailure { emit(LoadingState.Error(ErrorModelListItem.ErrorItem(it.getErrorModel()))) }
    }.flowOn(Dispatchers.IO)

}