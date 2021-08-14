package robin.vitalij.fortniteassitant.ui.history

import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.model.EmptyTextModel
import robin.vitalij.fortniteassitant.model.HistoryUserModel
import robin.vitalij.fortniteassitant.repository.db.HistoryRepository
import robin.vitalij.fortniteassitant.repository.storage.PreferenceManager
import robin.vitalij.fortniteassitant.ui.common.BaseViewModel
import robin.vitalij.fortniteassitant.utils.ResourceProvider

class HistoryViewModel(
    historyRepository: HistoryRepository,
    preferenceManager: PreferenceManager,
    resourceProvider: ResourceProvider
) : BaseViewModel() {

    val mutableLiveData = MutableLiveData<List<HistoryUserModel>>()

    init {
        historyRepository
            .loadData(preferenceManager.getPlayerId())
            .observeOn(AndroidSchedulers.mainThread())
            .let(::setupProgressShow)
            .subscribe({
                mutableLiveData.value = it

                emptyText(
                    EmptyTextModel(
                        it.isEmpty(),
                        resourceProvider.getString(
                            R.string.empty_session
                        )
                    )
                )
            }, error)
            .let(disposables::add)
    }
}