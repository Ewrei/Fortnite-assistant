package robin.vitalij.fortniteassitant.ui.bottomsheet.profile.adapter.viewholder.body.adapter.viewholder

import robin.vitalij.fortniteassitant.databinding.ItemBodyStatsEmptyBinding
import robin.vitalij.fortniteassitant.ui.bottomsheet.profile.adapter.viewholder.body.adapter.viewmodel.BodyStats
import robin.vitalij.fortniteassitant.ui.bottomsheet.profile.adapter.viewholder.body.adapter.viewmodel.BodyStatsEmpty
import robin.vitalij.fortniteassitant.ui.common.BaseViewHolder

class BodyStatsEmptyViewHolder(
    override val binding: ItemBodyStatsEmptyBinding
) : BaseViewHolder<BodyStats>(binding) {

    override fun bind(item: BodyStats) {
        if (item is BodyStatsEmpty) {
            binding.item = item
        }
    }
}