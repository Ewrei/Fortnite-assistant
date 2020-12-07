package robin.vitalij.fortniteassitant.ui.main

import robin.vitalij.fortniteassitant.repository.InterstitialAdRepository
import robin.vitalij.fortniteassitant.repository.db.UserRepository
import robin.vitalij.fortniteassitant.repository.network.SaveUserRepository
import robin.vitalij.fortniteassitant.repository.storage.PreferenceManager
import robin.vitalij.fortniteassitant.ui.common.BaseViewModelFactory
import robin.vitalij.fortniteassitant.utils.ResourceProvider
import javax.inject.Inject

class MainViewModelFactory @Inject constructor(
    private val preferenceManager: PreferenceManager,
    private val userRepository: UserRepository,
    private val interstitialAdRepository: InterstitialAdRepository,
    private val resourceProvider: ResourceProvider,
    private val saveUserRepository: SaveUserRepository
) : BaseViewModelFactory<MainViewModel>(MainViewModel::class.java) {

    private var viewModel: MainViewModel? = null

    override fun createViewModel(): MainViewModel {
        return viewModel ?: run {
            val model = MainViewModel(
                preferenceManager,
                userRepository,
                interstitialAdRepository,
                saveUserRepository,
                resourceProvider
            )
            viewModel = model
            return model
        }
    }
}