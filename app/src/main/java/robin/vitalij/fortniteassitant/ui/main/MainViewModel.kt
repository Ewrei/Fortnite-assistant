package robin.vitalij.fortniteassitant.ui.main

import robin.vitalij.fortniteassitant.interfaces.SaveUserCallback
import robin.vitalij.fortniteassitant.model.enums.FirebaseDynamicLinkType
import robin.vitalij.fortniteassitant.model.network.stats.FortniteProfileResponse
import robin.vitalij.fortniteassitant.repository.FirebaseDynamicLinkRepository
import robin.vitalij.fortniteassitant.repository.InterstitialAdRepository
import robin.vitalij.fortniteassitant.repository.RewardedAdRepository
import robin.vitalij.fortniteassitant.repository.db.UserRepository
import robin.vitalij.fortniteassitant.repository.network.SaveUserRepository
import robin.vitalij.fortniteassitant.repository.storage.PreferenceManager
import robin.vitalij.fortniteassitant.ui.common.BaseViewModel
import robin.vitalij.fortniteassitant.utils.ResourceProvider
import javax.inject.Inject

class MainViewModel @Inject constructor(
    val preferenceManager: PreferenceManager,
    private val userRepository: UserRepository,
    val interstitialAdRepository: InterstitialAdRepository,
    private val saveUserRepository: SaveUserRepository,
    val rewardedAdRepository: RewardedAdRepository,
    private val firebaseDynamicLinkRepository: FirebaseDynamicLinkRepository,
    resourceProvider: ResourceProvider
) : BaseViewModel() {

    lateinit var openMainScreen: () -> Unit

    lateinit var openFirebaseDynamicLink: (
        firebaseDynamicLinkType: FirebaseDynamicLinkType,
        id: String
    ) -> Unit

    fun saveUser(fortniteProfileResponse: FortniteProfileResponse) {
        saveUserRepository.saveUser(fortniteProfileResponse, object :
            SaveUserCallback {
            override fun showOrHideProgressBar(isVisible: Boolean) {
                activityProgressBarVisibility.value = isVisible
            }

            override fun showError(throwable: Throwable) {
                activityProgressBarVisibility.value = false
            }

            override fun showMessage(title: String) {
                //do nothing
            }

            override fun done() {
                preferenceManager.setPlayerId(
                    fortniteProfileResponse.stats.playerStatsData.account.id
                )
                activityProgressBarVisibility.value = false
                openMainScreen()
            }
        })
    }

    fun checkFirebaseDynamicLink() {
        if (firebaseDynamicLinkRepository.firebaseDynamicLinkType != null && firebaseDynamicLinkRepository.id != null) {
            openFirebaseDynamicLink(
                firebaseDynamicLinkRepository.firebaseDynamicLinkType!!,
                firebaseDynamicLinkRepository.id!!,
            )
        }
    }

    fun clearFirebaseDynamicLink() {
        firebaseDynamicLinkRepository.clear()
    }
}