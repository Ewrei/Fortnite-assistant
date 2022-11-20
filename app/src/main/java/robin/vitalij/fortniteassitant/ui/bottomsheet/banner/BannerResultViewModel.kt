package robin.vitalij.fortniteassitant.ui.bottomsheet.banner

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import robin.vitalij.fortniteassitant.db.entity.BannerEntity
import robin.vitalij.fortniteassitant.model.LoadingState
import robin.vitalij.fortniteassitant.repository.BannerRepository
import robin.vitalij.fortniteassitant.ui.common.BaseViewModel

class BannerResultViewModel(private val bannerRepository: BannerRepository) : BaseViewModel() {

    var bannerId: String = ""

    private val bannerUsState =
        MutableStateFlow<LoadingState<List<BannerEntity>>>(LoadingState.Loading)

    val bannerResult: StateFlow<LoadingState<List<BannerEntity>>> =
        bannerUsState

    private var job: Job? = null

    fun loadData() {
        job?.cancel()
        job = viewModelScope.launch {
            bannerRepository.getBannerId(bannerId).collect { loadingState ->
                bannerUsState.value = loadingState
            }
        }
    }

}