package robin.vitalij.fortniteassitant.ui.chartlist

import robin.vitalij.fortniteassitant.ui.common.BaseViewModelFactory
import javax.inject.Inject

class ChartsTypeViewModelFactory @Inject constructor() :
    BaseViewModelFactory<ChartsTypeViewModel>(ChartsTypeViewModel::class.java) {

    private var viewModel: ChartsTypeViewModel? = null

    override fun createViewModel(): ChartsTypeViewModel {
        return viewModel ?: run {
            val model = ChartsTypeViewModel()
            viewModel = model
            return model
        }
    }
}