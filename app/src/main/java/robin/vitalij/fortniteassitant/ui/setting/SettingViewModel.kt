package robin.vitalij.fortniteassitant.ui.setting

import androidx.databinding.ObservableField
import io.reactivex.android.schedulers.AndroidSchedulers
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.db.entity.UserEntity
import robin.vitalij.fortniteassitant.interfaces.SaveUserCallback
import robin.vitalij.fortniteassitant.model.network.stats.FortniteProfileResponse
import robin.vitalij.fortniteassitant.repository.db.UserRepository
import robin.vitalij.fortniteassitant.repository.network.GetUserRepository
import robin.vitalij.fortniteassitant.repository.network.SaveUserRepository
import robin.vitalij.fortniteassitant.repository.storage.PreferenceManager
import robin.vitalij.fortniteassitant.ui.common.BaseViewModel
import robin.vitalij.fortniteassitant.utils.view.ResourceProvider

class SettingViewModel(
    userRepository: UserRepository,
    private val saveUserRepository: SaveUserRepository,
    private val getUserRepository: GetUserRepository,
    private val preferenceManager: PreferenceManager,
    private val resourceProvider: ResourceProvider
) : BaseViewModel() {

    val user = ObservableField<UserEntity>()

    init {
        userRepository.getUser(preferenceManager.getPlayerId())
            .observeOn(AndroidSchedulers.mainThread())
            .let(::setupProgressShow)
            .subscribe({
                user.set(it)
            }, error)
            .let(disposables::add)
    }

    fun update() {
        activityProgressBarVisibility.value = true
        textActivityVisibility.set(resourceProvider.getString(R.string.user_update))
        getUserRepository
            .getUser(preferenceManager.getPlayerId())
            .observeOn(AndroidSchedulers.mainThread())
            .let(::setupActivityProgressShow)
            .subscribe({
                saveUser(it)
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

    fun checkSubscription() {
      //  billingRepository.startConnection(true)
    }
}