package robin.vitalij.fortniteassitant.ui.vehicles.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import robin.vitalij.fortniteassitant.databinding.ItemGameVecilesBinding
import robin.vitalij.fortniteassitant.model.network.VehicleModel

class VehiclesAdapter(
    private val onClick: (vehicleModel: VehicleModel) -> Unit,
) : RecyclerView.Adapter<VehiclesHolder>() {

    private val items = mutableListOf<VehicleModel>()

    fun updateData(data: List<VehicleModel>) {
        if (items != data) {
            items.clear()
            items.addAll(data)
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = VehiclesHolder(
        ItemGameVecilesBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: VehiclesHolder, position: Int) {
        holder.bind(items[position])
        holder.itemView.setOnClickListener {
            onClick(items[position])
        }
    }

    override fun getItemCount() = items.size

}
