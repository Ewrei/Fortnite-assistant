package robin.vitalij.fortniteassitant.repository

import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import robin.vitalij.fortniteassitant.api.FortniteRequestsComApi
import robin.vitalij.fortniteassitant.common.extensions.convertToBannerEntities
import robin.vitalij.fortniteassitant.db.dao.BannerDao
import robin.vitalij.fortniteassitant.db.entity.BannerEntity
import robin.vitalij.fortniteassitant.repository.storage.PreferenceManager
import robin.vitalij.fortniteassitant.utils.LocaleUtils
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

private const val THIRTY_DAY = 30L
private const val DEFAULT_DATE_UPDATE = 0L

@Singleton
class BannerRepository @Inject constructor(
    private val bannerDao: BannerDao,
    private val fortniteRequestsComApi: FortniteRequestsComApi,
    private val preferenceManager: PreferenceManager
) {

    fun loadData(): Single<List<BannerEntity>> {
        return if (preferenceManager.getBannerDataLastUpdate() == DEFAULT_DATE_UPDATE
            || preferenceManager.getBannerDataLastUpdate() < (Date().time - TimeUnit.DAYS.toMillis(
                THIRTY_DAY
            ))
        ) {
            fortniteRequestsComApi.getBanners(LocaleUtils.locale)
                .subscribeOn(Schedulers.io())
                .flatMap {
                    val banners = it.data.convertToBannerEntities()

                    bannerDao.removeBanners()
                    bannerDao.insertBanners(banners)
                    preferenceManager.setBannerDataLastUpdate(Date().time)
                    return@flatMap Single.just(banners)
                }
        } else {
            bannerDao.getBanners()
                .subscribeOn(Schedulers.io())
        }
    }

    fun getBannerId(bannerId: String) = bannerDao.getBanner(bannerId)

}