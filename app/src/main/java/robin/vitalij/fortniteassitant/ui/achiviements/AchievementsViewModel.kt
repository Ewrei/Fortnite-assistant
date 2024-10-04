package robin.vitalij.fortniteassitant.ui.achiviements

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import robin.vitalij.fortniteassitant.db.entity.AchievementEntity
import robin.vitalij.fortniteassitant.model.LoadingState
import robin.vitalij.fortniteassitant.repository.AchievementRepository

class AchievementsViewModel(
    private val achievementRepository: AchievementRepository
) : ViewModel() {

    private val achievementsState =
        MutableStateFlow<LoadingState<List<AchievementEntity>>>(LoadingState.Loading)

    val achievementsResult: StateFlow<LoadingState<List<AchievementEntity>>> = achievementsState

    private var job: Job? = null

    fun loadData() {
        job?.cancel()
        job = viewModelScope.launch {
            achievementRepository.getAchievements().collect { loadingState ->
                achievementsState.value = loadingState
            }
        }
    }
}