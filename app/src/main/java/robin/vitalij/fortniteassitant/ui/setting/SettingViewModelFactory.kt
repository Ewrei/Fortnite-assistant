package robin.vitalij.fortniteassitant.ui.setting

import robin.vitalij.fortniteassitant.repository.BillingRepository
import robin.vitalij.fortniteassitant.repository.db.UserRepository
import robin.vitalij.fortniteassitant.repository.storage.PreferenceManager
import robin.vitalij.fortniteassitant.ui.common.BaseViewModelFactory
import javax.inject.Inject

class SettingViewModelFactory @Inject constructor(
    private val billingRepository: BillingRepository,
    private val userRepository: UserRepository,
    private val preferenceManager: PreferenceManager
) : BaseViewModelFactory<SettingViewModel>(SettingViewModel::class.java) {

    private var viewModel: SettingViewModel? = null

    override fun createViewModel(): SettingViewModel {
        return viewModel ?: run {
            val model = SettingViewModel(billingRepository, userRepository, preferenceManager)
            viewModel = model
            return model
        }
    }
}