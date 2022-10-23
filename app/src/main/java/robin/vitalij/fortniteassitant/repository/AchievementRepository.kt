package robin.vitalij.fortniteassitant.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import robin.vitalij.fortniteassitant.api.FortniteRequestsIOApi
import robin.vitalij.fortniteassitant.common.extensions.getErrorModel
import robin.vitalij.fortniteassitant.db.dao.AchievementDao
import robin.vitalij.fortniteassitant.db.entity.AchievementEntity
import robin.vitalij.fortniteassitant.model.ErrorModelListItem
import robin.vitalij.fortniteassitant.model.LoadingState
import robin.vitalij.fortniteassitant.repository.storage.PreferenceManager
import robin.vitalij.fortniteassitant.utils.LocaleUtils
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AchievementRepository @Inject constructor(
    private val achievementDao: AchievementDao,
    private val fortniteRequestsIOApi: FortniteRequestsIOApi,
    private val preferenceManager: PreferenceManager
) {

    fun getAchievements(): Flow<LoadingState<List<AchievementEntity>>> {
        return if (preferenceManager.getAchievementDataLastUpdate() == DEFAULT_DATE_UPDATE
            || preferenceManager.getAchievementDataLastUpdate() < (Date().time - TimeUnit.DAYS.toMillis(
                ONE_MOUNT_DAY
            ))
        ) {
            getServerAchievements()
        } else {
            getLocalAchievements()
        }
    }

    private fun getServerAchievements(): Flow<LoadingState<List<AchievementEntity>>> = flow {
        emit(LoadingState.Loading)
        kotlin.runCatching { fortniteRequestsIOApi.getAchievements(LocaleUtils.locale) }
            .onSuccess {
                achievementDao.removeAchievements()
                achievementDao.insertAchievements(it.achievements)
                preferenceManager.setAchievementDataLastUpdate(Date().time)
                emit(LoadingState.Success(it.achievements))
            }
            .onFailure { emit(LoadingState.Error(ErrorModelListItem.ErrorItem(it.getErrorModel()))) }
    }.flowOn(Dispatchers.IO)

    private fun getLocalAchievements(): Flow<LoadingState<List<AchievementEntity>>> = flow {
        emit(LoadingState.Loading)
        kotlin.runCatching { achievementDao.getAchievements() }
            .onSuccess { emit(LoadingState.Success(it)) }
            .onFailure { emit(LoadingState.Error(ErrorModelListItem.ErrorItem(it.getErrorModel()))) }
    }.flowOn(Dispatchers.IO)

    companion object {
        private const val ONE_MOUNT_DAY = 31L
        private const val DEFAULT_DATE_UPDATE = 0L
    }

}