package robin.vitalij.fortniteassitant.ui.comparison.selected.manyaccount.statistics.adapter.viewholder

import robin.vitalij.fortniteassitant.databinding.ItemComparisonManyPlayersScheduleBinding
import robin.vitalij.fortniteassitant.ui.common.BaseViewHolder
import robin.vitalij.fortniteassitant.ui.comparison.selected.manyaccount.statistics.adapter.viewmodel.ComparisonManyPlayers
import robin.vitalij.fortniteassitant.ui.comparison.selected.manyaccount.statistics.adapter.viewmodel.ComparisonManyPlayersScheduleViewModel

class ComparisonManyPlayersScheduleViewHolder(override val binding: ItemComparisonManyPlayersScheduleBinding) :
    BaseViewHolder<ComparisonManyPlayers>(binding) {

    override fun bind(item: ComparisonManyPlayers) {
        if (item is ComparisonManyPlayersScheduleViewModel) {
            binding.item = item
        }
    }
}