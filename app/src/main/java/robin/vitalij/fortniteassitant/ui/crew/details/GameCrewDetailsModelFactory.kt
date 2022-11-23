package robin.vitalij.fortniteassitant.ui.crew.details

import robin.vitalij.fortniteassitant.ui.common.BaseViewModelFactory
import javax.inject.Inject

class GameCrewDetailsModelFactory @Inject constructor() :
    BaseViewModelFactory<GameCrewDetailsViewModel>(GameCrewDetailsViewModel::class.java) {

    private var viewModel: GameCrewDetailsViewModel? = null

    override fun createViewModel(): GameCrewDetailsViewModel {
        return viewModel ?: run {
            val model = GameCrewDetailsViewModel()
            viewModel = model
            return model
        }
    }
}