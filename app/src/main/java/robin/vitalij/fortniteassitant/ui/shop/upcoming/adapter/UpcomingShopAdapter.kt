package robin.vitalij.fortniteassitant.ui.shop.upcoming.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import robin.vitalij.fortniteassitant.databinding.ItemUpcomingShopBinding
import robin.vitalij.fortniteassitant.model.network.shop.ItemShopUpcoming

class UpcomingShopAdapter(
    private val onClick: (itemShopUpcoming: ItemShopUpcoming) -> Unit
) : RecyclerView.Adapter<UpcomingShopHolder>() {

    private val items = mutableListOf<ItemShopUpcoming>()

    fun updateData(data: List<ItemShopUpcoming>) {
        if (items != data) {
            items.clear()
            items.addAll(data)
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = UpcomingShopHolder(
        ItemUpcomingShopBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: UpcomingShopHolder, position: Int) {
        holder.bind(items[position])
        holder.itemView.setOnClickListener {
            onClick(items[position])
        }
    }

    override fun getItemCount() = items.size
}
