package robin.vitalij.fortniteassitant.ui.battlepassrewards.adapter

import androidx.recyclerview.widget.RecyclerView
import robin.vitalij.fortniteassitant.databinding.ItemBattlesPassRewardsBinding
import robin.vitalij.fortniteassitant.model.battle_pass_reward.BattlesPassRewardsModel

class BattlesPassRewardsHolder(
    private val binding: ItemBattlesPassRewardsBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: BattlesPassRewardsModel) {
        binding.item = item
    }
}