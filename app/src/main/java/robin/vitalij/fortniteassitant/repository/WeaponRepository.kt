package robin.vitalij.fortniteassitant.repository

import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import robin.vitalij.fortniteassitant.api.FortniteRequestsIOApi
import robin.vitalij.fortniteassitant.db.dao.WeaponDao
import robin.vitalij.fortniteassitant.db.entity.WeaponEntity
import robin.vitalij.fortniteassitant.repository.storage.PreferenceManager
import robin.vitalij.fortniteassitant.utils.LocaleUtils
import robin.vitalij.fortniteassitant.utils.mapper.WeaponMapper
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

private const val ONE_MOUNT_DAY = 31L
private const val DEFAULT_DATE_UPDATE = 0L

@Singleton
class WeaponRepository @Inject constructor(
    private val weaponDao: WeaponDao,
    private val fortniteRequestsIOApi: FortniteRequestsIOApi,
    private val preferenceManager: PreferenceManager
) {

    fun loadData(): Single<List<WeaponEntity>> {
        return if (preferenceManager.getWeaponDataLastUpdate() == DEFAULT_DATE_UPDATE
            || preferenceManager.getDateLastUpdate() < (Date().time - TimeUnit.DAYS.toMillis(
                ONE_MOUNT_DAY
            ))
        ) {
            fortniteRequestsIOApi.getWeapons(LocaleUtils.locale)
                .subscribeOn(Schedulers.io())
                .flatMap {
                    val list = WeaponMapper().transform(it)
                    weaponDao.insertWeapons(list)
                    preferenceManager.setWeaponDataLastUpdate(Date().time)
                    return@flatMap Single.just(list)
                }
        } else {
            weaponDao.getWeapons()
                .subscribeOn(Schedulers.io())
        }
    }
}