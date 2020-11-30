package robin.vitalij.fortniteassitant.ui.search.fortnite

import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import robin.vitalij.fortniteassitant.interfaces.SaveUserCallback
import robin.vitalij.fortniteassitant.model.network.search.SearchSteamUser
import robin.vitalij.fortniteassitant.model.network.stats.FortniteProfileResponse
import robin.vitalij.fortniteassitant.repository.network.GetSearchUserRepository
import robin.vitalij.fortniteassitant.repository.network.SaveUserRepository
import robin.vitalij.fortniteassitant.repository.storage.PreferenceManager
import robin.vitalij.fortniteassitant.ui.common.BaseViewModel

class SearchUserViewModel(
    private val getSearchUserRepository: GetSearchUserRepository,
    private val saveUserRepository: SaveUserRepository,
    val preferenceManager: PreferenceManager
) : BaseViewModel() {

    var mutableLiveData = MutableLiveData<List<SearchSteamUser>>()

    lateinit var openMainScreen: () -> Unit

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

    fun saveUser(csGoFullProfileResponse: FortniteProfileResponse) {
        activityProgressBarVisibility.value = true
        saveUserRepository.saveUser(csGoFullProfileResponse, object :
            SaveUserCallback {
            override fun showOrHideProgressBar(isVisible: Boolean) {
                //do nothing
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
