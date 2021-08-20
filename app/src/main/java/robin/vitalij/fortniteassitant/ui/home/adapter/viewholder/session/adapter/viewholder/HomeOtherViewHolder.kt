package robin.vitalij.fortniteassitant.ui.home.adapter.viewholder.session.adapter.viewholder

import robin.vitalij.fortniteassitant.databinding.ItemHomeSessionOtherBinding
import robin.vitalij.fortniteassitant.ui.common.BaseViewHolder
import robin.vitalij.fortniteassitant.ui.home.adapter.viewholder.session.adapter.viewmodel.HomeSession
import robin.vitalij.fortniteassitant.ui.home.adapter.viewholder.session.adapter.viewmodel.HomeSessionOtherViewModel

class HomeOtherViewHolder(
    override val binding: ItemHomeSessionOtherBinding,
    private val openSessions: () -> Unit
) : BaseViewHolder<HomeSession>(binding) {

    override fun bind(item: HomeSession) {
        if (item is HomeSessionOtherViewModel) {
            binding.item = item
            itemView.setOnClickListener {
                openSessions()
            }
        }
    }
}