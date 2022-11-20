package robin.vitalij.fortniteassitant.ui.bottomsheet.cosmetic.adapter


import androidx.recyclerview.widget.RecyclerView
import robin.vitalij.fortniteassitant.databinding.ItemCosmeticsVariantBinding
import robin.vitalij.fortniteassitant.db.entity.Option
import robin.vitalij.fortniteassitant.ui.bottomsheet.cosmetic.adapter.varians_adapter.VariantOptionAdapter

class CosmeticsVariantsViewHolder(
    private val binding: ItemCosmeticsVariantBinding,
    private val widthPixels: Int
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: CosmeticsListItem.VariantItem) {
        binding.title.text = item.variant.type
        binding.recyclerView.run {
            item.variant.options?.let {
                adapter = initVariantOptionsAdapter(it)
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
