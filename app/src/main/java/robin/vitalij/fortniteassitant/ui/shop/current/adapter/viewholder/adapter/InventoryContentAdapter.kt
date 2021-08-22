package robin.vitalij.fortniteassitant.ui.shop.current.adapter.viewholder.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.model.network.shop.ShopItem

internal class InventoryContentAdapter(
    private val onClick: (shopItem: ShopItem) -> Unit,
    private val widthPixels: Int
) : RecyclerView.Adapter<InventoryContentHolder>() {

    private val items = arrayListOf<ShopItem>()

    fun setData(data: List<ShopItem>) {
        items.clear()
        items.addAll(data)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = InventoryContentHolder(
        DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_inventory_content,
            parent,
            false
        ), onClick, widthPixels
    )

    override fun onBindViewHolder(holder: InventoryContentHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size
}
