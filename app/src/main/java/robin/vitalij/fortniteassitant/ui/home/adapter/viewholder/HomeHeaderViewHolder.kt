package robin.vitalij.fortniteassitant.ui.home.adapter.viewholder

import kotlinx.android.synthetic.main.item_home_header.view.*
import robin.vitalij.fortniteassitant.common.extensions.setSafeOnClickListener
import robin.vitalij.fortniteassitant.databinding.ItemHomeHeaderBinding
import robin.vitalij.fortniteassitant.ui.common.BaseViewHolder
import robin.vitalij.fortniteassitant.ui.home.adapter.viewmodel.Home
import robin.vitalij.fortniteassitant.ui.home.adapter.viewmodel.HomeHeaderViewModel

class HomeHeaderViewHolder(
    override val binding: ItemHomeHeaderBinding,
    private val openDetailsStatistics: () -> Unit,
    private val openSeason: () -> Unit
) : BaseViewHolder<Home>(binding) {

    override fun bind(item: Home) {
        if (item is HomeHeaderViewModel) {
            binding.item = item

            itemView.currentSeason.setSafeOnClickListener {
                openSeason()
            }

            itemView.detailsStatistics.setSafeOnClickListener {
                openDetailsStatistics()
            }
        }
    }
}