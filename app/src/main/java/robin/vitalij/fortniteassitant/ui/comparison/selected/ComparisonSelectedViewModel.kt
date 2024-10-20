package robin.vitalij.fortniteassitant.ui.comparison.selected

import android.annotation.SuppressLint
import androidx.databinding.ObservableField
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import robin.vitalij.fortniteassitant.interfaces.SaveUserCallback
import robin.vitalij.fortniteassitant.model.LoadingState
import robin.vitalij.fortniteassitant.model.network.search.SearchSteamUserModel
import robin.vitalij.fortniteassitant.model.network.stats.FortniteProfileResponse
import robin.vitalij.fortniteassitant.repository.comparison.ComparisonListUserRepository
import robin.vitalij.fortniteassitant.repository.network.GetSearchUserRepository
import robin.vitalij.fortniteassitant.repository.network.SaveUserRepository
import robin.vitalij.fortniteassitant.repository.storage.PreferenceManager
import robin.vitalij.fortniteassitant.ui.common.BaseViewModel

class ComparisonSelectedViewModel(
    private val getSearchUserRepository: GetSearchUserRepository,
    private val comparisonListUserRepository: ComparisonListUserRepository,
    private val saveUserRepository: SaveUserRepository,
    val preferenceManager: PreferenceManager
) : BaseViewModel() {

    lateinit var openMainScreen: () -> Unit

    val mutableSizeLiveData = MutableLiveData<Int>()

    val visibility = ObservableField(true)

    var mutableLiveData = MutableLiveData<List<SearchSteamUserModel>>()

    private val fullFishStatsState =
        MutableStateFlow<LoadingState<List<SearchSteamUserModel>>>(LoadingState.Success(emptyList()))

    val seasonsResult: StateFlow<LoadingState<List<SearchSteamUserModel>>> =
        fullFishStatsState

    private lateinit var owner: LifecycleOwner

    private var job: Job? = null

    fun initOwner(owner: LifecycleOwner) {
        this.owner = owner
    }

    @SuppressLint("CheckResult")
    fun loadPlayerComparisonSize() {
        comparisonListUserRepository.getPlayerSize()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                mutableSizeLiveData.value = it
            }, {
                //do nothing
            })
    }

    fun searchPlayer(searchName: String) {
        job?.cancel()
        job = viewModelScope.launch {
            getSearchUserRepository.getSearch(searchName, true)
                .collect { loadingState ->
                    if(loadingState is LoadingState.Success) {
                        mutableLiveData.value = loadingState.data
                    }

                    fullFishStatsState.value = loadingState
                }
        }
    }

    fun saveUser(fortniteProfileResponse: FortniteProfileResponse) {
        saveUserRepository.saveUser(fortniteProfileResponse, object :
            SaveUserCallback {
            override fun showOrHideProgressBar(isVisible: Boolean) {
            }

            override fun showError(throwable: Throwable) {
                //do nothing
            }

            override fun showMessage(title: String) {
                //do nothing
            }

            override fun done() {
                openMainScreen()
            }
        })
    }
}