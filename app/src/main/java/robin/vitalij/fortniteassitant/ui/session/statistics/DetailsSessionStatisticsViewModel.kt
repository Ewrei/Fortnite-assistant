package robin.vitalij.fortniteassitant.ui.session.statistics

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
import robin.vitalij.fortniteassitant.ui.home.adapter.viewholder.statistics.adapter.HomeBodyStatsListItem
import robin.vitalij.fortniteassitant.utils.mapper.HomeSessionRepository

class DetailsSessionStatisticsViewModel(
    private val homeSessionRepository: HomeSessionRepository
) : ViewModel() {

    var battlesType: BattlesType = BattlesType.DUO
    var gameType: GameType = GameType.ALL
    var sessionId: Long = 0
    var sessionLastId: Long = 0

    private val detailsStatisticsState =
        MutableStateFlow<LoadingState<List<HomeBodyStatsListItem>>>(LoadingState.Loading)

    val detailsStatisticsResult: StateFlow<LoadingState<List<HomeBodyStatsListItem>>> =
        detailsStatisticsState

    private var job: Job? = null

    fun loadData() {
        job?.cancel()
        job = viewModelScope.launch {
            homeSessionRepository
                .getDetailsStatistics(battlesType, gameType, sessionId, sessionLastId)
                .collect { loadingState ->
                    detailsStatisticsState.value = loadingState
                }
        }
    }

}