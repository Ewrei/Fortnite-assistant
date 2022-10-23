package robin.vitalij.fortniteassitant.ui.history

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import robin.vitalij.fortniteassitant.model.HistoryUserModel
import robin.vitalij.fortniteassitant.model.LoadingState
import robin.vitalij.fortniteassitant.repository.db.HistoryRepository
import robin.vitalij.fortniteassitant.repository.storage.PreferenceManager
import robin.vitalij.fortniteassitant.ui.common.BaseViewModel

class HistoryViewModel(
    private val historyRepository: HistoryRepository,
    private val preferenceManager: PreferenceManager
) : BaseViewModel() {

    private val historiesState =
        MutableStateFlow<LoadingState<List<HistoryUserModel>>>(LoadingState.Loading)

    val historiesResult: StateFlow<LoadingState<List<HistoryUserModel>>> = historiesState

    private var job: Job? = null

    fun loadData() {
        job?.cancel()
        job = viewModelScope.launch {
            historyRepository.getUserHistory(preferenceManager.getPlayerId())
                .collect { loadingState ->
                    historiesState.value = loadingState
                }
        }
    }

}