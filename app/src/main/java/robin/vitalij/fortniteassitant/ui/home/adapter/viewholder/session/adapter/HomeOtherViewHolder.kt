package robin.vitalij.fortniteassitant.ui.home.adapter.viewholder.session.adapter

import androidx.recyclerview.widget.RecyclerView
import robin.vitalij.fortniteassitant.databinding.ItemHomeSessionOtherBinding

class HomeOtherViewHolder(
    private val binding: ItemHomeSessionOtherBinding,
    private val openSessions: () -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: HomeSessionListItem.OtherItem) {
        binding.item = item
        binding.root.setOnClickListener {
            openSessions()
        }
    }
}