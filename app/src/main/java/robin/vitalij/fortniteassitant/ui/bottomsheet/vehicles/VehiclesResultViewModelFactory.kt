package robin.vitalij.fortniteassitant.ui.bottomsheet.vehicles

import robin.vitalij.fortniteassitant.ui.common.BaseViewModelFactory
import javax.inject.Inject

class VehiclesResultViewModelFactory @Inject constructor(
) : BaseViewModelFactory<VehiclesResultViewModel>(VehiclesResultViewModel::class.java) {

    private var viewModel: VehiclesResultViewModel? = null

    override fun createViewModel(): VehiclesResultViewModel {
        return viewModel ?: run {
            val model = VehiclesResultViewModel()
            viewModel = model
            return model
        }
    }
}