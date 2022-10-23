package robin.vitalij.fortniteassitant.ui.cosmetics

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import robin.vitalij.fortniteassitant.model.LoadingState
import robin.vitalij.fortniteassitant.model.enums.ShopType
import robin.vitalij.fortniteassitant.repository.CatalogCosmeticsRepository
import robin.vitalij.fortniteassitant.ui.common.BaseViewModel

class CatalogCosmeticsViewModel(
    private val catalogCosmeticsRepository: CatalogCosmeticsRepository
) : BaseViewModel() {

    private val shopTypesState =
        MutableStateFlow<LoadingState<List<ShopType>>>(LoadingState.Loading)

    val shopTypesResult: StateFlow<LoadingState<List<ShopType>>> = shopTypesState

    private var job: Job? = null

    fun loadData() {
        job?.cancel()
        job = viewModelScope.launch {
            catalogCosmeticsRepository.getShopTypes().collect { loadingState ->
                shopTypesState.value = loadingState
            }
        }
    }

}