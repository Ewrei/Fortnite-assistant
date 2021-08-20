package robin.vitalij.fortniteassitant.ui.bottomsheet.cosmetic.adapter.viewholder

import android.view.LayoutInflater
import kotlinx.android.synthetic.main.item_cosmetics_tags.view.*
import robin.vitalij.fortniteassitant.common.extensions.initChip
import robin.vitalij.fortniteassitant.databinding.ItemCosmeticsTagsBinding
import robin.vitalij.fortniteassitant.ui.bottomsheet.cosmetic.adapter.viewmodel.Cosmetics
import robin.vitalij.fortniteassitant.ui.bottomsheet.cosmetic.adapter.viewmodel.CosmeticsTagViewModel
import robin.vitalij.fortniteassitant.ui.common.BaseViewHolder

class CosmeticsTagViewHolder(
    override var binding: ItemCosmeticsTagsBinding,
    private val layoutInflater: LayoutInflater
) : BaseViewHolder<Cosmetics>(binding) {

    override fun bind(item: Cosmetics) {
        (item as? CosmeticsTagViewModel)?.let {
            binding.item = item
            itemView.chipGroup.initChip(item.tags, layoutInflater)
        }
    }
}
