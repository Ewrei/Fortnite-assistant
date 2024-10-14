package robin.vitalij.fortniteassitant.ui.comparison.statistics

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import robin.vitalij.fortniteassitant.model.LoadingState
import robin.vitalij.fortniteassitant.model.enums.BattlesType
import robin.vitalij.fortniteassitant.model.enums.GameType
import robin.vitalij.fortniteassitant.repository.comparison.ComparisonProfileRepository
import robin.vitalij.fortniteassitant.ui.common.BaseViewModel
import robin.vitalij.fortniteassitant.ui.comparison.statistics.adapter.ComparisonStatisticsListItem

class ComparisonStatisticsViewModel(
    private val comparisonProfileRepository: ComparisonProfileRepository
) : BaseViewModel() {

    private val comparisonStatisticsState =
        MutableStateFlow<LoadingState<List<ComparisonStatisticsListItem>>>(LoadingState.Loading)

    val comparisonStatisticsResult: StateFlow<LoadingState<List<ComparisonStatisticsListItem>>> =
        comparisonStatisticsState

    private var job: Job? = null

    lateinit var playerOneId: String
    lateinit var playerTwoId: String
    lateinit var battlesType: BattlesType
    lateinit var gameType: GameType

    fun loadData(isSchedule: Boolean) {
        job?.cancel()
        job = viewModelScope.launch {
            comparisonProfileRepository.getComparisonProfileStatistics(
                isSchedule,
                battlesType,
                gameType
            ).collect { loadingState ->
                comparisonStatisticsState.value = loadingState
            }
        }
    }
}
