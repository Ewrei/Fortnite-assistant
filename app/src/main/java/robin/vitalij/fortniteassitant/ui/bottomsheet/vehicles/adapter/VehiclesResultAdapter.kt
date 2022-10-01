package robin.vitalij.fortniteassitant.ui.bottomsheet.vehicles.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.databinding.ItemVehiclesResultGearBinding
import robin.vitalij.fortniteassitant.databinding.ItemVehiclesResultHeaderBinding
import robin.vitalij.fortniteassitant.databinding.ItemVehiclesResultTagBinding

class VehiclesResultAdapter(
    private val layoutInflater: LayoutInflater
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val items = mutableListOf<VehiclesResultListItem>()

    fun setData(data: List<VehiclesResultListItem>) {
        items.clear()
        items.addAll(data)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(data: List<VehiclesResultListItem>) {
        if (items != data) {
            items.clear()
            items.addAll(data)
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            R.layout.item_vehicles_result_header -> {
                VehiclesResultHeaderViewHolder(
                    ItemVehiclesResultHeaderBinding.inflate(
                        inflater,
                        parent,
                        false
                    )
                )
            }
            R.layout.item_vehicles_result_tag -> {
                VehiclesResultTagViewHolder(
                    ItemVehiclesResultTagBinding.inflate(
                        inflater,
                        parent,
                        false
                    ),
                    layoutInflater
                )
            }
            R.layout.item_vehicles_result_gear -> {
                VehiclesResultGearViewHolder(
                    ItemVehiclesResultGearBinding.inflate(
                        inflater,
                        parent,
                        false
                    )
                )
            }
            else -> throw UnknownError("Unknown view type $viewType")
        }
    }


    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = items[position]) {
            is VehiclesResultListItem.HeaderItem -> (holder as VehiclesResultHeaderViewHolder).bind(
                item
            )
            is VehiclesResultListItem.TagItem -> (holder as VehiclesResultTagViewHolder).bind(
                item
            )
            is VehiclesResultListItem.GearItem -> (holder as VehiclesResultGearViewHolder).bind(
                item
            )
        }
    }

    override fun getItemViewType(position: Int): Int = when (items[position]) {
        is VehiclesResultListItem.HeaderItem -> R.layout.item_vehicles_result_header
        is VehiclesResultListItem.TagItem -> R.layout.item_vehicles_result_tag
        is VehiclesResultListItem.GearItem -> R.layout.item_vehicles_result_gear
    }

}