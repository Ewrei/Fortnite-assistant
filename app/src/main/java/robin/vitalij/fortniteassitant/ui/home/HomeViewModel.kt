package robin.vitalij.fortniteassitant.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import robin.vitalij.fortniteassitant.model.DetailStatisticsModel
import robin.vitalij.fortniteassitant.model.FullHomeModel
import robin.vitalij.fortniteassitant.model.LoadingState
import robin.vitalij.fortniteassitant.repository.db.HomeRepository
import robin.vitalij.fortniteassitant.repository.storage.PreferenceManager
import robin.vitalij.fortniteassitant.ui.common.BaseViewModel

class HomeViewModel(
    private val homeRepository: HomeRepository,
    private val preferenceManager: PreferenceManager
) : ViewModel() {

    private val detailsStatisticsState =
        MutableStateFlow<LoadingState<FullHomeModel>>(LoadingState.Loading)

    val detailsStatisticsResult: StateFlow<LoadingState<FullHomeModel>> = detailsStatisticsState

    var detailsStatistics: ArrayList<DetailStatisticsModel> = arrayListOf()

    private var job: Job? = null

    fun loadData() {
        job?.cancel()
        job = viewModelScope.launch {
            homeRepository.loadData(preferenceManager.getPlayerId()).collect { loadingState ->
                if(loadingState is LoadingState.Success) {
                    detailsStatistics.clear()
                    detailsStatistics.addAll(loadingState.data.details)
                }
                detailsStatisticsState.value = loadingState
            }
        }
    }

}