package robin.vitalij.fortniteassitant.ui.fish

import robin.vitalij.fortniteassitant.repository.FishRepository
import robin.vitalij.fortniteassitant.ui.common.BaseViewModelFactory
import javax.inject.Inject

class FishViewModelFactory @Inject constructor(
    private val fishRepository: FishRepository
) : BaseViewModelFactory<FishViewModel>(FishViewModel::class.java) {

    private var viewModel: FishViewModel? = null

    override fun createViewModel(): FishViewModel {
        return viewModel ?: run {
            val model =
                FishViewModel(fishRepository)
            viewModel = model
            return model
        }
    }
}