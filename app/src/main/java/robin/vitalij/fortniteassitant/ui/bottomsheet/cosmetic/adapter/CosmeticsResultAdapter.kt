package robin.vitalij.fortniteassitant.ui.bottomsheet.cosmetic.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.databinding.ItemCosmeticsHeaderBinding
import robin.vitalij.fortniteassitant.databinding.ItemCosmeticsTagsBinding
import robin.vitalij.fortniteassitant.databinding.ItemCosmeticsVariantBinding

class CosmeticsResultAdapter(
    private val layoutInflater: LayoutInflater,
    private val widthPixels: Int
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val items = mutableListOf<CosmeticsListItem>()

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(data: List<CosmeticsListItem>) {
        if (items != data) {
            items.clear()
            items.addAll(data)
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            R.layout.item_cosmetics_tags -> {
                CosmeticsTagViewHolder(
                    ItemCosmeticsTagsBinding.inflate(
                        inflater,
                        parent,
                        false
                    ),
                    layoutInflater
                )
            }
            R.layout.item_cosmetics_header -> {
                return CosmeticsHeaderViewHolder(
                    ItemCosmeticsHeaderBinding.inflate(
                        inflater,
                        parent,
                        false
                    )
                )
            }
            R.layout.item_cosmetics_variant -> {
                return CosmeticsVariantsViewHolder(
                    ItemCosmeticsVariantBinding.inflate(
                        inflater,
                        parent,
                        false
                    ),
                    widthPixels
                )
            }

            else -> throw UnknownError("Unknown view type $viewType")
        }
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = items[position]) {
            is CosmeticsListItem.TagItem -> (holder as CosmeticsTagViewHolder).bind(item)
            is CosmeticsListItem.HeaderItem -> (holder as CosmeticsHeaderViewHolder).bind(item)
            is CosmeticsListItem.VariantItem -> (holder as CosmeticsVariantsViewHolder).bind(item)
        }
    }

    override fun getItemViewType(position: Int): Int = when (items[position]) {
        is CosmeticsListItem.TagItem -> R.layout.item_cosmetics_tags
        is CosmeticsListItem.HeaderItem -> R.layout.item_cosmetics_header
        is CosmeticsListItem.VariantItem -> R.layout.item_cosmetics_variant
    }
}