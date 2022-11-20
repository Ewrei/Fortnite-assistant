package robin.vitalij.fortniteassitant.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import robin.vitalij.fortniteassitant.api.FortniteRequestsComApi
import robin.vitalij.fortniteassitant.common.extensions.getErrorModel
import robin.vitalij.fortniteassitant.db.dao.CosmeticsNewDao
import robin.vitalij.fortniteassitant.db.entity.CosmeticsNewEntity
import robin.vitalij.fortniteassitant.model.ErrorModelListItem
import robin.vitalij.fortniteassitant.model.LoadingState
import robin.vitalij.fortniteassitant.repository.storage.PreferenceManager
import robin.vitalij.fortniteassitant.utils.LocaleUtils
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CosmeticsNewRepository @Inject constructor(
    private val cosmeticsNewDao: CosmeticsNewDao,
    private val fortniteRequestsComApi: FortniteRequestsComApi,
    private val preferenceManager: PreferenceManager
) {

    fun getCosmeticsNew():  Flow<LoadingState<List<CosmeticsNewEntity>>> {
        return if (isNeedUpdateFromServer()) {
            getServerCosmeticsNew()
        } else {
            getLocalCosmeticsNew()
        }
    }

    private fun isNeedUpdateFromServer(): Boolean =
        preferenceManager.getCosmeticsNewDataLastUpdate() == DEFAULT_DATE_UPDATE
                || preferenceManager.getCosmeticsNewDataLastUpdate() < (Date().time - TimeUnit.DAYS.toMillis(
            THREE_DAY
        ))

    private fun getServerCosmeticsNew(): Flow<LoadingState<List<CosmeticsNewEntity>>> = flow {
        emit(LoadingState.Loading)
        kotlin.runCatching {   fortniteRequestsComApi.getCosmeticsNew(LocaleUtils.locale) }
            .onSuccess {
                cosmeticsNewDao.removeCosmeticsNew()
                cosmeticsNewDao.insertCosmeticsNew(it.data.items)
                preferenceManager.setCosmeticsNewDataLastUpdate(Date().time)
                emit(LoadingState.Success(it.data.items))
            }
            .onFailure { emit(LoadingState.Error(ErrorModelListItem.ErrorItem(it.getErrorModel()))) }
    }.flowOn(Dispatchers.IO)

    private fun getLocalCosmeticsNew(): Flow<LoadingState<List<CosmeticsNewEntity>>> = flow {
        emit(LoadingState.Loading)
        kotlin.runCatching {   cosmeticsNewDao.getCosmeticsNew() }
            .onSuccess { emit(LoadingState.Success(it)) }
            .onFailure { emit(LoadingState.Error(ErrorModelListItem.ErrorItem(it.getErrorModel()))) }
    }.flowOn(Dispatchers.IO)

    companion object {
        private const val THREE_DAY = 3L
        private const val DEFAULT_DATE_UPDATE = 0L

    }

}