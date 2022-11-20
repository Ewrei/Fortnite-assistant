package robin.vitalij.fortniteassitant.ui.battle_pass_rewards.adapter

import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.common.binding.ImageViewBinging.loadImage
import robin.vitalij.fortniteassitant.common.binding.TextViewBinding.setTypeShop
import robin.vitalij.fortniteassitant.common.extensions.getStringFormat
import robin.vitalij.fortniteassitant.databinding.ItemBattlesPassRewardsBinding
import robin.vitalij.fortniteassitant.model.battle_pass_reward.BattlesPassRewardsModel

class BattlesPassRewardsHolder(
    private val binding: ItemBattlesPassRewardsBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: BattlesPassRewardsModel) {
        binding.image.loadImage(item.reward.images.fullBackground)
        binding.type.setTypeShop(item.reward.type)

        binding.tier.text = item.reward.tier.getStringFormat()
        binding.quantity.text =
            binding.root.context.getString(R.string.quantity_format, item.reward.quantity)

        binding.paid.isVisible = !item.isFree
    }
}