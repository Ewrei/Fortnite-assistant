package robin.vitalij.fortniteassitant.ui.fishstats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import robin.vitalij.fortniteassitant.common.extensions.getSeason
import robin.vitalij.fortniteassitant.model.LoadingState
import robin.vitalij.fortniteassitant.model.battle_pass_reward.SeasonModel
import robin.vitalij.fortniteassitant.model.network.FishStatsModel
import robin.vitalij.fortniteassitant.model.network.SeasonStatsFishModel
import robin.vitalij.fortniteassitant.repository.FishStatsRepository
import robin.vitalij.fortniteassitant.repository.storage.PreferenceManager
import robin.vitalij.fortniteassitant.utils.ResourceProvider

class FishStatsViewModel(
    private val fishStatsRepository: FishStatsRepository,
    private val preferenceManager: PreferenceManager,
    private val resourceProvider: ResourceProvider
) : ViewModel() {

    private val filterTriggerFlow = MutableStateFlow<SeasonModel?>(null)

    val seasonsResult = MutableStateFlow<List<SeasonModel>>(emptyList())

    private val fullFishStatsState =
        MutableStateFlow<LoadingState<List<SeasonStatsFishModel>>>(LoadingState.Loading)

    private var job: Job? = null

    val filterResult = filterTriggerFlow.combine(fullFishStatsState) { seasonModel, state ->
        return@combine when (state) {
            is LoadingState.Success -> {
                val seasonStats = state.data.find { it.season == seasonModel?.season }
                    ?: state.data.firstOrNull()
                LoadingState.Success(seasonStats?.fish ?: emptyList<FishStatsModel>())
            }
            is LoadingState.Loading -> {
                return@combine LoadingState.Loading
            }
            is LoadingState.Error -> {
                return@combine LoadingState.Error(state.cause)
            }
        }
    }

    fun loadData() {
        job?.cancel()
        job = viewModelScope.launch {
            fishStatsRepository.getFishStats(preferenceManager.getPlayerId())
                .collect { loadingState ->
                    fullFishStatsState.value = loadingState

                    if (loadingState is LoadingState.Success) {
                        seasonsResult.value = loadingState.data.getSeason(resourceProvider)
                    }
                }
        }
    }

    fun changeSeason(seasonModel: SeasonModel) {
        filterTriggerFlow.value = seasonModel
    }

}