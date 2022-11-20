package robin.vitalij.fortniteassitant.ui.weapons

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import robin.vitalij.fortniteassitant.db.entity.WeaponEntity
import robin.vitalij.fortniteassitant.model.LoadingState
import robin.vitalij.fortniteassitant.repository.WeaponRepository

class WeaponViewModel(
    private val weaponRepository: WeaponRepository
) : ViewModel() {

    private val weaponsState =
        MutableStateFlow<LoadingState<List<WeaponEntity>>>(LoadingState.Loading)

    val weaponsResult: StateFlow<LoadingState<List<WeaponEntity>>> = weaponsState

    private var job: Job? = null

    fun loadData() {
        job?.cancel()
        job = viewModelScope.launch {
            weaponRepository.getWeapons().collect { loadingState ->
                weaponsState.value = loadingState
            }
        }
    }
}