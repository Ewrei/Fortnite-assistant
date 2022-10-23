package robin.vitalij.fortniteassitant.ui.fishing

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import robin.vitalij.fortniteassitant.db.entity.FishEntity
import robin.vitalij.fortniteassitant.model.LoadingState
import robin.vitalij.fortniteassitant.repository.FishRepository
import robin.vitalij.fortniteassitant.ui.common.BaseViewModel

class FishingViewModel(
    private val fishRepository: FishRepository
) : BaseViewModel() {

    private val fishState =
        MutableStateFlow<LoadingState<List<FishEntity>>>(LoadingState.Loading)

    val fishResult: StateFlow<LoadingState<List<FishEntity>>> = fishState

    private var job: Job? = null

    fun loadData() {
        job?.cancel()
        job = viewModelScope.launch {
            fishRepository.getFish().collect { loadingState ->
                fishState.value = loadingState
            }
        }
    }

}