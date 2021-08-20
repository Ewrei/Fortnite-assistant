package robin.vitalij.fortniteassitant.ui.bottomsheet.cosmetic.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.databinding.ItemCosmeticsHeaderBinding
import robin.vitalij.fortniteassitant.databinding.ItemCosmeticsTagsBinding
import robin.vitalij.fortniteassitant.databinding.ItemCosmeticsVariantBinding
import robin.vitalij.fortniteassitant.ui.bottomsheet.cosmetic.adapter.viewholder.CosmeticsHeaderViewHolder
import robin.vitalij.fortniteassitant.ui.bottomsheet.cosmetic.adapter.viewholder.CosmeticsTagViewHolder
import robin.vitalij.fortniteassitant.ui.bottomsheet.cosmetic.adapter.viewholder.variants.CosmeticsVariantsViewHolder
import robin.vitalij.fortniteassitant.ui.bottomsheet.cosmetic.adapter.viewmodel.Cosmetics
import robin.vitalij.fortniteassitant.ui.bottomsheet.cosmetic.adapter.viewmodel.CosmeticsType
import robin.vitalij.fortniteassitant.ui.bottomsheet.cosmetic.adapter.viewmodel.CosmeticsVariantViewModel
import robin.vitalij.fortniteassitant.ui.common.BaseViewHolder

class CosmeticsResultAdapter(
    private val layoutInflater: LayoutInflater,
    private val widthPixels: Int
) : RecyclerView.Adapter<BaseViewHolder<Cosmetics>>() {

    private val items = arrayListOf<Cosmetics>()

    fun setData(data: List<Cosmetics>) {
        items.clear()
        items.addAll(data)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<Cosmetics> {
        val inflater = LayoutInflater.from(parent.context)
        when (viewType) {
            CosmeticsType.HEADER.id -> {
                val binding = DataBindingUtil.inflate<ItemCosmeticsHeaderBinding>(
                    inflater,
                    R.layout.item_cosmetics_header,
                    parent,
                    false
                )
                return CosmeticsHeaderViewHolder(binding)
            }
            CosmeticsType.TAG.id -> {
                val binding = DataBindingUtil.inflate<ItemCosmeticsTagsBinding>(
                    inflater,
                    R.layout.item_cosmetics_tags,
                    parent,
                    false
                )
                return CosmeticsTagViewHolder(binding, layoutInflater)
            }
            CosmeticsType.VARIANT.id -> {
                val binding = DataBindingUtil.inflate<ItemCosmeticsVariantBinding>(
                    inflater,
                    R.layout.item_cosmetics_variant,
                    parent,
                    false
                )
                return CosmeticsVariantsViewHolder(binding, widthPixels)
            }
            else -> {
                val binding = DataBindingUtil.inflate<ItemCosmeticsTagsBinding>(
                    inflater,
                    R.layout.item_cosmetics_tags,
                    parent,
                    false
                )
                return CosmeticsTagViewHolder(binding, layoutInflater)
            }
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder<Cosmetics>, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

    override fun getItemViewType(position: Int) = items[position].getType().id

}
