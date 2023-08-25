package robin.vitalij.fortniteassitant.ui.season.statistics

import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import robin.vitalij.fortniteassitant.model.enums.BattlesType
import robin.vitalij.fortniteassitant.model.enums.GameType
import robin.vitalij.fortniteassitant.repository.network.GetSeasonStatisticsRepository
import robin.vitalij.fortniteassitant.ui.common.BaseViewModel
import robin.vitalij.fortniteassitant.ui.home.adapter.viewholder.statistics.adapter.HomeBodyStatsListItem

class DetailsSeasonStatisticsViewModel(
    private val getSeasonStatisticsRepository: GetSeasonStatisticsRepository
) : BaseViewModel() {

    val mutableLiveData = MutableLiveData<List<HomeBodyStatsListItem>>()

    fun loadData(battlesType: BattlesType, gameType: GameType) {
        getSeasonStatisticsRepository
            .loadData(battlesType, gameType)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                mutableLiveData.value = it
            }, error)
            .let(disposables::add)
    }
}