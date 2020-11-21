package robin.vitalij.fortniteassitant.repository.network

import android.annotation.SuppressLint
import io.reactivex.CompletableObserver
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableMaybeObserver
import robin.vitalij.fortniteassitant.db.projection.User
import robin.vitalij.fortniteassitant.interfaces.SaveUserCallback
import robin.vitalij.fortniteassitant.model.SaveUserModel
import robin.vitalij.fortniteassitant.model.network.stats.FortniteProfileResponse
import robin.vitalij.fortniteassitant.repository.db.UserRepository
import robin.vitalij.fortniteassitant.repository.storage.PreferenceManager
import robin.vitalij.fortniteassitant.utils.mapper.SaveUserMapper
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SaveUserRepository @Inject constructor(
    private val userRepository: UserRepository,
    private val preferenceManager: PreferenceManager,
) {

    @SuppressLint("CheckResult")
    fun saveUser(
        csGoFullProfileResponse: FortniteProfileResponse,
        saveUserCallback: SaveUserCallback
    ) {
        val user = SaveUserMapper().transform(csGoFullProfileResponse)

        if (user.userEntity.all?.overall?.matches == 0 || user.userEntity.playerId == "") {
            saveUserCallback.showError(Throwable())
        } else {
            userRepository.getFullUser(user.userEntity.playerId)
                .subscribe(object : DisposableMaybeObserver<User>() {
                    override fun onSuccess(t: User) {
                        if ((t.userEntity.all?.overall?.matches != user.userEntity.all?.overall?.matches) && t.userEntity.all?.overall?.matches != 0) {
                            saveData(user, saveUserCallback)
                        } else {
                            saveUserCallback.showOrHideProgressBar(false)
                            saveUserCallback.done()
                        }
                    }

                    override fun onComplete() {
                        saveData(
                            user,
                            saveUserCallback
                        )
                    }

                    override fun onError(e: Throwable) {
                        saveUserCallback.showError(e)
                    }
                })
        }
    }

    private fun saveData(
        saveUserModel: SaveUserModel,
        saveUserCallback: SaveUserCallback
    ) {
        userRepository.saveUser(saveUserModel).subscribe(object : CompletableObserver {
            override fun onComplete() {
                preferenceManager.setPlayerId(saveUserModel.userEntity.playerId)
                saveUserCallback.showOrHideProgressBar(false)
                saveUserCallback.done()
            }

            override fun onSubscribe(d: Disposable) {
                //do nothing
            }

            override fun onError(e: Throwable) {
                saveUserCallback.showError(e)
            }
        })
    }
}