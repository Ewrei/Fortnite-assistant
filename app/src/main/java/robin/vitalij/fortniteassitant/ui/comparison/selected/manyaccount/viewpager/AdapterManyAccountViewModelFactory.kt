package robin.vitalij.fortniteassitant.ui.comparison.selected.manyaccount.viewpager

import robin.vitalij.fortniteassitant.ui.common.BaseViewModelFactory
import javax.inject.Inject

class AdapterManyAccountViewModelFactory @Inject constructor(
) : BaseViewModelFactory<AdapterManyAccountViewModel>(AdapterManyAccountViewModel::class.java) {

    private var viewModel: AdapterManyAccountViewModel? = null

    override fun createViewModel(): AdapterManyAccountViewModel {
        return viewModel ?: run {
            val model = AdapterManyAccountViewModel()
            viewModel = model
            return model
        }
    }
}