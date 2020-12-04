package robin.vitalij.fortniteassitant.ui.bottomsheet.fish

import robin.vitalij.fortniteassitant.ui.common.BaseViewModelFactory
import javax.inject.Inject

class FishResultViewModelFactory @Inject constructor(
) : BaseViewModelFactory<FishResultViewModel>(FishResultViewModel::class.java) {

    private var viewModel: FishResultViewModel? = null

    override fun createViewModel(): FishResultViewModel {
        return viewModel ?: run {
            val model = FishResultViewModel()
            viewModel = model
            return model
        }
    }
}