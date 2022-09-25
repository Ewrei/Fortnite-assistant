package robin.vitalij.fortniteassitant.ui.session.statistics

import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import robin.vitalij.fortniteassitant.model.enums.BattlesType
import robin.vitalij.fortniteassitant.model.enums.GameType
import robin.vitalij.fortniteassitant.ui.common.BaseViewModel
import robin.vitalij.fortniteassitant.ui.home.adapter.viewholder.statistics.adapter.HomeBodyStatsListItem
import robin.vitalij.fortniteassitant.utils.mapper.HomeSessionRepository

class DetailsSessionStatisticsViewModel(
    private val homeSessionRepository: HomeSessionRepository
) : BaseViewModel() {

    val mutableLiveData = MutableLiveData<List<HomeBodyStatsListItem>>()

    fun loadData(battlesType: BattlesType, gameType: GameType, sessionId: Long, sessionLastId: Long) {
        homeSessionRepository
            .loadData(battlesType, gameType, sessionId, sessionLastId)
            .observeOn(AndroidSchedulers.mainThread())
            .let(::setupProgressShow)
            .subscribe({
                mutableLiveData.value = it
            }, error)
            .let(disposables::add)
    }
}