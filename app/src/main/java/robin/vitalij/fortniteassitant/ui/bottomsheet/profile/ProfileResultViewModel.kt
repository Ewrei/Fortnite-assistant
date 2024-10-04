package robin.vitalij.fortniteassitant.ui.bottomsheet.profile

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import robin.vitalij.fortniteassitant.model.LoadingState
import robin.vitalij.fortniteassitant.model.network.stats.FortniteProfileResponse
import robin.vitalij.fortniteassitant.repository.comparison.ComparisonListUserRepository
import robin.vitalij.fortniteassitant.repository.comparison.ComparisonRepository
import robin.vitalij.fortniteassitant.repository.network.GetUserRepository
import robin.vitalij.fortniteassitant.ui.bottomsheet.profile.adapter.ProfileListItem
import robin.vitalij.fortniteassitant.utils.ResourceProvider
import robin.vitalij.fortniteassitant.utils.mapper.PlayerUserMapper
import robin.vitalij.fortniteassitant.utils.mapper.ProfileResultMapper


class ProfileResultViewModel(
    private val getUserRepository: GetUserRepository,
    private val comparisonListUserRepository: ComparisonListUserRepository,
    private val comparisonRepository: ComparisonRepository,
    private val resourceProvider: ResourceProvider
) : ViewModel() {

    val mutableLiveData = MutableLiveData<List<ProfileListItem>>()

    var playerModel: ObservableField<FortniteProfileResponse> = ObservableField()

    lateinit var openCompare: () -> Unit

    private val profileState =
        MutableStateFlow<LoadingState<List<ProfileListItem>>>(LoadingState.Loading)

    val profileResult: StateFlow<LoadingState<List<ProfileListItem>>> = profileState

    private var job: Job? = null

    fun loadData(accountId: String, avatarImage: String) {
        job?.cancel()
        job = viewModelScope.launch {
            getUserRepository.getUserNewVersion(accountId).collect { loadingState ->
                when (loadingState) {
                    is LoadingState.Success -> {
                        loadingState.data.stats.playerStatsData.image = avatarImage
                        loadingState.data.avatar = avatarImage
                        playerModel.set(loadingState.data)
                        profileState.value = LoadingState.Success(
                            ProfileResultMapper(resourceProvider).transform(playerModel.get()!!)
                        )
                    }

                    is LoadingState.Error -> {
                        profileState.value = loadingState
                    }

                    else -> {
                        profileState.value = LoadingState.Loading
                    }
                }
            }
        }
    }

    fun addedUserMode() {
        playerModel.get()?.let {
            comparisonListUserRepository.addUserModel(PlayerUserMapper().transform(it))
        }
    }

    fun compareWithYourself() {
        playerModel.get()?.let {
            comparisonRepository.loadOneLocalData(PlayerUserMapper().transform(it))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { openCompare() }, {
                        // do nothing
                    })
        }
    }
}