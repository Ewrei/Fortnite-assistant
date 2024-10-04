package robin.vitalij.fortniteassitant.model.battle_pass_reward

import robin.vitalij.fortniteassitant.model.network.Reward

data class BattlesPassRewardsModel(
    val reward: Reward,
    val isFree: Boolean,
    val id: Long
)