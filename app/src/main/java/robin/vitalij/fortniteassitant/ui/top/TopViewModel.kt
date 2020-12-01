package robin.vitalij.fortniteassitant.ui.top

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import robin.vitalij.fortniteassitant.model.TopFullModel
import robin.vitalij.fortniteassitant.model.enums.BattlesType
import robin.vitalij.fortniteassitant.model.enums.GameType
import robin.vitalij.fortniteassitant.model.enums.TopType
import robin.vitalij.fortniteassitant.repository.network.TopRepository
import robin.vitalij.fortniteassitant.ui.common.BaseViewModel
import robin.vitalij.fortniteassitant.ui.top.adapter.TopUserModel

class TopViewModel(
    private val topRepository: TopRepository
) : BaseViewModel() {

    var topType = ObservableField(TopType.KD)

    var topFullModel = ObservableField(TopFullModel(TopType.MATCHES, GameType.ALL, BattlesType.SOLO))

    val mutableLiveData = MutableLiveData<List<TopUserModel>>()

    init {
        loadData()
    }

    fun loadData() {
        topRepository.getTopUsers(topType.get() ?: TopType.KD)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .let(::setupProgressShow)
            .subscribe({
                mutableLiveData.value = it
            }, error)
            .let(disposables::add)
    }
}