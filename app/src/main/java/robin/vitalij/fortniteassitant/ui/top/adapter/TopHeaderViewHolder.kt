package robin.vitalij.fortniteassitant.ui.top.adapter

import androidx.recyclerview.widget.RecyclerView
import robin.vitalij.fortniteassitant.common.extensions.setBattlesType
import robin.vitalij.fortniteassitant.common.extensions.setGameType
import robin.vitalij.fortniteassitant.common.extensions.setTopType
import robin.vitalij.fortniteassitant.databinding.ItemTopHeaderBinding

class TopHeaderViewHolder(
    private val binding: ItemTopHeaderBinding,
    private val onTopClick: () -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: TopListItem.HeaderItem) {
        binding.topType.setTopType(item.topFullModel.topType)
        binding.topValue.setTopType(item.topFullModel.topType)
        binding.gameType.setGameType(item.topFullModel.gameType)
        binding.battlesType.setBattlesType(item.topFullModel.battlesType)

        itemView.setOnClickListener {
            onTopClick()
        }
    }
}