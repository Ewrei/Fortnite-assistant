package robin.vitalij.fortniteassitant.ui.bottomsheet.cosmetic.adapter

import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import robin.vitalij.fortniteassitant.common.extensions.initChip
import robin.vitalij.fortniteassitant.databinding.ItemCosmeticsTagsBinding

class CosmeticsTagViewHolder(
    private val binding: ItemCosmeticsTagsBinding,
    private val layoutInflater: LayoutInflater
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: CosmeticsListItem.TagItem) {
        binding.chipGroup.initChip(item.tags, layoutInflater)
    }
}
