package robin.vitalij.fortniteassitant.ui.shop.current

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import robin.vitalij.fortniteassitant.model.LoadingState
import robin.vitalij.fortniteassitant.model.network.shop.ShopAdapterItem
import robin.vitalij.fortniteassitant.repository.network.GetCurrentShopRepository

class CurrentShopViewModel(
    private val getCurrentShopRepository: GetCurrentShopRepository
) : ViewModel() {

    private val currentShopState =
        MutableStateFlow<LoadingState<List<ShopAdapterItem>>>(LoadingState.Loading)

    val currentShopResult: StateFlow<LoadingState<List<ShopAdapterItem>>> = currentShopState

    private var job: Job? = null

    fun loadData() {
        job?.cancel()
        job = viewModelScope.launch {
            getCurrentShopRepository.getCurrentShop().collect { loadingState ->
                currentShopState.value = loadingState
            }
        }
    }
}