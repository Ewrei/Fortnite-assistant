package robin.vitalij.fortniteassitant.ui.bottomsheet.profile.adapter.viewholder.body.adapter.viewholder

import robin.vitalij.fortniteassitant.databinding.ItemBodyStatsStatsBinding
import robin.vitalij.fortniteassitant.databinding.ItemProfileBodyBinding
import robin.vitalij.fortniteassitant.ui.bottomsheet.profile.adapter.viewholder.body.adapter.viewmodel.BodyStats
import robin.vitalij.fortniteassitant.ui.bottomsheet.profile.adapter.viewholder.body.adapter.viewmodel.BodyStatsStats
import robin.vitalij.fortniteassitant.ui.common.BaseViewHolder

class BodyStatsStatsViewHolder(
    override val binding: ItemBodyStatsStatsBinding
) : BaseViewHolder<BodyStats>(binding) {

    override fun bind(item: BodyStats) {
        if (item is BodyStatsStats) {
            binding.item = item
        }
    }
}