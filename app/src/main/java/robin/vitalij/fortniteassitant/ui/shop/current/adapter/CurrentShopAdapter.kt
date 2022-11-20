package robin.vitalij.fortniteassitant.ui.shop.current.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import robin.vitalij.fortniteassitant.databinding.ItemCurrentShopBinding
import robin.vitalij.fortniteassitant.model.network.shop.ShopAdapterItem
import robin.vitalij.fortniteassitant.model.network.shop.ShopNewItem

class CurrentShopAdapter(
    private val onClick: (shopNewItem: ShopNewItem) -> Unit
) : RecyclerView.Adapter<CurrentShopHolder>() {

    private val items = mutableListOf<ShopNewItem>()

    fun setData(data: List<ShopNewItem>) {
        items.clear()
        items.addAll(data)
    }

    fun updateData(data: List<ShopNewItem>) {
        if (items != data) {
            items.clear()
            items.addAll(data)
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = CurrentShopHolder(
        ItemCurrentShopBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: CurrentShopHolder, position: Int) {
        holder.bind(items[position])
        holder.itemView.setOnClickListener {
            onClick(items[position])
        }
    }

    override fun getItemCount() = items.size
}
