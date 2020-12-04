package robin.vitalij.fortniteassitant.ui.fishing

import robin.vitalij.fortniteassitant.repository.FishRepository
import robin.vitalij.fortniteassitant.ui.common.BaseViewModelFactory
import javax.inject.Inject

class FishingViewModelFactory @Inject constructor(
    private var fishRepository: FishRepository
) : BaseViewModelFactory<FishingViewModel>(FishingViewModel::class.java) {

    private var viewModel: FishingViewModel? = null

    override fun createViewModel(): FishingViewModel {
        return viewModel ?: run {
            val model =
                FishingViewModel(fishRepository)
            viewModel = model
            return model
        }
    }
}