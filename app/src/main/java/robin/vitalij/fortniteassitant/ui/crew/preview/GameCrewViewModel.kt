package robin.vitalij.fortniteassitant.ui.crew.preview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import robin.vitalij.fortniteassitant.model.LoadingState
import robin.vitalij.fortniteassitant.model.network.CrewModel
import robin.vitalij.fortniteassitant.repository.network.GameCrewRepository

class GameCrewViewModel(private val gameCrewRepository: GameCrewRepository) : ViewModel() {

    private val gameCrewsState =
        MutableStateFlow<LoadingState<List<CrewModel>>>(LoadingState.Loading)

    val gameCrewsResult: StateFlow<LoadingState<List<CrewModel>>> = gameCrewsState

    private var job: Job? = null

    fun loadData() {
        job?.cancel()
        job = viewModelScope.launch {
            gameCrewRepository.getGameCrew().collect { loadingState ->
                gameCrewsState.value = loadingState
            }
        }
    }
}