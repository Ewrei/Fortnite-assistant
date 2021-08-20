package robin.vitalij.fortniteassitant.ui.comparison.selected.manyaccount.statistics.adapter.viewholder

import robin.vitalij.fortniteassitant.databinding.ItemComparisonManyPlayersHeaderBinding
import robin.vitalij.fortniteassitant.ui.common.BaseViewHolder
import robin.vitalij.fortniteassitant.ui.comparison.selected.manyaccount.statistics.adapter.viewmodel.ComparisonManyPlayers
import robin.vitalij.fortniteassitant.ui.comparison.selected.manyaccount.statistics.adapter.viewmodel.ComparisonManyPlayersHeaderViewModel

class ComparisonManyPlayersHeaderViewHolder(override val binding: ItemComparisonManyPlayersHeaderBinding) :
    BaseViewHolder<ComparisonManyPlayers>(binding) {

    override fun bind(item: ComparisonManyPlayers) {
        if (item is ComparisonManyPlayersHeaderViewModel) {
            binding.item = item
        }
    }
}