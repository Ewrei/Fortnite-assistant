package robin.vitalij.fortniteassitant.ui.top.adapter

import androidx.recyclerview.widget.RecyclerView
import robin.vitalij.fortniteassitant.databinding.ItemTopBinding

class TopHolder(
    var binding: ItemTopBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: TopUserModel) {
        binding.item = item
    }
}