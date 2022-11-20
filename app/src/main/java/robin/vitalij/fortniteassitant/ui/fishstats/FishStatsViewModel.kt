package robin.vitalij.fortniteassitant.ui.fishstats

import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.common.extensions.getSeason
import robin.vitalij.fortniteassitant.model.EmptyTextModel
import robin.vitalij.fortniteassitant.model.battle_pass_reward.SeasonModel
import robin.vitalij.fortniteassitant.model.network.FishStatsModel
import robin.vitalij.fortniteassitant.model.network.FishStatsResponse
import robin.vitalij.fortniteassitant.repository.FishStatsRepository
import robin.vitalij.fortniteassitant.repository.storage.PreferenceManager
import robin.vitalij.fortniteassitant.ui.common.BaseViewModel
import robin.vitalij.fortniteassitant.utils.ResourceProvider

class FishStatsViewModel(
    private val fishStatsRepository: FishStatsRepository,
    private val preferenceManager: PreferenceManager,
    private val resourceProvider: ResourceProvider
) : BaseViewModel() {

    val mutableLiveData = MutableLiveData<List<FishStatsModel>>()
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
                mutableLiveData.value = it.seasonStats.firstOrNull()?.fish ?: emptyList()
                mutableSeasonLiveData.value = it.seasonStats.getSeason(resourceProvider)

                emptyText(
                    EmptyTextModel(
                        it.seasonStats.isEmpty(),
                        resourceProvider.getString(
                            R.string.empty_fish
                        )
                    )
                )
            }, error)
            .let(disposables::add)
    }

    fun changeSeason(seasonModel: SeasonModel) {
        fishStatsResponse.seasonStats.find { it.season == seasonModel.season }?.let {
            mutableLiveData.value = it.fish
        }
    }
}