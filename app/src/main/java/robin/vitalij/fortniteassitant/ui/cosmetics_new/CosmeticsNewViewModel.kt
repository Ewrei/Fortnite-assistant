package robin.vitalij.fortniteassitant.ui.cosmetics_new

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import robin.vitalij.fortniteassitant.db.entity.CosmeticsNewEntity
import robin.vitalij.fortniteassitant.model.LoadingState
import robin.vitalij.fortniteassitant.repository.CosmeticsNewRepository
import robin.vitalij.fortniteassitant.ui.common.BaseViewModel

class CosmeticsNewViewModel(
    private val cosmeticsNewRepository: CosmeticsNewRepository
) : BaseViewModel() {

    private val cosmeticsNewState =
        MutableStateFlow<LoadingState<List<CosmeticsNewEntity>>>(LoadingState.Loading)

    val cosmeticsNewResult: StateFlow<LoadingState<List<CosmeticsNewEntity>>> = cosmeticsNewState

    private var job: Job? = null

    fun loadData() {
        job?.cancel()
        job = viewModelScope.launch {
            cosmeticsNewRepository.getCosmeticsNew().collect { loadingState ->
                cosmeticsNewState.value = loadingState
            }
        }
    }
}