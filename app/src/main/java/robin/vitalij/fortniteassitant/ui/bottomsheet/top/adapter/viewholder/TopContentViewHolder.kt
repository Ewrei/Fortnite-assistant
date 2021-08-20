package robin.vitalij.fortniteassitant.ui.bottomsheet.top.adapter.viewholder

import robin.vitalij.fortniteassitant.databinding.ItemTopResultContentBinding
import robin.vitalij.fortniteassitant.model.enums.TopType
import robin.vitalij.fortniteassitant.ui.bottomsheet.top.adapter.viewmodel.TopContentModel
import robin.vitalij.fortniteassitant.ui.bottomsheet.top.adapter.viewmodel.TopResult
import robin.vitalij.fortniteassitant.ui.common.BaseViewHolder

class TopContentViewHolder(
    override val binding: ItemTopResultContentBinding,
    private val onClick: (topType: TopType) -> Unit
) : BaseViewHolder<TopResult>(binding) {

    override fun bind(item: TopResult) {
        (item as? TopContentModel)?.let {
            binding.item = item
            itemView.setOnClickListener {
                onClick(item.topType)
            }
        }
    }
}