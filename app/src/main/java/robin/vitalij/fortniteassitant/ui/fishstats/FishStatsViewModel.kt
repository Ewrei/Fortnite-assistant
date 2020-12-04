package robin.vitalij.fortniteassitant.ui.fishstats

import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import robin.vitalij.fortniteassitant.common.extensions.getSeason
import robin.vitalij.fortniteassitant.db.entity.FishEntity
import robin.vitalij.fortniteassitant.model.battlepassreward.SeasonModel
import robin.vitalij.fortniteassitant.model.network.FishStats
import robin.vitalij.fortniteassitant.model.network.FishStatsResponse
import robin.vitalij.fortniteassitant.repository.FishStatsRepository
import robin.vitalij.fortniteassitant.repository.storage.PreferenceManager
import robin.vitalij.fortniteassitant.ui.common.BaseViewModel
import robin.vitalij.fortniteassitant.utils.view.ResourceProvider

class FishStatsViewModel(
    private val fishStatsRepository: FishStatsRepository,
    private val preferenceManager: PreferenceManager,
    private val resourceProvider: ResourceProvider
) : BaseViewModel() {

    val mutableLiveData = MutableLiveData<List<FishStats>>()
    val mutableSeasonLiveData = MutableLiveData<List<SeasonModel>>()

    private var fishStatsResponse: FishStatsResponse = FishStatsResponse()

    init {
        loadData()
    }

    fun loadData() {
        fishStatsRepository
            .loadData(preferenceManager.getPlayerId())
            .observeOn(AndroidSchedulers.mainThread())
            .let(::setupProgressShow)
            .subscribe({
                fishStatsResponse= it
                mutableLiveData.value = it.seasonStats.firstOrNull()?.fish ?: arrayListOf()
                mutableSeasonLiveData.value = it.seasonStats.getSeason(resourceProvider)
            }, error)
            .let(disposables::add)
    }

    fun changeSeason(seasonModel: SeasonModel) {
        fishStatsResponse.seasonStats.find { it.season == seasonModel.season }?.let {
            mutableLiveData.value = it.fish
        }
    }
}