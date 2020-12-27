package robin.vitalij.fortniteassitant.ui.top.adapter.viewholder

import robin.vitalij.fortniteassitant.databinding.ItemTopBinding
import robin.vitalij.fortniteassitant.ui.common.BaseViewHolder
import robin.vitalij.fortniteassitant.ui.top.adapter.viewmodel.Top
import robin.vitalij.fortniteassitant.ui.top.adapter.viewmodel.TopViewModel

class TopViewHolder(
    override var binding: ItemTopBinding,
    private val onClick: (accountId: String) -> Unit
) : BaseViewHolder<Top>(binding) {

    override fun bind(item: Top) {
        (item as? TopViewModel)?.let {
            binding.item = item

            itemView.setOnClickListener {
                onClick(item.playerId)
            }
        }
    }
}