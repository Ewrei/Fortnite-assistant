package robin.vitalij.fortniteassitant.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import robin.vitalij.fortniteassitant.api.FortniteRequestsIOApi
import robin.vitalij.fortniteassitant.common.extensions.getErrorModel
import robin.vitalij.fortniteassitant.db.dao.WeaponDao
import robin.vitalij.fortniteassitant.db.entity.WeaponEntity
import robin.vitalij.fortniteassitant.model.ErrorModelListItem
import robin.vitalij.fortniteassitant.model.LoadingState
import robin.vitalij.fortniteassitant.repository.storage.PreferenceManager
import robin.vitalij.fortniteassitant.utils.LocaleUtils
import robin.vitalij.fortniteassitant.utils.mapper.WeaponMapper
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeaponRepository @Inject constructor(
    private val weaponDao: WeaponDao,
    private val fortniteRequestsIOApi: FortniteRequestsIOApi,
    private val preferenceManager: PreferenceManager
) {

    fun getWeapons(): Flow<LoadingState<List<WeaponEntity>>> {
        return if (preferenceManager.getWeaponDataLastUpdate() == DEFAULT_DATE_UPDATE
            || preferenceManager.getDateLastUpdate() < (Date().time - TimeUnit.DAYS.toMillis(
                ONE_MOUNT_DAY
            ))
        ) {
            getServerWeapons()
        } else {
            getLocalWeapons()
        }
    }

    private fun getServerWeapons(): Flow<LoadingState<List<WeaponEntity>>> = flow {
        emit(LoadingState.Loading)
        kotlin.runCatching { fortniteRequestsIOApi.getWeapons(LocaleUtils.locale) }
            .onSuccess {
                val weapons = WeaponMapper().transform(it)
                weaponDao.insertWeapons(weapons)
                preferenceManager.setWeaponDataLastUpdate(Date().time)
                emit(LoadingState.Success(weapons))
            }
            .onFailure { emit(LoadingState.Error(ErrorModelListItem.ErrorItem(it.getErrorModel()))) }
    }.flowOn(Dispatchers.IO)

    private fun getLocalWeapons(): Flow<LoadingState<List<WeaponEntity>>> = flow {
        emit(LoadingState.Loading)
        kotlin.runCatching { weaponDao.getWeapons() }
            .onSuccess { emit(LoadingState.Success(it)) }
            .onFailure { emit(LoadingState.Error(ErrorModelListItem.ErrorItem(it.getErrorModel()))) }
    }.flowOn(Dispatchers.IO)

    companion object {
        private const val ONE_MOUNT_DAY = 31L
        private const val DEFAULT_DATE_UPDATE = 0L

    }

}