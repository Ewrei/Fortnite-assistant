package robin.vitalij.fortniteassitant.ui.details.statistics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import robin.vitalij.fortniteassitant.model.LoadingState
import robin.vitalij.fortniteassitant.model.enums.BattlesType
import robin.vitalij.fortniteassitant.model.enums.GameType
import robin.vitalij.fortniteassitant.repository.db.DetailsStatisticsRepository
import robin.vitalij.fortniteassitant.repository.storage.PreferenceManager
import robin.vitalij.fortniteassitant.ui.home.adapter.viewholder.statistics.adapter.HomeBodyStatsListItem

class DetailsStatisticsViewModel(
    private val detailsStatisticsRepository: DetailsStatisticsRepository,
    private val preferenceManager: PreferenceManager
) : ViewModel() {

    private val detailsStatisticsState =
        MutableStateFlow<LoadingState<List<HomeBodyStatsListItem>>>(LoadingState.Loading)

    val detailsStatisticsResult: StateFlow<LoadingState<List<HomeBodyStatsListItem>>> = detailsStatisticsState

    var battlesType: BattlesType = BattlesType.SOLO
    var gameType: GameType = GameType.ALL

    private var job: Job? = null

    fun loadData() {
        job?.cancel()
        job = viewModelScope.launch {
            detailsStatisticsRepository.getDetailsStatistics(
                preferenceManager.getPlayerId(),
                battlesType,
                gameType
            ).collect { loadingState ->
                detailsStatisticsState.value = loadingState
            }
        }
    }
}