package robin.vitalij.fortniteassitant.ui.bottomsheet.cosmetic.adapter.viewholder

import robin.vitalij.fortniteassitant.databinding.ItemCosmeticsHeaderBinding
import robin.vitalij.fortniteassitant.ui.bottomsheet.cosmetic.adapter.viewmodel.Cosmetics
import robin.vitalij.fortniteassitant.ui.bottomsheet.cosmetic.adapter.viewmodel.CosmeticsHeaderViewModel
import robin.vitalij.fortniteassitant.ui.common.BaseViewHolder

class CosmeticsHeaderViewHolder(
    override var binding: ItemCosmeticsHeaderBinding,
) : BaseViewHolder<Cosmetics>(binding) {

    override fun bind(item: Cosmetics) {
        (item as? CosmeticsHeaderViewModel)?.let {
            binding.item = item
        }
    }
}
