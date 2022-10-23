package robin.vitalij.fortniteassitant.ui.crew.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import robin.vitalij.fortniteassitant.model.LoadingState
import robin.vitalij.fortniteassitant.model.network.CrewModel
import robin.vitalij.fortniteassitant.repository.network.GameCrewRepository
import robin.vitalij.fortniteassitant.ui.common.BaseViewModel

class GameCrewViewModel(private val gameCrewRepository: GameCrewRepository) : BaseViewModel() {

    val mutableLiveData = MutableLiveData<List<CrewModel>>()

    private val newsState =
        MutableStateFlow<LoadingState<List<CrewModel>>>(LoadingState.Loading)

    val newsResult: StateFlow<LoadingState<List<CrewModel>>> = newsState

    private var job: Job? = null

    fun loadData() {
        job?.cancel()
        job = viewModelScope.launch {
            gameCrewRepository.getGameCrew().collect { loadingState ->
                newsState.value = loadingState
            }
        }
    }
}