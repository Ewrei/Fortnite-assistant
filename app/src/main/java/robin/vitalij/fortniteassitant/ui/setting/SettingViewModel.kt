package robin.vitalij.fortniteassitant.ui.setting

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import robin.vitalij.fortniteassitant.db.entity.UserEntity
import robin.vitalij.fortniteassitant.interfaces.SaveUserCallback
import robin.vitalij.fortniteassitant.model.LoadingState
import robin.vitalij.fortniteassitant.model.network.stats.FortniteProfileResponse
import robin.vitalij.fortniteassitant.repository.db.UserRepository
import robin.vitalij.fortniteassitant.repository.network.GetUserRepository
import robin.vitalij.fortniteassitant.repository.network.SaveUserRepository
import robin.vitalij.fortniteassitant.repository.storage.PreferenceManager
import robin.vitalij.fortniteassitant.utils.ResourceProvider

class SettingViewModel(
    private val userRepository: UserRepository,
    private val saveUserRepository: SaveUserRepository,
    private val getUserRepository: GetUserRepository,
    private val preferenceManager: PreferenceManager,
    private val resourceProvider: ResourceProvider
) : ViewModel() {

    lateinit var openDialogError: (titleError: String) -> Unit

    val user = ObservableField<UserEntity>()

    private val userState =
        MutableStateFlow<LoadingState<UserEntity>>(LoadingState.Loading)

    val userResult: StateFlow<LoadingState<UserEntity>> = userState

    private var job: Job? = null

    fun loadData() {
        job?.cancel()
        job = viewModelScope.launch {
            userRepository.getUser(preferenceManager.getPlayerId()).collect { loadingState ->
                userState.value = loadingState
            }
        }
    }

    fun update() {
       // textActivityVisibility.set(resourceProvider.getString(R.string.user_update))
      //  activityProgressBarVisibility.value = true

        getUserRepository
            .getUser(preferenceManager.getPlayerId())
            .observeOn(AndroidSchedulers.mainThread())
           // .let(::setupActivityProgressShow)
            .subscribe({
                saveUser(it)
            }, {
                openDialogError(it.message ?: it.toString())
            })
           .let(CompositeDisposable()::add)
    }

    fun getPlayerId() = preferenceManager.getPlayerId()

    private fun saveUser(fortniteProfileResponse: FortniteProfileResponse) {
        saveUserRepository.saveUser(
            fortniteProfileResponse,
            object : SaveUserCallback {
                override fun showOrHideProgressBar(isVisible: Boolean) {
                //    activityProgressBarVisibility.value = isVisible
                }

                override fun showError(throwable: Throwable) {
                //    activityProgressBarVisibility.value = false
                }

                override fun showMessage(title: String) {
                  //  textActivityVisibility.set(title)
                }

                override fun done() {
                 //   activityProgressBarVisibility.value = false
                }
            })
    }

    fun checkSubscription() {
        //  billingRepository.startConnection(true)
    }
}