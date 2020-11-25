package robin.vitalij.fortniteassitant.ui.setting

import androidx.databinding.ObservableField
import io.reactivex.android.schedulers.AndroidSchedulers
import robin.vitalij.fortniteassitant.db.entity.UserEntity
import robin.vitalij.fortniteassitant.repository.BillingRepository
import robin.vitalij.fortniteassitant.repository.db.UserRepository
import robin.vitalij.fortniteassitant.repository.storage.PreferenceManager
import robin.vitalij.fortniteassitant.ui.common.BaseViewModel

class SettingViewModel(
    private val billingRepository: BillingRepository,
    userRepository: UserRepository,
    preferenceManager: PreferenceManager
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

    fun checkSubscription() {
        billingRepository.startConnection(true)
    }
}