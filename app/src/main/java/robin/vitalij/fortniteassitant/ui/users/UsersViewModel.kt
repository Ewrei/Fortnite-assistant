package robin.vitalij.fortniteassitant.ui.users

import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.interfaces.SaveUserCallback
import robin.vitalij.fortniteassitant.model.network.stats.FortniteProfileResponse
import robin.vitalij.fortniteassitant.repository.db.UsersRepository
import robin.vitalij.fortniteassitant.repository.network.GetUserRepository
import robin.vitalij.fortniteassitant.repository.network.SaveUserRepository
import robin.vitalij.fortniteassitant.repository.storage.PreferenceManager
import robin.vitalij.fortniteassitant.ui.common.BaseViewModel
import robin.vitalij.fortniteassitant.model.UserModel
import robin.vitalij.fortniteassitant.utils.ResourceProvider

class UsersViewModel(
    private val usersRepository: UsersRepository,
    private val saveUserRepository: SaveUserRepository,
    private val getUserRepository: GetUserRepository,
    private val resourceProvider: ResourceProvider,
    val preferenceManager: PreferenceManager
) : BaseViewModel() {

    val mutableLiveData = MutableLiveData<List<UserModel>>()

    init {
        loadData()
    }

    fun update(playerId: String) {
        activityProgressBarVisibility.value = true
        textActivityVisibility.set(resourceProvider.getString(R.string.user_update))
        getUserRepository
            .getUser(playerId)
            .observeOn(AndroidSchedulers.mainThread())
            .let(::setupActivityProgressShow)
            .subscribe({
                saveUser(it)
            }, error)
            .let(disposables::add)
    }

    fun switch(playerId: String) {
        preferenceManager.setPlayerId(playerId)
    }

    private fun loadData() {
        usersRepository
            .loadData(preferenceManager.getPlayerId())
            .observeOn(AndroidSchedulers.mainThread())
            .let(::setupProgressShow)
            .subscribe({
                mutableLiveData.value = it
            }, error)
            .let(disposables::add)
    }

    private fun saveUser(fortniteProfileResponse: FortniteProfileResponse) {
        saveUserRepository.saveUser(
            fortniteProfileResponse,
            object : SaveUserCallback {
                override fun showOrHideProgressBar(isVisible: Boolean) {
                    activityProgressBarVisibility.value = isVisible
                }

                override fun showError(throwable: Throwable) {
                    activityProgressBarVisibility.value = false
                }

                override fun showMessage(title: String) {
                    textActivityVisibility.set(title)
                }

                override fun done() {
                    activityProgressBarVisibility.value = false
                }
            })
    }
}