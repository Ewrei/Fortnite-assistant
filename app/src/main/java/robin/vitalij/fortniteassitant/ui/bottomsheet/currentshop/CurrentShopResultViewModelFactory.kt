package robin.vitalij.fortniteassitant.ui.bottomsheet.currentshop

import robin.vitalij.fortniteassitant.ui.common.BaseViewModelFactory
import javax.inject.Inject

class CurrentShopResultViewModelFactory @Inject constructor(
) : BaseViewModelFactory<CurrentShopResultViewModel>(CurrentShopResultViewModel::class.java) {

    private var viewModel: CurrentShopResultViewModel? = null

    override fun createViewModel(): CurrentShopResultViewModel {
        return viewModel ?: run {
            val model = CurrentShopResultViewModel()
            viewModel = model
            return model
        }
    }
}