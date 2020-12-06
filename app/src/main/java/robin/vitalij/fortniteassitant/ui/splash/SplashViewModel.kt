package robin.vitalij.fortniteassitant.ui.splash

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.ads.formats.UnifiedNativeAd
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import robin.vitalij.fortniteassitant.interfaces.NativeResult
import robin.vitalij.fortniteassitant.interfaces.SaveUserCallback
import robin.vitalij.fortniteassitant.model.network.stats.FortniteProfileResponse
import robin.vitalij.fortniteassitant.repository.InterstitialAdRepository
import robin.vitalij.fortniteassitant.repository.NativeRepository
import robin.vitalij.fortniteassitant.repository.network.GetUserRepository
import robin.vitalij.fortniteassitant.repository.network.SaveUserRepository
import robin.vitalij.fortniteassitant.repository.storage.PreferenceManager
import javax.inject.Inject


class SplashViewModel @Inject constructor(
    val preferenceManager: PreferenceManager,
    private val getUserRepository: GetUserRepository,
    private val saveUserRepository: SaveUserRepository,
    private val nativeRepository: NativeRepository,
    private val interstitialAdRepository: InterstitialAdRepository
) : ViewModel() {

    private val isUserAuthorised = MutableLiveData<Boolean>()

    var isSync: Boolean = false

    val disposables = CompositeDisposable()

    init {

        if (preferenceManager.getIsEstimate()) {
            preferenceManager.setEstimate((preferenceManager.getEstimate() + 1))
        }

      //  loadNative()
        loadInterstitialAd()
    }

    private fun loadNative() {
        nativeRepository.loadNativeAds(object : NativeResult {
            override fun onSusses(data: List<UnifiedNativeAd>) {
            }
        })
    }

    private fun loadInterstitialAd() {
        interstitialAdRepository.initInterstitialAd {
            checkAccount()
        }
    }

    private fun checkAccount() {
        if (preferenceManager.getPlayerId().isNotEmpty()) {
            loadData(preferenceManager.getPlayerId())
        } else {
            isUserAuthorised.value = false
        }
    }

    private fun loadData(accountId: String) {
        getUserRepository.getUser(accountId)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                saveUser(it)
            }, {
                isUserAuthorised.value = true
                it.printStackTrace()
            })
            .let(disposables::add)
    }

    private fun saveUser(fortniteProfileResponse: FortniteProfileResponse) {
        saveUserRepository.saveUser(fortniteProfileResponse, object :
            SaveUserCallback {

            override fun showOrHideProgressBar(isVisible: Boolean) {
                //do nothing
            }

            override fun showError(throwable: Throwable) {
                isUserAuthorised.value = true
            }

            override fun showMessage(title: String) {
                //do nothing
            }

            override fun done() {
                isUserAuthorised.value = true
            }
        })
    }

    fun isUserAuthorised() = isUserAuthorised
}