package robin.vitalij.fortniteassitant.repository

import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import robin.vitalij.fortniteassitant.api.FortniteRequestsComApi
import robin.vitalij.fortniteassitant.db.dao.CosmeticsNewDao
import robin.vitalij.fortniteassitant.db.entity.CosmeticsNewEntity
import robin.vitalij.fortniteassitant.repository.storage.PreferenceManager
import robin.vitalij.fortniteassitant.utils.LocaleUtils
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

private const val THREE_DAY = 3L
private const val DEFAULT_DATE_UPDATE = 0L

@Singleton
class CosmeticsNewRepositoryRepository @Inject constructor(
    private val cosmeticsNewDao: CosmeticsNewDao,
    private val fortniteRequestsComApi: FortniteRequestsComApi,
    private val preferenceManager: PreferenceManager
) {

    fun loadData(): Single<List<CosmeticsNewEntity>> {
        return if (preferenceManager.getCosmeticsNewDataLastUpdate() == DEFAULT_DATE_UPDATE
            || preferenceManager.getCosmeticsNewDataLastUpdate() < (Date().time - TimeUnit.DAYS.toMillis(
                THREE_DAY
            ))
        ) {
            fortniteRequestsComApi.getCosmeticsNew(LocaleUtils.locale)
                .subscribeOn(Schedulers.io())
                .flatMap {
                    cosmeticsNewDao.removeCosmeticsNew()
                    cosmeticsNewDao.insertCosmeticsNew(it.data.items)
                    preferenceManager.setCosmeticsNewDataLastUpdate(Date().time)
                    return@flatMap Single.just(it.data.items)
                }
        } else {
            cosmeticsNewDao.getCosmeticsNew()
                .subscribeOn(Schedulers.io())
        }
    }
}