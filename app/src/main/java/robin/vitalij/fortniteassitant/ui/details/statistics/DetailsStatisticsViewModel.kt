package robin.vitalij.fortniteassitant.ui.details.statistics

import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import robin.vitalij.fortniteassitant.model.enums.BattlesType
import robin.vitalij.fortniteassitant.model.enums.GameType
import robin.vitalij.fortniteassitant.repository.db.DetailsStatisticsRepository
import robin.vitalij.fortniteassitant.repository.storage.PreferenceManager
import robin.vitalij.fortniteassitant.ui.common.BaseViewModel
import robin.vitalij.fortniteassitant.ui.home.adapter.viewholder.statistics.adapter.HomeBodyStatsListItem

class DetailsStatisticsViewModel(
    private val detailsStatisticsRepository: DetailsStatisticsRepository,
    private val preferenceManager: PreferenceManager
) : BaseViewModel() {

    val mutableLiveData = MutableLiveData<List<HomeBodyStatsListItem>>()

    fun loadData(battlesType: BattlesType, gameType: GameType) {
        detailsStatisticsRepository
            .loadData(preferenceManager.getPlayerId(), battlesType, gameType)
            .observeOn(AndroidSchedulers.mainThread())
            .let(::setupProgressShow)
            .subscribe({
                mutableLiveData.value = it
            }, error)
            .let(disposables::add)
    }
}