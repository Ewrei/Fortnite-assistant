package robin.vitalij.fortniteassitant.ui.top.adapter

import androidx.recyclerview.widget.RecyclerView
import robin.vitalij.fortniteassitant.common.extensions.setValueText
import robin.vitalij.fortniteassitant.databinding.ItemTopBinding
import robin.vitalij.fortniteassitant.ui.top.adapter.TopListItem

class TopViewHolder(
    private val binding: ItemTopBinding,
    private val onClick: (accountId: String) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: TopListItem.PlayerItem) {
        binding.position.setValueText(item.position)
        binding.nickName.text = item.userName
        binding.value.text = item.value

        itemView.setOnClickListener {
            onClick(item.playerId)
        }
    }
}