package robin.vitalij.fortniteassitant.ui.bottomsheet.vehicles.adapter

import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import robin.vitalij.fortniteassitant.common.extensions.initChip
import robin.vitalij.fortniteassitant.databinding.ItemVehiclesResultTagBinding

class VehiclesResultTagViewHolder(
    private val binding: ItemVehiclesResultTagBinding,
    private val layoutInflater: LayoutInflater
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: VehiclesResultListItem.TagItem) {
        binding.chipGroup.initChip(item.tags, layoutInflater)
    }
}