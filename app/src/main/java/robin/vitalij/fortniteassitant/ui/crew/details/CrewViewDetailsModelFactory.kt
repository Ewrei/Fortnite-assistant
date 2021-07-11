package robin.vitalij.fortniteassitant.ui.crew.details

import robin.vitalij.fortniteassitant.ui.common.BaseViewModelFactory
import javax.inject.Inject

class CrewViewDetailsModelFactory @Inject constructor() :
    BaseViewModelFactory<CrewViewDetailsViewModel>(CrewViewDetailsViewModel::class.java) {

    private var viewModel: CrewViewDetailsViewModel? = null

    override fun createViewModel(): CrewViewDetailsViewModel {
        return viewModel ?: run {
            val model =
                CrewViewDetailsViewModel()
            viewModel = model
            return model
        }
    }
}