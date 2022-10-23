package robin.vitalij.fortniteassitant.ui.cosmetics.catalog

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import robin.vitalij.fortniteassitant.db.entity.CosmeticsEntity
import robin.vitalij.fortniteassitant.model.LoadingState
import robin.vitalij.fortniteassitant.model.enums.ShopType
import robin.vitalij.fortniteassitant.repository.CatalogCosmeticsRepository
import robin.vitalij.fortniteassitant.ui.common.BaseViewModel

class CosmeticsViewModel(
    private val catalogCosmeticsRepository: CatalogCosmeticsRepository
) : BaseViewModel() {

    var shopType: ShopType = ShopType.BANNER

    private val cosmeticsState =
        MutableStateFlow<LoadingState<List<CosmeticsEntity>>>(LoadingState.Loading)

    val cosmeticsResult: StateFlow<LoadingState<List<CosmeticsEntity>>> = cosmeticsState

    private var job: Job? = null

    fun loadData() {
        job?.cancel()
        job = viewModelScope.launch {
            catalogCosmeticsRepository.getCosmetics(shopType).collect { loadingState ->
                cosmeticsState.value = loadingState
            }
        }
    }

}