package robin.vitalij.fortniteassitant.ui.charts

import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import robin.vitalij.fortniteassitant.model.ChartsModel
import robin.vitalij.fortniteassitant.model.enums.ChartsType
import robin.vitalij.fortniteassitant.repository.db.ChartsRepository
import robin.vitalij.fortniteassitant.repository.storage.PreferenceManager
import robin.vitalij.fortniteassitant.ui.common.BaseViewModel
import java.util.*

class ChartsViewModel(
    private val chartsRepository: ChartsRepository,
    private val preferenceManager: PreferenceManager
) : BaseViewModel() {

    val mutableLiveData = MutableLiveData<ChartsModel>()

    fun loadData(chartsType: ChartsType) {
        chartsRepository
            .loadData(preferenceManager.getPlayerId(), chartsType)
            .observeOn(AndroidSchedulers.mainThread())
            .let(::setupProgressShow)
            .subscribe({
                mutableLiveData.value = it
                checkSubscriptionAccess()
            }, error)
            .let(disposables::add)
    }

    fun checkSubscriptionAccess() {
        subscriptionAccessVisibility.value =
            !preferenceManager.getIsSubscription() && !(preferenceManager.getSubscriptionAccess() > Date().time)
    }
}