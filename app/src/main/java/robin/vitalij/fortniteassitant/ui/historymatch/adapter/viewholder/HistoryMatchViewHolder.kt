package robin.vitalij.fortniteassitant.ui.historymatch.adapter.viewholder

import kotlinx.android.synthetic.main.item_history_match.view.*
import robin.vitalij.fortniteassitant.common.extensions.DATE_FULL
import robin.vitalij.fortniteassitant.common.extensions.getDateFull
import robin.vitalij.fortniteassitant.databinding.ItemHistoryMatchBinding
import robin.vitalij.fortniteassitant.ui.common.BaseViewHolder
import robin.vitalij.fortniteassitant.ui.historymatch.adapter.viewmodel.HistoryMatch
import robin.vitalij.fortniteassitant.ui.historymatch.adapter.viewmodel.HistoryMatchViewModel

class HistoryMatchViewHolder(
    override val binding: ItemHistoryMatchBinding
) : BaseViewHolder<HistoryMatch>(binding) {

    override fun bind(item: HistoryMatch) {
        if (item is HistoryMatchViewModel) {
            binding.item = item
            itemView.dateTitle.text = item.match.date.getDateFull(DATE_FULL)
        }
    }
}