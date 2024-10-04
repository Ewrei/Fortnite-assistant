package robin.vitalij.fortniteassitant.ui.search

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import robin.vitalij.fortniteassitant.interfaces.SaveUserCallback
import robin.vitalij.fortniteassitant.model.LoadingState
import robin.vitalij.fortniteassitant.model.enums.FirebaseDynamicLinkType
import robin.vitalij.fortniteassitant.model.enums.ProfileResultType
import robin.vitalij.fortniteassitant.model.network.search.SearchSteamUser
import robin.vitalij.fortniteassitant.model.network.stats.FortniteProfileResponse
import robin.vitalij.fortniteassitant.repository.FirebaseDynamicLinkRepository
import robin.vitalij.fortniteassitant.repository.network.GetSearchUserRepository
import robin.vitalij.fortniteassitant.repository.network.SaveUserRepository
import robin.vitalij.fortniteassitant.repository.storage.PreferenceManager
import robin.vitalij.fortniteassitant.ui.common.BaseViewModel
import robin.vitalij.fortniteassitant.utils.ResourceProvider

class SearchUserViewModel(
    private val getSearchUserRepository: GetSearchUserRepository,
    private val saveUserRepository: SaveUserRepository,
    private val firebaseDynamicLinkRepository: FirebaseDynamicLinkRepository,
    val preferenceManager: PreferenceManager
) : BaseViewModel() {

    var profileResultType: ProfileResultType = ProfileResultType.FULL

    lateinit var openFirebaseDynamicLink: (
        firebaseDynamicLinkType: FirebaseDynamicLinkType,
        id: String
    ) -> Unit

    lateinit var openMainScreen: () -> Unit

    var strict: Boolean = true

    private var job: Job? = null

    private val fullFishStatsState =
        MutableStateFlow<LoadingState<List<SearchSteamUser>>>(LoadingState.Success(emptyList()))

    val seasonsResult: StateFlow<LoadingState<List<SearchSteamUser>>> =
        fullFishStatsState

    fun searchPlayer(searchName: String) {
        job?.cancel()
        job = viewModelScope.launch {
            getSearchUserRepository.getSearch(searchName, strict)
                .collect { loadingState ->
                    fullFishStatsState.value = loadingState
                }
        }
    }

    fun clearSearch() {
        fullFishStatsState.value = LoadingState.Success(emptyList())
    }

    fun saveUser(csGoFullProfileResponse: FortniteProfileResponse) {
        activityProgressBarVisibility.value = true
        saveUserRepository.saveUser(csGoFullProfileResponse, object :
            SaveUserCallback {
            override fun showOrHideProgressBar(isVisible: Boolean) {
                activityProgressBarVisibility.value = isVisible
                //do nothing
            }

            override fun showError(throwable: Throwable) {
                activityProgressBarVisibility.value = false
            }

            override fun showMessage(title: String) {
                //do nothing
            }

            override fun done() {
                activityProgressBarVisibility.value = false
                openMainScreen()
            }
        })
    }

    fun checkFirebaseDynamicLink() {
        if (firebaseDynamicLinkRepository.firebaseDynamicLinkType != null && firebaseDynamicLinkRepository.id != null) {
            openFirebaseDynamicLink(
                firebaseDynamicLinkRepository.firebaseDynamicLinkType!!,
                firebaseDynamicLinkRepository.id!!,
            )
        }
    }

    fun clearFirebaseDynamicLink() {
        firebaseDynamicLinkRepository.clear()
    }
}
