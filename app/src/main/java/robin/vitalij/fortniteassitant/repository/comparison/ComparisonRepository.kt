package robin.vitalij.fortniteassitant.repository.comparison

import android.annotation.SuppressLint
import io.reactivex.Maybe
import io.reactivex.Observable
import robin.vitalij.fortniteassitant.db.dao.UserDao
import robin.vitalij.fortniteassitant.model.comparison.ComparisonProfileResponse
import robin.vitalij.fortniteassitant.model.comparison.PlayerModel
import robin.vitalij.fortniteassitant.model.enums.ComparisonDataType
import robin.vitalij.fortniteassitant.repository.storage.PreferenceManager
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ComparisonRepository @Inject constructor(
    private val userDao: UserDao,
    private val comparisonListUserRepository: ComparisonListUserRepository,
    private val comparisonProfileRepository: ComparisonProfileRepository,
    private val preferenceManager: PreferenceManager
) {
    @SuppressLint("CheckResult")
    fun loadData(
        comparisonDataType: ComparisonDataType
    ): Observable<ComparisonProfileResponse> {
        return if (comparisonDataType == ComparisonDataType.TWO_PLAYER_USER) {
            comparisonListUserRepository.getData().flatMap {
                val filterList = it.filter { it.isSelected }
                comparisonProfileRepository.updateProfile(
                    ComparisonProfileResponse(
                        playerModel = filterList.first(),
                        playerTwoModel = filterList.last()
                    )
                )
                return@flatMap comparisonProfileRepository.getData()
            }
        } else {
            return comparisonProfileRepository.getData()
        }
    }

    fun loadOneLocalData(playerModel: PlayerModel) =
        userDao.getUserFull(preferenceManager.getPlayerId()).flatMap {
            val user = PlayerModel(it.userEntity)
            comparisonProfileRepository.updateProfile(
                ComparisonProfileResponse(
                    playerModel = user,
                    playerTwoModel = playerModel
                )
            )
            return@flatMap Maybe.just(true)

        }
}