package robin.vitalij.fortniteassitant.ui.crew.details.adapter

import androidx.recyclerview.widget.RecyclerView
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.common.binding.ImageViewBinging.loadBackgroundRarity
import robin.vitalij.fortniteassitant.common.binding.ImageViewBinging.loadImage
import robin.vitalij.fortniteassitant.databinding.ItemCrewDetailsBinding
import robin.vitalij.fortniteassitant.model.network.CrewRewardsModel

class GameCrewViewDetailsHolder(
    private val binding: ItemCrewDetailsBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: CrewRewardsModel) {
        binding.image.loadImage(item.item.images?.fullBackground)
        binding.image.loadBackgroundRarity(item.item.rarity?.id)

        binding.description.text = item.item.description
        binding.type.text = item.item.type.name
        binding.quantity.text =
            binding.root.context.getString(R.string.quantity_format, item.quantity)
    }

}