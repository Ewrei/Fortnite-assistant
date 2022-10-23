package robin.vitalij.fortniteassitant.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import robin.vitalij.fortniteassitant.api.FortniteRequestsComApi
import robin.vitalij.fortniteassitant.common.extensions.getErrorModel
import robin.vitalij.fortniteassitant.db.dao.CosmeticsDao
import robin.vitalij.fortniteassitant.db.entity.CosmeticsEntity
import robin.vitalij.fortniteassitant.model.ErrorModelListItem
import robin.vitalij.fortniteassitant.model.LoadingState
import robin.vitalij.fortniteassitant.model.enums.ShopType
import robin.vitalij.fortniteassitant.repository.storage.PreferenceManager
import robin.vitalij.fortniteassitant.utils.LocaleUtils
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CatalogCosmeticsRepository @Inject constructor(
    private val cosmeticsDao: CosmeticsDao,
    private val fortniteRequestsComApi: FortniteRequestsComApi,
    private val preferenceManager: PreferenceManager
) {

    fun getShopTypes(): Flow<LoadingState<List<ShopType>>> {
        return if (isNeedUpdateFromServer()) {
            getServerCosmetics()
        } else {
            getLocalCosmetics()
        }
    }

    fun getCosmetics(shopType: ShopType): Flow<LoadingState<List<CosmeticsEntity>>> = flow {
        emit(LoadingState.Loading)
        kotlin.runCatching { cosmeticsDao.getCosmetics(shopType.id) }
            .onSuccess { emit(LoadingState.Success(it)) }
            .onFailure { emit(LoadingState.Error(ErrorModelListItem.ErrorItem(it.getErrorModel()))) }
    }.flowOn(Dispatchers.IO)

    private fun isNeedUpdateFromServer(): Boolean =
        preferenceManager.getCosmeticsDataLastUpdate() == DEFAULT_DATE_UPDATE
                || preferenceManager.getCosmeticsDataLastUpdate() < (Date().time - TimeUnit.DAYS.toMillis(
            THREE_DAY
        ))

    private fun getServerCosmetics(): Flow<LoadingState<List<ShopType>>> = flow {
        emit(LoadingState.Loading)
        kotlin.runCatching { fortniteRequestsComApi.getCosmetics(LocaleUtils.locale) }
            .onSuccess {
                cosmeticsDao.removeCosmetics()
                cosmeticsDao.insertCosmetics(it.data)
                preferenceManager.setCosmeticsDataLastUpdate(Date().time)
                emit(LoadingState.Success(ShopType.values().toList()))
            }
            .onFailure { emit(LoadingState.Error(ErrorModelListItem.ErrorItem(it.getErrorModel()))) }
    }.flowOn(Dispatchers.IO)

    private fun getLocalCosmetics(): Flow<LoadingState<List<ShopType>>> = flow {
        emit(LoadingState.Loading)
        kotlin.runCatching { cosmeticsDao.getCosmetics() }
            .onSuccess { emit(LoadingState.Success(ShopType.values().toList())) }
            .onFailure { emit(LoadingState.Error(ErrorModelListItem.ErrorItem(it.getErrorModel()))) }
    }.flowOn(Dispatchers.IO)

    companion object {
        private const val THREE_DAY = 3L
        private const val DEFAULT_DATE_UPDATE = 0L

    }

}