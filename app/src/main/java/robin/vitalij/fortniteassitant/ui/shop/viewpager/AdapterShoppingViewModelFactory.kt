package robin.vitalij.fortniteassitant.ui.shop.viewpager

import robin.vitalij.fortniteassitant.ui.common.BaseViewModelFactory
import javax.inject.Inject

class AdapterShoppingViewModelFactory @Inject constructor() :
    BaseViewModelFactory<AdapterShoppingViewModel>(AdapterShoppingViewModel::class.java) {

    private var viewModel: AdapterShoppingViewModel? = null

    override fun createViewModel(): AdapterShoppingViewModel {
        return viewModel ?: run {
            val model = AdapterShoppingViewModel()
            viewModel = model
            return model
        }
    }
}