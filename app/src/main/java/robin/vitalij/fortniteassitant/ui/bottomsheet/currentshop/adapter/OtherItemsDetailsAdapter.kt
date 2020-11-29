package robin.vitalij.fortniteassitant.ui.bottomsheet.currentshop.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.databinding.ItemInventoryContentBinding
import robin.vitalij.fortniteassitant.databinding.ItemOtherItemsDetailsBinding
import robin.vitalij.fortniteassitant.model.network.shop.OtherItemsDetails
import robin.vitalij.fortniteassitant.model.network.shop.ShopItem

internal class OtherItemsDetailsAdapter(
    private val onClick: (shopItem: OtherItemsDetails) -> Unit,
    private val widthPixels: Int
) : RecyclerView.Adapter<OtherItemsDetailsHolder>() {

    private val items = arrayListOf<OtherItemsDetails>()

    fun setData(data: List<OtherItemsDetails>) {
        items.clear()
        items.addAll(data)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OtherItemsDetailsHolder {
        val binding: ItemOtherItemsDetailsBinding =
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_other_items_details,
                parent,
                false
            )
        return OtherItemsDetailsHolder(binding, onClick, widthPixels)
    }

    override fun onBindViewHolder(holder: OtherItemsDetailsHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size
}
