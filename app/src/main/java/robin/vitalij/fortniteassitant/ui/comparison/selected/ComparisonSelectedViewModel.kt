package robin.vitalij.fortniteassitant.ui.comparison.selected

import android.annotation.SuppressLint
import androidx.databinding.ObservableField
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import robin.vitalij.fortniteassitant.interfaces.SaveUserCallback
import robin.vitalij.fortniteassitant.model.network.search.SearchSteamUser
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

    val nickname = ObservableField("")

    val visibility = ObservableField<Boolean>(true)

    var mutableLiveData = MutableLiveData<List<SearchSteamUser>>()

    private lateinit var owner: LifecycleOwner

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
        getSearchUserRepository.getSearch(searchName)
            .observeOn(AndroidSchedulers.mainThread())
            .let(::setupProgressShow)
            .subscribe({
                mutableLiveData.value = it
            }, {
                it.printStackTrace()
            })
            .let(disposables::add)
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