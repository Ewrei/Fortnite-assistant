package robin.vitalij.fortniteassitant.repository

import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import robin.vitalij.fortniteassitant.api.FortniteRequestsComApi
import robin.vitalij.fortniteassitant.db.dao.CosmeticsDao
import robin.vitalij.fortniteassitant.db.entity.CosmeticsEntity
import robin.vitalij.fortniteassitant.model.enums.ShopType
import robin.vitalij.fortniteassitant.repository.storage.PreferenceManager
import robin.vitalij.fortniteassitant.utils.LocaleUtils
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

private const val THREE_DAY = 3L
private const val DEFAULT_DATE_UPDATE = 0L

@Singleton
class CatalogCosmeticsRepository @Inject constructor(
    private val cosmeticsDao: CosmeticsDao,
    private val fortniteRequestsComApi: FortniteRequestsComApi,
    private val preferenceManager: PreferenceManager
) {

    fun loadData(): Single<List<ShopType>> {
        return if (preferenceManager.getCosmeticsDataLastUpdate() == DEFAULT_DATE_UPDATE
            || preferenceManager.getCosmeticsDataLastUpdate() < (Date().time - TimeUnit.DAYS.toMillis(
                THREE_DAY
            ))
        ) {
            fortniteRequestsComApi.getCosmetics(LocaleUtils.locale)
                .subscribeOn(Schedulers.io())
                .flatMap {
                    cosmeticsDao.removeCosmetics()
                    cosmeticsDao.insertCosmetics(it.data)
                    preferenceManager.setCosmeticsDataLastUpdate(Date().time)
                    return@flatMap Single.just(ShopType.values().toList())
                }
        } else {
            cosmeticsDao.getCosmetics()
                .subscribeOn(Schedulers.io())
                .flatMap {
                    return@flatMap Single.just(ShopType.values().toList())
                }
        }
    }

    fun getCosmetics(shopType: ShopType): Single<List<CosmeticsEntity>> = cosmeticsDao.getCosmetics(shopType.id)

}