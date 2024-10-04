package robin.vitalij.fortniteassitant.ui.setting

import robin.vitalij.fortniteassitant.repository.db.UserRepository
import robin.vitalij.fortniteassitant.repository.network.GetUserRepository
import robin.vitalij.fortniteassitant.repository.network.SaveUserRepository
import robin.vitalij.fortniteassitant.repository.storage.PreferenceManager
import robin.vitalij.fortniteassitant.ui.common.BaseViewModelFactory
import javax.inject.Inject

class SettingViewModelFactory @Inject constructor(
    private val userRepository: UserRepository,
    private val saveUserRepository: SaveUserRepository,
    private val getUserRepository: GetUserRepository,
    private val preferenceManager: PreferenceManager
) : BaseViewModelFactory<SettingViewModel>(SettingViewModel::class.java) {

    private var viewModel: SettingViewModel? = null

    override fun createViewModel(): SettingViewModel {
        return viewModel ?: run {
            val model = SettingViewModel(
                userRepository,
                saveUserRepository,
                getUserRepository,
                preferenceManager
            )
            viewModel = model
            return model
        }
    }
}