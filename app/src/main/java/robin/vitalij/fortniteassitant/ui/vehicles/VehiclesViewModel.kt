package robin.vitalij.fortniteassitant.ui.vehicles

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import robin.vitalij.fortniteassitant.model.LoadingState
import robin.vitalij.fortniteassitant.model.network.VehicleModel
import robin.vitalij.fortniteassitant.repository.network.GameVehiclesRepository
import robin.vitalij.fortniteassitant.ui.common.BaseViewModel

class VehiclesViewModel(
    private val gameVehiclesRepository: GameVehiclesRepository
) : BaseViewModel() {

    private val gameVehiclesState =
        MutableStateFlow<LoadingState<List<VehicleModel>>>(LoadingState.Loading)

    val gameVehiclesResult: StateFlow<LoadingState<List<VehicleModel>>> = gameVehiclesState

    private var job: Job? = null

    fun loadData() {
        job?.cancel()
        job = viewModelScope.launch {
            gameVehiclesRepository.getGameCrew().collect { loadingState ->
                gameVehiclesState.value = loadingState
            }
        }
    }
}