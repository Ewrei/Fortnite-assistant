package robin.vitalij.fortniteassitant.ui.bottomsheet.vehicles.adapter

import androidx.recyclerview.widget.RecyclerView
import robin.vitalij.fortniteassitant.databinding.ItemVehiclesResultGearBinding

class VehiclesResultGearViewHolder(
    private val binding: ItemVehiclesResultGearBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: VehiclesResultListItem.GearItem) {
        binding.topSpeed.text = item.vehicleGearsModel.topSpeed
        binding.minSpeed.text = item.vehicleGearsModel.minSpeed
        binding.pushForce.text = item.vehicleGearsModel.pushForce
        binding.rampTime.text = item.vehicleGearsModel.rampTime
        binding.steeringAngleMultiplier.text = item.vehicleGearsModel.steeringAngleMultiplier
        binding.bAutoBrake.text = item.vehicleGearsModel.bAutoBrake.toString()
        binding.bIgnoreGravity.text = item.vehicleGearsModel.bIgnoreGravity.toString()
    }
}