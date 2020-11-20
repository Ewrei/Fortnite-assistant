package robin.vitalij.fortniteassitant.ui.bottomsheet.profile.adapter.viewholder.body.adapter.viewholder

import robin.vitalij.fortniteassitant.databinding.ItemBodyStatsLtmBinding
import robin.vitalij.fortniteassitant.databinding.ItemProfileBodyBinding
import robin.vitalij.fortniteassitant.ui.bottomsheet.profile.adapter.viewholder.body.adapter.viewmodel.BodyStats
import robin.vitalij.fortniteassitant.ui.bottomsheet.profile.adapter.viewholder.body.adapter.viewmodel.BodyStatsLtm
import robin.vitalij.fortniteassitant.ui.common.BaseViewHolder

class BodyStatsLtmViewHolder(
    override val binding: ItemBodyStatsLtmBinding
) : BaseViewHolder<BodyStats>(binding) {

    override fun bind(item: BodyStats) {
        if (item is BodyStatsLtm) {
            binding.item = item
        }
    }
}