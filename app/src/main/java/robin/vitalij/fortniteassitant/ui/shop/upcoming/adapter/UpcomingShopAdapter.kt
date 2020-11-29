package robin.vitalij.fortniteassitant.ui.shop.upcoming.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.model.network.shop.ItemShopUpcoming

class UpcomingShopAdapter(
    private val onClick: (itemShopUpcoming: ItemShopUpcoming) -> Unit
) : RecyclerView.Adapter<UpcomingShopHolder>() {

    private val items = arrayListOf<ItemShopUpcoming>()

    fun setData(data: List<ItemShopUpcoming>) {
        items.clear()
        items.addAll(data)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = UpcomingShopHolder(
        DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_upcoming_shop,
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: UpcomingShopHolder, position: Int) {
        holder.bind(items[position])
        holder.itemView.setOnClickListener {
            onClick(items[position])
        }
    }

    override fun getItemCount() = items.size
}
