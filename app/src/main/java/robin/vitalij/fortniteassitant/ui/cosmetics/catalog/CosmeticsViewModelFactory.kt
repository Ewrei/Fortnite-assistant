package robin.vitalij.fortniteassitant.ui.cosmetics.catalog

import robin.vitalij.fortniteassitant.repository.CatalogCosmeticsRepository
import robin.vitalij.fortniteassitant.ui.common.BaseViewModelFactory
import javax.inject.Inject

class CosmeticsViewModelFactory @Inject constructor(
    private var catalogCosmeticsRepository: CatalogCosmeticsRepository
) : BaseViewModelFactory<CosmeticsViewModel>(CosmeticsViewModel::class.java) {

    private var viewModel: CosmeticsViewModel? = null

    override fun createViewModel(): CosmeticsViewModel {
        return viewModel ?: run {
            val model =
                CosmeticsViewModel(catalogCosmeticsRepository)
            viewModel = model
            return model
        }
    }
}