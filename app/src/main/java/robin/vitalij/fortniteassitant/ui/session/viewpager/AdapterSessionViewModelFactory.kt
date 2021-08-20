package robin.vitalij.fortniteassitant.ui.session.viewpager

import robin.vitalij.fortniteassitant.ui.common.BaseViewModelFactory
import javax.inject.Inject

class AdapterSessionViewModelFactory @Inject constructor() : BaseViewModelFactory<AdapterSessionViewModel>(
    AdapterSessionViewModel::class.java
) {

    private var viewModel: AdapterSessionViewModel? = null

    override fun createViewModel(): AdapterSessionViewModel {
        return viewModel ?: run {
            val model = AdapterSessionViewModel()
            viewModel = model
            return model
        }
    }
}