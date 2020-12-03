package robin.vitalij.fortniteassitant.ui.battlepassrewards

import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import robin.vitalij.fortniteassitant.model.battlepassreward.BattlesPassRewardsModel
import robin.vitalij.fortniteassitant.model.battlepassreward.SeasonModel
import robin.vitalij.fortniteassitant.repository.network.BattlesPassRewardRepository
import robin.vitalij.fortniteassitant.ui.common.BaseViewModel

class BattlePassRewardsViewModel(
    private val battlesPassRewardRepository: BattlesPassRewardRepository
) : BaseViewModel() {

    val mutableLiveData = MutableLiveData<List<BattlesPassRewardsModel>>()
    val mutableSeasonLiveData = MutableLiveData<List<SeasonModel>>()

    init {
        battlesPassRewardRepository
            .getBattlesPassReward()
            .observeOn(AndroidSchedulers.mainThread())
            .let(::setupProgressShow)
            .subscribe({
                mutableLiveData.value = it.battlesPassRewards
                mutableSeasonLiveData.value = it.seasons
            }, error)
            .let(disposables::add)
    }

    fun loadData(season: String) {
        battlesPassRewardRepository
            .getBattlesPassReward(season)
            .observeOn(AndroidSchedulers.mainThread())
            .let(::setupProgressShow)
            .subscribe({
                mutableLiveData.value = it.battlesPassRewards
            }, error)
            .let(disposables::add)
    }
}