package robin.vitalij.fortniteassitant.ui.history.adapter.viewholder

import robin.vitalij.fortniteassitant.databinding.ItemHistoryMatchDateBinding
import robin.vitalij.fortniteassitant.ui.common.BaseViewHolder
import robin.vitalij.fortniteassitant.ui.history.adapter.viewmodel.HistoryMatch
import robin.vitalij.fortniteassitant.ui.history.adapter.viewmodel.HistoryMatchDateViewModel

class HistoryMatchDateViewHolder(
    override val binding: ItemHistoryMatchDateBinding
) : BaseViewHolder<HistoryMatch>(binding) {

    override fun bind(item: HistoryMatch) {
        if (item is HistoryMatchDateViewModel) {
            binding.item = item
        }
    }
}