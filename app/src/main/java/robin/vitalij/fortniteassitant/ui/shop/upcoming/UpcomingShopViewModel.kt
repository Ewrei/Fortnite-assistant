package robin.vitalij.fortniteassitant.ui.shop.upcoming

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import robin.vitalij.fortniteassitant.model.LoadingState
import robin.vitalij.fortniteassitant.model.network.shop.ItemShopUpcoming
import robin.vitalij.fortniteassitant.repository.network.GetUpcomingShopRepository
import robin.vitalij.fortniteassitant.ui.common.BaseViewModel

class UpcomingShopViewModel(
    private val getUpcomingShopRepository: GetUpcomingShopRepository
) : BaseViewModel() {

    private val upcomingShopState =
        MutableStateFlow<LoadingState<List<ItemShopUpcoming>>>(LoadingState.Loading)

    val upcomingShopResult: StateFlow<LoadingState<List<ItemShopUpcoming>>> = upcomingShopState

    private var job: Job? = null

    fun loadData() {
        job?.cancel()
        job = viewModelScope.launch {
            getUpcomingShopRepository.getUpcomingShop().collect { loadingState ->
                upcomingShopState.value = loadingState
            }
        }
    }

}