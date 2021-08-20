package robin.vitalij.fortniteassitant.repository

import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import robin.vitalij.fortniteassitant.api.FortniteRequestsIOApi
import robin.vitalij.fortniteassitant.db.dao.AchievementDao
import robin.vitalij.fortniteassitant.db.entity.AchievementEntity
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
class AchievementRepository @Inject constructor(
    private val achievementDao: AchievementDao,
    private val fortniteRequestsIOApi: FortniteRequestsIOApi,
    private val preferenceManager: PreferenceManager
) {

    fun loadData(): Single<List<AchievementEntity>> {
        return if (preferenceManager.getAchievementDataLastUpdate() == DEFAULT_DATE_UPDATE
            || preferenceManager.getAchievementDataLastUpdate() < (Date().time - TimeUnit.DAYS.toMillis(
                ONE_MOUNT_DAY
            ))
        ) {
            fortniteRequestsIOApi.getAchievements(LocaleUtils.locale)
                .subscribeOn(Schedulers.io())
                .flatMap {
                    achievementDao.removeAchievements()
                    achievementDao.insertAchievements(it.achievements)
                    preferenceManager.setAchievementDataLastUpdate(Date().time)
                    return@flatMap Single.just(it.achievements)
                }
        } else {
            achievementDao.getAchievements()
                .subscribeOn(Schedulers.io())
        }
    }
}