package robin.vitalij.fortniteassitant.ui.bottomsheet.fish

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import robin.vitalij.fortniteassitant.db.entity.FishEntity
import robin.vitalij.fortniteassitant.model.LoadingState
import robin.vitalij.fortniteassitant.repository.FishRepository

class FishResultViewModel(
    private val fishRepository: FishRepository
) : ViewModel() {

    var fishId: String = ""

    private val fishState = MutableStateFlow<LoadingState<FishEntity>>(LoadingState.Loading)

    val fishResult: StateFlow<LoadingState<FishEntity>> = fishState

    private var job: Job? = null

    fun loadData() {
        job?.cancel()
        job = viewModelScope.launch {
            fishRepository.getFish(fishId).collect { loadingState ->
                fishState.value = loadingState
            }
        }
    }

}