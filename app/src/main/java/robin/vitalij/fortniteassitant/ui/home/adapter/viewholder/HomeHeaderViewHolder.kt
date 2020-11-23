package robin.vitalij.fortniteassitant.ui.home.adapter.viewholder

import robin.vitalij.fortniteassitant.databinding.ItemHomeHeaderBinding
import robin.vitalij.fortniteassitant.ui.common.BaseViewHolder
import robin.vitalij.fortniteassitant.ui.home.adapter.viewmodel.Home
import robin.vitalij.fortniteassitant.ui.home.adapter.viewmodel.HomeHeaderViewModel

class HomeHeaderViewHolder(
    override val binding: ItemHomeHeaderBinding
) : BaseViewHolder<Home>(binding) {

    override fun bind(item: Home) {
        if (item is HomeHeaderViewModel) {
            binding.item = item
//            itemView.friendButton.setOnClickListener {
//                openFriends()
//            }
//            itemView.achievementsButton.setOnClickListener {
//                openAchievements()
//            }
//            itemView.inventoryButton.setOnClickListener {
//                openInventoryButton()
//            }
        }
    }
}