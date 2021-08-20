package robin.vitalij.fortniteassitant.ui.vehicles.adapter

import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_news.view.*
import robin.vitalij.fortniteassitant.databinding.ItemGameCrewBinding
import robin.vitalij.fortniteassitant.databinding.ItemGameVecilesBinding
import robin.vitalij.fortniteassitant.model.network.CrewModel
import robin.vitalij.fortniteassitant.model.network.VehicleModel

class VehiclesHolder(
    var binding: ItemGameVecilesBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: VehicleModel) {
        binding.item = item
    }
}