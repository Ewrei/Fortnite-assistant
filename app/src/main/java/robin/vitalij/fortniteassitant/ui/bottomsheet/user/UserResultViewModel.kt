package robin.vitalij.fortniteassitant.ui.bottomsheet.user

import android.annotation.SuppressLint
import androidx.databinding.ObservableBoolean
import io.reactivex.CompletableObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import robin.vitalij.fortniteassitant.repository.comparison.ComparisonRepository
import robin.vitalij.fortniteassitant.repository.db.UserRepository
import robin.vitalij.fortniteassitant.repository.storage.PreferenceManager
import robin.vitalij.fortniteassitant.ui.common.BaseViewModel

class UserResultViewModel(
    private val preferenceManager: PreferenceManager,
    private val comparisonRepository: ComparisonRepository,
    private val userRepository: UserRepository
) : BaseViewModel() {

    lateinit var openCompare: () -> Unit

    lateinit var showRemoveUserToast: () -> Unit

    var isDifferentUser = ObservableBoolean(false)

    var currentId: String = ""

    fun setPlayerId(playerId: String) {
        currentId = playerId
        isDifferentUser.set(preferenceManager.getPlayerId() != playerId)
    }

    fun deleteProfile(playerId: String) {
        userRepository.deleteProfile(playerId)
            .subscribe(object : CompletableObserver {
            override fun onComplete() {
                showRemoveUserToast()
            }

            override fun onSubscribe(d: Disposable) {
            }

            override fun onError(e: Throwable) {
               //do nothing
            }
        })
    }

    @SuppressLint("CheckResult")
    fun compareWithYourself() {
        userRepository.loadData(currentId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                comparisonRepository.loadOneLocalData(it)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()).subscribe {
                        openCompare()
                    }
            }
    }
}