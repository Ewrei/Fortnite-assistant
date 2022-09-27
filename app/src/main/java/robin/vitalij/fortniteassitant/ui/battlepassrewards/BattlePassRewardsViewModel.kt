package robin.vitalij.fortniteassitant.ui.battlepassrewards

import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import robin.vitalij.fortniteassitant.model.battle_pass_reward.BattlesPassRewardsModel
import robin.vitalij.fortniteassitant.model.battle_pass_reward.SeasonModel
import robin.vitalij.fortniteassitant.model.enums.BattlePassSortedType
import robin.vitalij.fortniteassitant.repository.network.BattlesPassRewardRepository
import robin.vitalij.fortniteassitant.ui.common.BaseViewModel

class BattlePassRewardsViewModel(
    private val battlesPassRewardRepository: BattlesPassRewardRepository
) : BaseViewModel() {

    val mutableLiveData = MutableLiveData<List<BattlesPassRewardsModel>>()
    val mutableSeasonLiveData = MutableLiveData<List<SeasonModel>>()

    private var list = listOf<BattlesPassRewardsModel>()

    init {
        battlesPassRewardRepository
            .getBattlesPassReward()
            .observeOn(AndroidSchedulers.mainThread())
            .let(::setupProgressShow)
            .subscribe({
                mutableLiveData.value = it.battlesPassRewards
                mutableSeasonLiveData.value = it.seasons
                list = it.battlesPassRewards
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
                list = it.battlesPassRewards
            }, error)
            .let(disposables::add)
    }

    fun sortedBattlesPassReward(battlePassSortedType: BattlePassSortedType) {
        mutableLiveData.value =
            when (battlePassSortedType) {
                BattlePassSortedType.ALL -> {
                    list
                }
                BattlePassSortedType.FREE -> {
                    list.filter { it.isFree }
                }
                BattlePassSortedType.PAID -> {
                    list.filter { !it.isFree }
                }
            }
    }
}