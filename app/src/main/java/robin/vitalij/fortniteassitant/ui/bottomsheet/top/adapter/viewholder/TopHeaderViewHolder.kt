package robin.vitalij.fortniteassitant.ui.bottomsheet.top.adapter.viewholder

import robin.vitalij.fortniteassitant.databinding.ItemTopResultHeaderBinding
import robin.vitalij.fortniteassitant.ui.bottomsheet.top.adapter.viewmodel.TopHeaderModel
import robin.vitalij.fortniteassitant.ui.bottomsheet.top.adapter.viewmodel.TopResult
import robin.vitalij.fortniteassitant.ui.common.BaseViewHolder

class TopHeaderViewHolder(override val binding: ItemTopResultHeaderBinding) :
    BaseViewHolder<TopResult>(binding) {

    override fun bind(item: TopResult) {
        (item as? TopHeaderModel)?.let {
            binding.item = item
        }
    }
}