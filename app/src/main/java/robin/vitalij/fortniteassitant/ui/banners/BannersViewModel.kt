package robin.vitalij.fortniteassitant.ui.banners

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import robin.vitalij.fortniteassitant.db.entity.BannerEntity
import robin.vitalij.fortniteassitant.model.LoadingState
import robin.vitalij.fortniteassitant.repository.BannerRepository

class BannersViewModel(
    private val bannerRepository: BannerRepository
) : ViewModel() {

    private val bannersState =
        MutableStateFlow<LoadingState<List<BannerEntity>>>(LoadingState.Loading)

    val bannersResult: StateFlow<LoadingState<List<BannerEntity>>> = bannersState

    private var job: Job? = null

    fun loadData() {
        job?.cancel()
        job = viewModelScope.launch {
            bannerRepository.getBanners().collect { loadingState ->
                bannersState.value = loadingState
            }
        }
    }

}