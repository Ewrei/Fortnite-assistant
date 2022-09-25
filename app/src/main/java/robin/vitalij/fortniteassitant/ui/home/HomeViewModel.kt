package robin.vitalij.fortniteassitant.ui.home

import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import robin.vitalij.fortniteassitant.model.DetailStatisticsModel
import robin.vitalij.fortniteassitant.repository.db.HomeRepository
import robin.vitalij.fortniteassitant.repository.storage.PreferenceManager
import robin.vitalij.fortniteassitant.ui.common.BaseViewModel
import robin.vitalij.fortniteassitant.ui.home.adapter.viewmodel.Home

class HomeViewModel(
    private val homeRepository: HomeRepository,
    private val preferenceManager: PreferenceManager
) : BaseViewModel() {

    val mutableLiveData = MutableLiveData<List<Home>>()

    var detailsStatistics: ArrayList<DetailStatisticsModel> = arrayListOf()

    fun loadData() {
        homeRepository
            .loadData(preferenceManager.getPlayerId())
            .observeOn(AndroidSchedulers.mainThread())
            .let(::setupProgressShow)
            .subscribe({
                detailsStatistics.clear()
                detailsStatistics.addAll(it.details)
                mutableLiveData.value = it.homes
            }, error)
            .let(disposables::add)
    }
}