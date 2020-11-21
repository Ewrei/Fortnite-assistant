package robin.vitalij.fortniteassitant.ui.history

import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import robin.vitalij.fortniteassitant.repository.db.HistoryMatchRepository
import robin.vitalij.fortniteassitant.repository.storage.PreferenceManager
import robin.vitalij.fortniteassitant.ui.common.BaseViewModel
import robin.vitalij.fortniteassitant.ui.history.adapter.viewmodel.HistoryMatch

class HistoryMatchViewModel(
    historyRepository: HistoryMatchRepository,
    preferenceManager: PreferenceManager
) : BaseViewModel() {

    val mutableLiveData = MutableLiveData<List<HistoryMatch>>()

    init {
        historyRepository
            .loadData(preferenceManager.getPlayerId())
            .observeOn(AndroidSchedulers.mainThread())
            .let(::setupProgressShow)
            .subscribe({
                mutableLiveData.value = it
            }, error)
            .let(disposables::add)
    }

}