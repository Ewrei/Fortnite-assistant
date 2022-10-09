package robin.vitalij.fortniteassitant.ui.shop.current.header_adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import robin.vitalij.fortniteassitant.databinding.ItemHeaderShopBinding
import robin.vitalij.fortniteassitant.model.network.shop.ShopAdapterItem
import robin.vitalij.fortniteassitant.model.network.shop.ShopNewItem

class HeaderShopAdapter(private val onClick: (shopNewItem: ShopNewItem) -> Unit) :
    RecyclerView.Adapter<HeaderShopHolder>() {

    private val items = mutableListOf<ShopAdapterItem>()

    fun updateData(data: List<ShopAdapterItem>) {
        if (items != data) {
            items.clear()
            items.addAll(data)
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = HeaderShopHolder(
        ItemHeaderShopBinding.inflate(LayoutInflater.from(parent.context), parent, false),
        onClick
    )

    override fun onBindViewHolder(holder: HeaderShopHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

}