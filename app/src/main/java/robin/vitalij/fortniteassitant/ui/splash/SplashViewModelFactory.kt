package robin.vitalij.fortniteassitant.ui.splash

import robin.vitalij.fortniteassitant.repository.InterstitialAdRepository
import robin.vitalij.fortniteassitant.repository.NativeRepository
import robin.vitalij.fortniteassitant.repository.network.GetUserRepository
import robin.vitalij.fortniteassitant.repository.network.SaveUserRepository
import robin.vitalij.fortniteassitant.repository.storage.PreferenceManager
import robin.vitalij.fortniteassitant.ui.common.BaseViewModelFactory
import javax.inject.Inject

class SplashViewModelFactory @Inject constructor(
    private val preferenceManager: PreferenceManager,
    private val getUserRepository: GetUserRepository,
    private val saveUserRepository: SaveUserRepository,
    private val nativeRepository: NativeRepository,
    private val interstitialAdRepository: InterstitialAdRepository
) : BaseViewModelFactory<SplashViewModel>(SplashViewModel::class.java) {

    private var viewModel: SplashViewModel? = null

    override fun createViewModel(): SplashViewModel {
        return viewModel ?: run {
            val model = SplashViewModel(
                preferenceManager,
                getUserRepository,
                saveUserRepository,
                nativeRepository,
                interstitialAdRepository
            )
            viewModel = model
            return model
        }
    }
}