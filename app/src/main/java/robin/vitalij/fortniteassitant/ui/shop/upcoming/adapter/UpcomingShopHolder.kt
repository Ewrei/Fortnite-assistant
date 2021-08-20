package robin.vitalij.fortniteassitant.ui.shop.upcoming.adapter

import androidx.recyclerview.widget.RecyclerView
import robin.vitalij.fortniteassitant.databinding.ItemTopBinding
import robin.vitalij.fortniteassitant.databinding.ItemUpcomingShopBinding
import robin.vitalij.fortniteassitant.model.network.shop.ItemShopUpcoming

class UpcomingShopHolder(
    var binding: ItemUpcomingShopBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: ItemShopUpcoming) {
        binding.item = item
    }
}