package robin.vitalij.fortniteassitant.ui.bottomsheet.profile.adapter.viewholder.body.adapter.viewholder

import robin.vitalij.fortniteassitant.databinding.ItemBodyStatsOverallBinding
import robin.vitalij.fortniteassitant.databinding.ItemProfileBodyBinding
import robin.vitalij.fortniteassitant.ui.bottomsheet.profile.adapter.viewholder.body.adapter.viewmodel.BodyStats
import robin.vitalij.fortniteassitant.ui.bottomsheet.profile.adapter.viewholder.body.adapter.viewmodel.BodyStatsOverall
import robin.vitalij.fortniteassitant.ui.common.BaseViewHolder

class BodyStatsOverallViewHolder(
    override val binding: ItemBodyStatsOverallBinding
) : BaseViewHolder<BodyStats>(binding) {

    override fun bind(item: BodyStats) {
        if (item is BodyStatsOverall) {
            binding.item = item
        }
    }
}