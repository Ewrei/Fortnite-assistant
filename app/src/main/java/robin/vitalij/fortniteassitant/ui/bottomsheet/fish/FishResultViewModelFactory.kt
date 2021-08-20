package robin.vitalij.fortniteassitant.ui.bottomsheet.fish

import robin.vitalij.fortniteassitant.repository.FishRepository
import robin.vitalij.fortniteassitant.ui.common.BaseViewModelFactory
import javax.inject.Inject

class FishResultViewModelFactory @Inject constructor(
    private val fishRepository: FishRepository
) : BaseViewModelFactory<FishResultViewModel>(FishResultViewModel::class.java) {

    private var viewModel: FishResultViewModel? = null

    override fun createViewModel(): FishResultViewModel {
        return viewModel ?: run {
            val model = FishResultViewModel(fishRepository)
            viewModel = model
            return model
        }
    }
}