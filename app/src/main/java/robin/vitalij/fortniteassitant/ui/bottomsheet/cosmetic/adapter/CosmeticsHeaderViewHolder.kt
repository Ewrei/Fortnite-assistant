package robin.vitalij.fortniteassitant.ui.bottomsheet.cosmetic.adapter

import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import robin.vitalij.fortniteassitant.common.binding.ImageViewBinging.loadBackgroundRarity
import robin.vitalij.fortniteassitant.common.binding.ImageViewBinging.loadImages
import robin.vitalij.fortniteassitant.databinding.ItemCosmeticsHeaderBinding
import robin.vitalij.fortniteassitant.db.entity.Set

class CosmeticsHeaderViewHolder(
    private val binding: ItemCosmeticsHeaderBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: CosmeticsListItem.HeaderItem) {
        setAddDate(item.addDate)
        setIntroduction(item.introduction)
        setTypeSet(item.set)

        binding.imageView.loadBackgroundRarity(item.rarity?.value ?: "")

        item.images?.let {
            binding.imageView.loadImages(item.images)
        }

        binding.name.text = item.name
        binding.description.text = item.description
        binding.type.text = item.typeCosmetics.displayValue
    }

    private fun setAddDate(addDate: String?) {
        binding.addDate.text = addDate
        binding.addDate.isVisible = addDate != null
    }

    private fun setIntroduction(introduction: String?) {
        binding.introduction.text = introduction
        binding.introduction.isVisible = introduction != null
    }

    private fun setTypeSet(set: Set?) {
        binding.typeSet.text = set?.text
        binding.typeSet.isVisible = set != null
    }
}
