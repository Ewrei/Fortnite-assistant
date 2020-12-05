package robin.vitalij.fortniteassitant.ui.bottomsheet.cosmetic

import robin.vitalij.fortniteassitant.repository.db.CosmeticsResultRepository
import robin.vitalij.fortniteassitant.ui.common.BaseViewModelFactory
import javax.inject.Inject

class CosmeticResultViewModelFactory @Inject constructor(
    private val cosmeticsResultRepository: CosmeticsResultRepository
) : BaseViewModelFactory<CosmeticResultViewModel>(CosmeticResultViewModel::class.java) {

    private var viewModel: CosmeticResultViewModel? = null

    override fun createViewModel(): CosmeticResultViewModel {
        return viewModel ?: run {
            val model = CosmeticResultViewModel(cosmeticsResultRepository)
            viewModel = model
            return model
        }
    }
}