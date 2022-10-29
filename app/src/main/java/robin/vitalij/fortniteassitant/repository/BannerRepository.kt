package robin.vitalij.fortniteassitant.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import robin.vitalij.fortniteassitant.api.FortniteRequestsComApi
import robin.vitalij.fortniteassitant.common.extensions.convertToBannerEntities
import robin.vitalij.fortniteassitant.common.extensions.getErrorModel
import robin.vitalij.fortniteassitant.db.dao.BannerDao
import robin.vitalij.fortniteassitant.db.entity.BannerEntity
import robin.vitalij.fortniteassitant.model.ErrorModelListItem
import robin.vitalij.fortniteassitant.model.LoadingState
import robin.vitalij.fortniteassitant.repository.storage.PreferenceManager
import robin.vitalij.fortniteassitant.utils.LocaleUtils
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BannerRepository @Inject constructor(
    private val bannerDao: BannerDao,
    private val fortniteRequestsComApi: FortniteRequestsComApi,
    private val preferenceManager: PreferenceManager
) {

    fun getBanners(): Flow<LoadingState<List<BannerEntity>>> {
        return if (isNeedUpdateFromServer()) {
            getServerBanners()
        } else {
            getLocalBanners()
        }
    }

    private fun isNeedUpdateFromServer(): Boolean =
        preferenceManager.getBannerDataLastUpdate() == DEFAULT_DATE_UPDATE
                || preferenceManager.getBannerDataLastUpdate() < (Date().time - TimeUnit.DAYS.toMillis(
            THIRTY_DAY
        ))

    private fun getServerBanners(): Flow<LoadingState<List<BannerEntity>>> = flow {
        emit(LoadingState.Loading)
        kotlin.runCatching { fortniteRequestsComApi.getBanners(LocaleUtils.locale) }
            .onSuccess {
                val banners = it.data.convertToBannerEntities()

                bannerDao.removeBanners()
                bannerDao.insertBanners(banners)
                preferenceManager.setBannerDataLastUpdate(Date().time)
                emit(LoadingState.Success(banners))
            }
            .onFailure { emit(LoadingState.Error(ErrorModelListItem.ErrorItem(it.getErrorModel()))) }
    }.flowOn(Dispatchers.IO)

    private fun getLocalBanners(): Flow<LoadingState<List<BannerEntity>>> = flow {
        emit(LoadingState.Loading)
        kotlin.runCatching { bannerDao.getBanners() }
            .onSuccess { emit(LoadingState.Success(it)) }
            .onFailure { emit(LoadingState.Error(ErrorModelListItem.ErrorItem(it.getErrorModel()))) }
    }.flowOn(Dispatchers.IO)

    fun getBannerId(bannerId: String) = bannerDao.getBanner(bannerId)

    companion object {
        private const val THIRTY_DAY = 30L
        private const val DEFAULT_DATE_UPDATE = 0L
    }

}