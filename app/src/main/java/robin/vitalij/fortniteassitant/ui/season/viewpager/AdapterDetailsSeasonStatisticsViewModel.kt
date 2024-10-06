package robin.vitalij.fortniteassitant.ui.season.viewpager

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import robin.vitalij.fortniteassitant.common.extensions.converterUserEntity
import robin.vitalij.fortniteassitant.common.extensions.getDetailStatisticsModelList
import robin.vitalij.fortniteassitant.model.DetailStatisticsModel
import robin.vitalij.fortniteassitant.model.LoadingState
import robin.vitalij.fortniteassitant.repository.network.GetSeasonStatisticsRepository
import robin.vitalij.fortniteassitant.repository.storage.PreferenceManager
import robin.vitalij.fortniteassitant.ui.common.BaseViewModel

class AdapterDetailsSeasonStatisticsViewModel(
    private val getSeasonStatisticsRepository: GetSeasonStatisticsRepository,
    private val preferenceManager: PreferenceManager
) : BaseViewModel() {

    var detailsStatistics: MutableList<DetailStatisticsModel> = mutableListOf()

    private val sessionStatsState =
        MutableStateFlow<LoadingState<List<DetailStatisticsModel>>>(LoadingState.Loading)

    val sessionStatsResult: StateFlow<LoadingState<List<DetailStatisticsModel>>> = sessionStatsState

    private var job: Job? = null

    fun loadData() {
        job?.cancel()
        job = viewModelScope.launch {
            getSeasonStatisticsRepository.getSeasonStatsNewVersion(preferenceManager.getPlayerId())
                .collect { loadingState ->
                    when (loadingState) {
                        is LoadingState.Success -> {
                            val userEntity = loadingState.data.converterUserEntity()
                            detailsStatistics = userEntity.getDetailStatisticsModelList()
                            getSeasonStatisticsRepository.seasonStats.onNext(userEntity)

                            sessionStatsState.value = LoadingState.Success(detailsStatistics)
                        }

                        is LoadingState.Loading -> {
                            sessionStatsState.value = loadingState
                        }

                        is LoadingState.Error -> {
                            sessionStatsState.value = loadingState
                        }
                    }
                }
        }
    }

}