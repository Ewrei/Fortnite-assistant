package robin.vitalij.fortniteassitant.ui.vehicles

import robin.vitalij.fortniteassitant.repository.network.GameVehiclesRepository
import robin.vitalij.fortniteassitant.ui.common.BaseViewModelFactory
import javax.inject.Inject

class VehiclesViewModelFactory @Inject constructor(
    private val gameVehiclesRepository: GameVehiclesRepository
) : BaseViewModelFactory<VehiclesViewModel>(VehiclesViewModel::class.java) {

    private var viewModel: VehiclesViewModel? = null

    override fun createViewModel(): VehiclesViewModel {
        return viewModel ?: run {
            val model =
                VehiclesViewModel(gameVehiclesRepository)
            viewModel = model
            return model
        }
    }
}