package robin.vitalij.fortniteassitant.ui.home

import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import robin.vitalij.fortniteassitant.model.DetailStatisticsModel
import robin.vitalij.fortniteassitant.repository.db.HomeRepository
import robin.vitalij.fortniteassitant.repository.storage.PreferenceManager
import robin.vitalij.fortniteassitant.ui.common.BaseViewModel
import robin.vitalij.fortniteassitant.ui.home.adapter.viewmodel.Home

class HomeViewModel(
    homeRepository: HomeRepository,
    preferenceManager: PreferenceManager
) : BaseViewModel() {

    val mutableLiveData = MutableLiveData<List<Home>>()

    var detailsStatistics: ArrayList<DetailStatisticsModel> = arrayListOf()

    init {
        homeRepository
            .loadData(preferenceManager.getPlayerId())
            .observeOn(AndroidSchedulers.mainThread())
            .let(::setupProgressShow)
            .subscribe({
                mutableLiveData.value = it.homes
                detailsStatistics.clear()
                detailsStatistics.addAll(it.details)
            }, error)
            .let(disposables::add)
    }
}