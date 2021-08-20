package robin.vitalij.fortniteassitant.ui.splash

import robin.vitalij.fortniteassitant.repository.FirebaseDynamicLinkRepository
import robin.vitalij.fortniteassitant.repository.InterstitialAdRepository
import robin.vitalij.fortniteassitant.repository.network.GetUserRepository
import robin.vitalij.fortniteassitant.repository.network.SaveUserRepository
import robin.vitalij.fortniteassitant.repository.storage.PreferenceManager
import robin.vitalij.fortniteassitant.ui.common.BaseViewModelFactory
import javax.inject.Inject

class SplashViewModelFactory @Inject constructor(
    private val preferenceManager: PreferenceManager,
    private val getUserRepository: GetUserRepository,
    private val saveUserRepository: SaveUserRepository,
    private val interstitialAdRepository: InterstitialAdRepository,
    private val firebaseDynamicLinkRepository: FirebaseDynamicLinkRepository
) : BaseViewModelFactory<SplashViewModel>(SplashViewModel::class.java) {

    private var viewModel: SplashViewModel? = null

    override fun createViewModel(): SplashViewModel {
        return viewModel ?: run {
            val model = SplashViewModel(
                preferenceManager,
                getUserRepository,
                saveUserRepository,
                interstitialAdRepository,
                firebaseDynamicLinkRepository
            )
            viewModel = model
            return model
        }
    }
}