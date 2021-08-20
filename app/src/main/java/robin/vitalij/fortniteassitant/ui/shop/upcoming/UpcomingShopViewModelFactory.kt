package robin.vitalij.fortniteassitant.ui.shop.upcoming

import robin.vitalij.fortniteassitant.repository.network.GetUpcomingShopRepository
import robin.vitalij.fortniteassitant.ui.common.BaseViewModelFactory
import javax.inject.Inject

class UpcomingShopViewModelFactory @Inject constructor(
    private val getUpcomingShopRepository: GetUpcomingShopRepository
) : BaseViewModelFactory<UpcomingShopViewModel>(UpcomingShopViewModel::class.java) {

    private var viewModel: UpcomingShopViewModel? = null

    override fun createViewModel(): UpcomingShopViewModel {
        return viewModel ?: run {
            val model =
                UpcomingShopViewModel(
                    getUpcomingShopRepository
                )
            viewModel = model
            return model
        }
    }
}