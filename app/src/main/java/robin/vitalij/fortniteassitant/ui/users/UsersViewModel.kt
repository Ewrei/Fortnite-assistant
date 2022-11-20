package robin.vitalij.fortniteassitant.ui.users

import androidx.lifecycle.viewModelScope
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.interfaces.SaveUserCallback
import robin.vitalij.fortniteassitant.model.LoadingState
import robin.vitalij.fortniteassitant.model.UserModel
import robin.vitalij.fortniteassitant.model.network.stats.FortniteProfileResponse
import robin.vitalij.fortniteassitant.repository.db.UsersRepository
import robin.vitalij.fortniteassitant.repository.network.GetUserRepository
import robin.vitalij.fortniteassitant.repository.network.SaveUserRepository
import robin.vitalij.fortniteassitant.repository.storage.PreferenceManager
import robin.vitalij.fortniteassitant.ui.common.BaseViewModel
import robin.vitalij.fortniteassitant.utils.ResourceProvider

class UsersViewModel(
    private val usersRepository: UsersRepository,
    private val saveUserRepository: SaveUserRepository,
    private val getUserRepository: GetUserRepository,
    private val resourceProvider: ResourceProvider,
    val preferenceManager: PreferenceManager
) : BaseViewModel() {

    private val usersState = MutableSharedFlow<LoadingState<List<UserModel>>>(1)

    val usersResult: SharedFlow<LoadingState<List<UserModel>>> = usersState

    private var job: Job? = null

    fun loadData() {
        job?.cancel()
        job = viewModelScope.launch {
            usersRepository.getUsers(preferenceManager.getPlayerId()).collect { loadingState ->
                usersState.tryEmit(loadingState)
            }
        }
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