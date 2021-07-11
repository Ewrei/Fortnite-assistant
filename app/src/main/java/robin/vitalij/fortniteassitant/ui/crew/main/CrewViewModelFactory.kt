package robin.vitalij.fortniteassitant.ui.crew.main

import robin.vitalij.fortniteassitant.repository.network.GameCrewRepository
import robin.vitalij.fortniteassitant.ui.common.BaseViewModelFactory
import javax.inject.Inject

class CrewViewModelFactory @Inject constructor(
    private var gameCrewRepository: GameCrewRepository
) : BaseViewModelFactory<GameCrewViewModel>(GameCrewViewModel::class.java) {

    private var viewModel: GameCrewViewModel? = null

    override fun createViewModel(): GameCrewViewModel {
        return viewModel ?: run {
            val model =
                GameCrewViewModel(gameCrewRepository)
            viewModel = model
            return model
        }
    }
}