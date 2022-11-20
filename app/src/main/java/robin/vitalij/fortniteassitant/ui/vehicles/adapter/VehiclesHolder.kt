package robin.vitalij.fortniteassitant.ui.vehicles.adapter

import androidx.recyclerview.widget.RecyclerView
import robin.vitalij.fortniteassitant.common.binding.ImageViewBinging.loadImage
import robin.vitalij.fortniteassitant.databinding.ItemGameVecilesBinding
import robin.vitalij.fortniteassitant.model.network.VehicleModel

class VehiclesHolder(
    private val binding: ItemGameVecilesBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: VehicleModel) {
        binding.image.loadImage(item.icon)
        binding.name.text = item.name
    }

}