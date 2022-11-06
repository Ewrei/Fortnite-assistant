package robin.vitalij.fortniteassitant.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import robin.vitalij.fortniteassitant.api.FortniteRequestsIOApi
import robin.vitalij.fortniteassitant.common.extensions.getErrorModel
import robin.vitalij.fortniteassitant.db.dao.FishDao
import robin.vitalij.fortniteassitant.db.entity.FishEntity
import robin.vitalij.fortniteassitant.model.ErrorModelListItem
import robin.vitalij.fortniteassitant.model.LoadingState
import robin.vitalij.fortniteassitant.repository.storage.PreferenceManager
import robin.vitalij.fortniteassitant.utils.LocaleUtils
import robin.vitalij.fortniteassitant.utils.mapper.FishMapper
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FishRepository @Inject constructor(
    private val fishDao: FishDao,
    private val fortniteRequestsIOApi: FortniteRequestsIOApi,
    private val preferenceManager: PreferenceManager
) {

    fun getFish(): Flow<LoadingState<List<FishEntity>>> {
        return if (isNeedUpdateFromServer()) {
            getServerFish()
        } else {
            getLocalFish()
        }
    }

    private fun isNeedUpdateFromServer(): Boolean =
        preferenceManager.getFishDataLastUpdate() == DEFAULT_DATE_UPDATE || preferenceManager.getFishDataLastUpdate() < (Date().time - TimeUnit.DAYS.toMillis(
            ONE_MOUNT_DAY
        ))

    private fun getServerFish(): Flow<LoadingState<List<FishEntity>>> = flow {
        emit(LoadingState.Loading)
        kotlin.runCatching { fortniteRequestsIOApi.getFish(LocaleUtils.locale) }
            .onSuccess {
                val list = FishMapper().transform(it)
                fishDao.insertFish(list)
                preferenceManager.setFishDataLastUpdate(Date().time)
                emit(LoadingState.Success(list))
            }
            .onFailure { emit(LoadingState.Error(ErrorModelListItem.ErrorItem(it.getErrorModel()))) }
    }.flowOn(Dispatchers.IO)

    private fun getLocalFish(): Flow<LoadingState<List<FishEntity>>> = flow {
        emit(LoadingState.Loading)
        kotlin.runCatching { fishDao.getFish() }
            .onSuccess { emit(LoadingState.Success(it)) }
            .onFailure { emit(LoadingState.Error(ErrorModelListItem.ErrorItem(it.getErrorModel()))) }
    }.flowOn(Dispatchers.IO)

    fun getFish(fishId: String): Flow<LoadingState<FishEntity>> = flow {
        emit(LoadingState.Loading)
        kotlin.runCatching { fishDao.getFish(fishId) }
            .onSuccess { emit(LoadingState.Success(it)) }
            .onFailure { emit(LoadingState.Error(ErrorModelListItem.ErrorItem(it.getErrorModel()))) }
    }.flowOn(Dispatchers.IO)

    companion object {
        private const val ONE_MOUNT_DAY = 31L
        private const val DEFAULT_DATE_UPDATE = 0L
    }

}