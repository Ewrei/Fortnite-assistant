package robin.vitalij.fortniteassitant.ui.battle_pass_rewards

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import robin.vitalij.fortniteassitant.model.LoadingState
import robin.vitalij.fortniteassitant.model.battle_pass_reward.FullBattlePassRewardModel
import robin.vitalij.fortniteassitant.model.battle_pass_reward.SeasonModel
import robin.vitalij.fortniteassitant.model.enums.BattlePassSortedType
import robin.vitalij.fortniteassitant.repository.network.BattlesPassRewardRepository

class BattlePassRewardsViewModel(
    private val battlesPassRewardRepository: BattlesPassRewardRepository
) : ViewModel() {

    private val filterTriggerFlow = MutableStateFlow(BattlePassSortedType.ALL)

    val seasonsResult = MutableStateFlow<List<SeasonModel>>(emptyList())

    private val fullBattlePassRewardState =
        MutableStateFlow<LoadingState<FullBattlePassRewardModel>>(LoadingState.Loading)

    private var job: Job? = null

    var currentSeason = CURRENT_SEASON

    val filterResult = filterTriggerFlow.combine(fullBattlePassRewardState) { battlePassSortedType, state ->
        return@combine when (state) {
            is LoadingState.Success -> {
                if (currentSeason == CURRENT_SEASON) {
                    seasonsResult.value = state.data.seasons
                }

                LoadingState.Success(
                    when (battlePassSortedType) {
                        BattlePassSortedType.ALL -> {
                            state.data.battlesPassRewards
                        }
                        BattlePassSortedType.FREE -> {
                            state.data.battlesPassRewards.filter { it.isFree }
                        }
                        BattlePassSortedType.PAID -> {
                            state.data.battlesPassRewards.filter { !it.isFree }
                        }
                    })
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
            battlesPassRewardRepository.getBattlesPassReward(currentSeason)
                .collect { loadingState ->
                    fullBattlePassRewardState.value = loadingState
                }
        }
    }

    fun filterBattlesPassReward(battlePassSortedType: BattlePassSortedType) {
        filterTriggerFlow.value = battlePassSortedType
    }

    companion object {
        const val CURRENT_SEASON = "current"
    }

}