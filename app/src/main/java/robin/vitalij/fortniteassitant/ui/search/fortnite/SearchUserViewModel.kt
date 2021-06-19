package robin.vitalij.fortniteassitant.ui.search.fortnite

import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import robin.vitalij.fortniteassitant.interfaces.SaveUserCallback
import robin.vitalij.fortniteassitant.model.enums.FirebaseDynamicLinkType
import robin.vitalij.fortniteassitant.model.network.search.SearchSteamUser
import robin.vitalij.fortniteassitant.model.network.stats.FortniteProfileResponse
import robin.vitalij.fortniteassitant.repository.FirebaseDynamicLinkRepository
import robin.vitalij.fortniteassitant.repository.network.GetSearchUserRepository
import robin.vitalij.fortniteassitant.repository.network.SaveUserRepository
import robin.vitalij.fortniteassitant.repository.storage.PreferenceManager
import robin.vitalij.fortniteassitant.ui.common.BaseViewModel

class SearchUserViewModel(
    private val getSearchUserRepository: GetSearchUserRepository,
    private val saveUserRepository: SaveUserRepository,
    private val firebaseDynamicLinkRepository: FirebaseDynamicLinkRepository,
    val preferenceManager: PreferenceManager
) : BaseViewModel() {

    lateinit var openFirebaseDynamicLink: (
        firebaseDynamicLinkType: FirebaseDynamicLinkType,
        id: String
    ) -> Unit

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
