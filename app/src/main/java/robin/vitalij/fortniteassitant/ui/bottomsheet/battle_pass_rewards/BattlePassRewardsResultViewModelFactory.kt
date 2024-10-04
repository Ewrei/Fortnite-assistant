package robin.vitalij.fortniteassitant.ui.bottomsheet.battle_pass_rewards

import robin.vitalij.fortniteassitant.ui.common.BaseViewModelFactory
import javax.inject.Inject

class BattlePassRewardsResultViewModelFactory @Inject constructor(
) : BaseViewModelFactory<BattlePassRewardsResultViewModel>(BattlePassRewardsResultViewModel::class.java) {

    private var viewModel: BattlePassRewardsResultViewModel? = null

    override fun createViewModel(): BattlePassRewardsResultViewModel {
        return viewModel ?: run {
            val model = BattlePassRewardsResultViewModel()
            viewModel = model
            return model
        }
    }
}