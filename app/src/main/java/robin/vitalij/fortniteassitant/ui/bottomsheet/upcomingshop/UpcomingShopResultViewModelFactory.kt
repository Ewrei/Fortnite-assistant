package robin.vitalij.fortniteassitant.ui.bottomsheet.upcomingshop

import robin.vitalij.fortniteassitant.ui.common.BaseViewModelFactory
import javax.inject.Inject

class UpcomingShopResultViewModelFactory @Inject constructor(
) : BaseViewModelFactory<UpcomingShopResultViewModel>(UpcomingShopResultViewModel::class.java) {

    private var viewModel: UpcomingShopResultViewModel? = null

    override fun createViewModel(): UpcomingShopResultViewModel {
        return viewModel ?: run {
            val model = UpcomingShopResultViewModel()
            viewModel = model
            return model
        }
    }
}