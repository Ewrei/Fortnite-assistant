package robin.vitalij.fortniteassitant.ui.fishing.adapter

import androidx.recyclerview.widget.RecyclerView
import robin.vitalij.fortniteassitant.databinding.ItemFishBinding
import robin.vitalij.fortniteassitant.db.entity.FishEntity

class FishHolder(
    private val binding: ItemFishBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: FishEntity) {
        binding.item = item
    }
}