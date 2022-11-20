package robin.vitalij.fortniteassitant.ui.charts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import robin.vitalij.fortniteassitant.model.ChartsModel
import robin.vitalij.fortniteassitant.model.LoadingState
import robin.vitalij.fortniteassitant.model.enums.BattlesType
import robin.vitalij.fortniteassitant.model.enums.ChartsType
import robin.vitalij.fortniteassitant.model.enums.GameType
import robin.vitalij.fortniteassitant.repository.db.ChartsRepository
import robin.vitalij.fortniteassitant.repository.storage.PreferenceManager
import java.util.*

class ChartsViewModel(
    private val chartsRepository: ChartsRepository,
    private val preferenceManager: PreferenceManager
) : ViewModel() {

    var chartsType: ChartsType =  ChartsType.AVG_SCORE
    var battlesType: BattlesType =  BattlesType.DUO
    var gameType: GameType = GameType.ALL

    private val chartsState =
        MutableStateFlow<LoadingState<ChartsModel>>(LoadingState.Loading)

    val chartsResult: StateFlow<LoadingState<ChartsModel>> = chartsState

    private var job: Job? = null

    fun loadData() {
        job?.cancel()
        job = viewModelScope.launch {
            chartsRepository.loadData(
                preferenceManager.getPlayerId(),
                chartsType,
                battlesType,
                gameType
            ).collect { loadingState ->
                chartsState.value = loadingState
            }
        }
    }

    fun checkSubscriptionAccess() = !preferenceManager.getIsSubscription() && !(preferenceManager.getSubscriptionAccess() > Date().time)
}