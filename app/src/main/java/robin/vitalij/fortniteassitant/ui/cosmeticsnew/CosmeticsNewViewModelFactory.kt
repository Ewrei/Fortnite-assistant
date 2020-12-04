package robin.vitalij.fortniteassitant.ui.cosmeticsnew

import robin.vitalij.fortniteassitant.repository.CosmeticsNewRepositoryRepository
import robin.vitalij.fortniteassitant.ui.common.BaseViewModelFactory
import javax.inject.Inject

class CosmeticsNewViewModelFactory @Inject constructor(
    private var cosmeticsNewRepositoryRepository: CosmeticsNewRepositoryRepository
) : BaseViewModelFactory<CosmeticsNewViewModel>(CosmeticsNewViewModel::class.java) {

    private var viewModel: CosmeticsNewViewModel? = null

    override fun createViewModel(): CosmeticsNewViewModel {
        return viewModel ?: run {
            val model =
                CosmeticsNewViewModel(cosmeticsNewRepositoryRepository)
            viewModel = model
            return model
        }
    }
}