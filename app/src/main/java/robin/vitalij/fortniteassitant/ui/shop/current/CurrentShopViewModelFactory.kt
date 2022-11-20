package robin.vitalij.fortniteassitant.ui.shop.current

import robin.vitalij.fortniteassitant.repository.network.GetCurrentShopRepository
import robin.vitalij.fortniteassitant.ui.common.BaseViewModelFactory
import javax.inject.Inject

class CurrentShopViewModelFactory @Inject constructor(
    private val getCurrentShopRepository: GetCurrentShopRepository
) : BaseViewModelFactory<CurrentShopViewModel>(CurrentShopViewModel::class.java) {

    private var viewModel: CurrentShopViewModel? = null

    override fun createViewModel(): CurrentShopViewModel {
        return viewModel ?: run {
            val model =
                CurrentShopViewModel(
                    getCurrentShopRepository
                )
            viewModel = model
            return model
        }
    }
}