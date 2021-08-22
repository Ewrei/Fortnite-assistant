package robin.vitalij.fortniteassitant.ui.cosmetics.catalog.adapter

import androidx.recyclerview.widget.RecyclerView
import robin.vitalij.fortniteassitant.databinding.ItemCosmeticsBinding
import robin.vitalij.fortniteassitant.db.entity.CosmeticsEntity

class CosmeticsHolder(
    private val binding: ItemCosmeticsBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: CosmeticsEntity) {
        binding.item = item
    }
}