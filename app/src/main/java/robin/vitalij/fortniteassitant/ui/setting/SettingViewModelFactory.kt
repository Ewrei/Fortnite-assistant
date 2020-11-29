package robin.vitalij.fortniteassitant.ui.setting

import robin.vitalij.fortniteassitant.repository.BillingRepository
import robin.vitalij.fortniteassitant.repository.db.UserRepository
import robin.vitalij.fortniteassitant.repository.network.GetUserRepository
import robin.vitalij.fortniteassitant.repository.network.SaveUserRepository
import robin.vitalij.fortniteassitant.repository.storage.PreferenceManager
import robin.vitalij.fortniteassitant.ui.common.BaseViewModelFactory
import robin.vitalij.fortniteassitant.utils.view.ResourceProvider
import javax.inject.Inject

class SettingViewModelFactory @Inject constructor(
    private val billingRepository: BillingRepository,
    private val userRepository: UserRepository,
    private val saveUserRepository: SaveUserRepository,
    private val getUserRepository: GetUserRepository,
    private val preferenceManager: PreferenceManager,
    private val resourceProvider: ResourceProvider
) : BaseViewModelFactory<SettingViewModel>(SettingViewModel::class.java) {

    private var viewModel: SettingViewModel? = null

    override fun createViewModel(): SettingViewModel {
        return viewModel ?: run {
            val model = SettingViewModel(
                billingRepository,
                userRepository,
                saveUserRepository,
                getUserRepository,
                preferenceManager, resourceProvider
            )
            viewModel = model
            return model
        }
    }
}