package robin.vitalij.fortniteassitant.ui.top

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import robin.vitalij.fortniteassitant.model.LoadingState
import robin.vitalij.fortniteassitant.model.TopFullModel
import robin.vitalij.fortniteassitant.repository.network.TopRepository
import robin.vitalij.fortniteassitant.ui.common.BaseViewModel
import robin.vitalij.fortniteassitant.ui.top.adapter.TopListItem

class TopViewModel(private val topRepository: TopRepository) : BaseViewModel() {

    var topType: TopFullModel = TopFullModel()

    private val topState =
        MutableStateFlow<LoadingState<List<TopListItem>>>(LoadingState.Loading)

    val topResult: StateFlow<LoadingState<List<TopListItem>>> = topState

    private var job: Job? = null

    fun loadData() {
        job?.cancel()
        job = viewModelScope.launch {
            topRepository.getTopUsers(topType).collect { loadingState ->
                topState.value = loadingState
            }
        }
    }

}