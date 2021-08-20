package robin.vitalij.fortniteassitant.ui.cosmetics

import robin.vitalij.fortniteassitant.repository.CatalogCosmeticsRepository
import robin.vitalij.fortniteassitant.ui.common.BaseViewModelFactory
import javax.inject.Inject

class CatalogCosmeticsViewModelFactory @Inject constructor(
    private var catalogCosmeticsRepository: CatalogCosmeticsRepository
) : BaseViewModelFactory<CatalogCosmeticsViewModel>(CatalogCosmeticsViewModel::class.java) {

    private var viewModel: CatalogCosmeticsViewModel? = null

    override fun createViewModel(): CatalogCosmeticsViewModel {
        return viewModel ?: run {
            val model =
                CatalogCosmeticsViewModel(catalogCosmeticsRepository)
            viewModel = model
            return model
        }
    }
}