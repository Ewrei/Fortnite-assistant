package robin.vitalij.fortniteassitant.ui.bottomsheet.cosmetic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import robin.vitalij.fortniteassitant.model.LoadingState
import robin.vitalij.fortniteassitant.repository.db.CosmeticsResultRepository
import robin.vitalij.fortniteassitant.ui.bottomsheet.cosmetic.adapter.CosmeticsListItem

class CosmeticResultViewModel(
    private val cosmeticsResultRepository: CosmeticsResultRepository
) : ViewModel() {

    var cosmeticsId: String = ""
    var isCosmeticNew: Boolean = false

    private val cosmeticsState =
        MutableStateFlow<LoadingState<List<CosmeticsListItem>>>(LoadingState.Loading)

    val cosmeticsResult: StateFlow<LoadingState<List<CosmeticsListItem>>> = cosmeticsState

    private var job: Job? = null

    fun loadData() {
        job?.cancel()
        job = viewModelScope.launch {
            cosmeticsResultRepository
                .getCosmetics(cosmeticsId, isCosmeticNew).collect { loadingState ->
                    cosmeticsState.value = loadingState
                }
        }
    }

}