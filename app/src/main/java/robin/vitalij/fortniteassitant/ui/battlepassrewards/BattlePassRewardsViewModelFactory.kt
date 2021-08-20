package robin.vitalij.fortniteassitant.ui.battlepassrewards

import robin.vitalij.fortniteassitant.repository.network.BattlesPassRewardRepository
import robin.vitalij.fortniteassitant.ui.common.BaseViewModelFactory
import javax.inject.Inject

class BattlePassRewardsViewModelFactory @Inject constructor(
    private val battlesPassRewardRepository: BattlesPassRewardRepository
) : BaseViewModelFactory<BattlePassRewardsViewModel>(BattlePassRewardsViewModel::class.java) {

    private var viewModel: BattlePassRewardsViewModel? = null

    override fun createViewModel(): BattlePassRewardsViewModel {
        return viewModel ?: run {
            val model =
                BattlePassRewardsViewModel(battlesPassRewardRepository)
            viewModel = model
            return model
        }
    }
}