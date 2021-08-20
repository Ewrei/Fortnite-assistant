package robin.vitalij.fortniteassitant.ui.setting.applicationinfo

import robin.vitalij.fortniteassitant.ui.common.BaseViewModelFactory
import javax.inject.Inject

class ApplicationInfoViewModelFactory @Inject constructor() :
    BaseViewModelFactory<ApplicationInfoViewModel>(ApplicationInfoViewModel::class.java) {

    private var viewModel: ApplicationInfoViewModel? = null

    override fun createViewModel(): ApplicationInfoViewModel {
        return viewModel ?: run {
            val model = ApplicationInfoViewModel()
            viewModel = model
            return model
        }
    }
}