package robin.vitalij.fortniteassitant.ui.bottomsheet.cosmetic.adapter.viewholder.variants


import robin.vitalij.fortniteassitant.databinding.ItemCosmeticsVariantBinding
import robin.vitalij.fortniteassitant.db.entity.Option
import robin.vitalij.fortniteassitant.ui.bottomsheet.cosmetic.adapter.viewholder.variants.adapter.VariantOptionAdapter
import robin.vitalij.fortniteassitant.ui.bottomsheet.cosmetic.adapter.viewmodel.Cosmetics
import robin.vitalij.fortniteassitant.ui.bottomsheet.cosmetic.adapter.viewmodel.CosmeticsVariantViewModel
import robin.vitalij.fortniteassitant.ui.common.BaseViewHolder

class CosmeticsVariantsViewHolder(
    override var binding: ItemCosmeticsVariantBinding,
    private val widthPixels: Int
) : BaseViewHolder<Cosmetics>(binding) {

    override fun bind(item: Cosmetics) {
        (item as? CosmeticsVariantViewModel)?.let {
            binding.item = item
            binding.recyclerView.run {
                item.variant.options?.let {
                    adapter = initVariantOptionsAdapter(it)
                }
            }
        }
    }

    private fun initVariantOptionsAdapter(list: List<Option>): VariantOptionAdapter {
        val adapter = VariantOptionAdapter(
            widthPixels
        )
        if (list.isNotEmpty()) {
            adapter.setData(list)
        }
        return adapter
    }
}
