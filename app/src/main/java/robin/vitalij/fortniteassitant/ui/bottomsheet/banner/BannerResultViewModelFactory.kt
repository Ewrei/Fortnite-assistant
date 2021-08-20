package robin.vitalij.fortniteassitant.ui.bottomsheet.banner

import robin.vitalij.fortniteassitant.repository.BannerRepository
import robin.vitalij.fortniteassitant.ui.common.BaseViewModelFactory
import javax.inject.Inject

class BannerResultViewModelFactory @Inject constructor(
    private val bannerRepository: BannerRepository
) : BaseViewModelFactory<BannerResultViewModel>(BannerResultViewModel::class.java) {

    private var viewModel: BannerResultViewModel? = null

    override fun createViewModel(): BannerResultViewModel {
        return viewModel ?: run {
            val model = BannerResultViewModel(bannerRepository)
            viewModel = model
            return model
        }
    }
}