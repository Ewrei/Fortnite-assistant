package robin.vitalij.fortniteassitant.ui.top.adapter.viewholder

import robin.vitalij.fortniteassitant.databinding.ItemTopHeaderBinding
import robin.vitalij.fortniteassitant.ui.common.BaseViewHolder
import robin.vitalij.fortniteassitant.ui.top.adapter.viewmodel.Top
import robin.vitalij.fortniteassitant.ui.top.adapter.viewmodel.TopHeaderViewModel

class TopHeaderViewHolder(
    override var binding: ItemTopHeaderBinding,
    private val onTopClick: () -> Unit
) : BaseViewHolder<Top>(binding) {

    override fun bind(item: Top) {
        (item as? TopHeaderViewModel)?.let {
            binding.item = item.topFullModel
            itemView.setOnClickListener {
                onTopClick()
            }
        }
    }
}