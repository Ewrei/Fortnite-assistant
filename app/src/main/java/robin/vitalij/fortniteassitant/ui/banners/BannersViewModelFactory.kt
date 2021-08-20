package robin.vitalij.fortniteassitant.ui.banners

import robin.vitalij.fortniteassitant.repository.BannerRepository
import robin.vitalij.fortniteassitant.ui.common.BaseViewModelFactory
import javax.inject.Inject

class BannersViewModelFactory @Inject constructor(
    private val bannerRepository: BannerRepository
) : BaseViewModelFactory<BannersViewModel>(BannersViewModel::class.java) {

    private var viewModel: BannersViewModel? = null

    override fun createViewModel(): BannersViewModel {
        return viewModel ?: run {
            val model =
                BannersViewModel(bannerRepository)
            viewModel = model
            return model
        }
    }
}