package robin.vitalij.fortniteassitant.ui.vehicles.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.model.network.VehicleModel

class VehiclesAdapter(
    private val onClick: (vehicleModel: VehicleModel) -> Unit,
) : RecyclerView.Adapter<VehiclesHolder>() {

    private val items = arrayListOf<VehicleModel>()

    fun setData(data: List<VehicleModel>) {
        items.clear()
        items.addAll(data)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = VehiclesHolder(
        DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_game_veciles,
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
