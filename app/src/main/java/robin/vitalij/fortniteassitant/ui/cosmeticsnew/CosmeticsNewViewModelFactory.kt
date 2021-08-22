package robin.vitalij.fortniteassitant.ui.cosmeticsnew

import robin.vitalij.fortniteassitant.repository.CosmeticsNewRepository
import robin.vitalij.fortniteassitant.ui.common.BaseViewModelFactory
import javax.inject.Inject

class CosmeticsNewViewModelFactory @Inject constructor(
    private val cosmeticsNewRepository: CosmeticsNewRepository
) : BaseViewModelFactory<CosmeticsNewViewModel>(CosmeticsNewViewModel::class.java) {

    private var viewModel: CosmeticsNewViewModel? = null

    override fun createViewModel(): CosmeticsNewViewModel {
        return viewModel ?: run {
            val model =
                CosmeticsNewViewModel(cosmeticsNewRepository)
            viewModel = model
            return model
        }
    }
}