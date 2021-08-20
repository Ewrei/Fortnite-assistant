package robin.vitalij.fortniteassitant.ui.bottomsheet.vehicles.adapter

import androidx.recyclerview.widget.RecyclerView
import robin.vitalij.fortniteassitant.databinding.ItemVehiclesResultHeaderBinding

class VehiclesResultHeaderViewHolder(
    private val binding: ItemVehiclesResultHeaderBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: VehiclesResultListItem.HeaderItem) {
        binding.item = item
    }
}