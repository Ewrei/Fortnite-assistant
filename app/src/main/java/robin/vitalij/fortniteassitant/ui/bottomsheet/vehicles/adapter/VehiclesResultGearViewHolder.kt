package robin.vitalij.fortniteassitant.ui.bottomsheet.vehicles.adapter

import androidx.recyclerview.widget.RecyclerView
import robin.vitalij.fortniteassitant.databinding.ItemVehiclesResultGearBinding

class VehiclesResultGearViewHolder(
    private val binding: ItemVehiclesResultGearBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: VehiclesResultListItem.GearItem) {
        binding.item = item
    }
}