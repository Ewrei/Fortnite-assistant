package robin.vitalij.fortniteassitant.ui.top

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import robin.vitalij.fortniteassitant.model.TopFullModel
import robin.vitalij.fortniteassitant.repository.network.TopRepository
import robin.vitalij.fortniteassitant.ui.common.BaseViewModel
import robin.vitalij.fortniteassitant.ui.top.adapter.TopListItem

class TopViewModel(
    private val topRepository: TopRepository
) : BaseViewModel() {

    var topType = ObservableField(TopFullModel())

    val mutableLiveData = MutableLiveData<List<TopListItem>>()

    fun loadData() {
        topRepository.getTopUsers(topType.get() ?: TopFullModel())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .let(::setupProgressShow)
            .subscribe({
                mutableLiveData.value = it
            }, error)
            .let(disposables::add)
    }
}