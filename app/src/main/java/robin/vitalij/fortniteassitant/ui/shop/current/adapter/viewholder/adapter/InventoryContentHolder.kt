package robin.vitalij.fortniteassitant.ui.shop.current.adapter.viewholder.adapter

import android.graphics.Paint
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_inventory_content.view.*
import robin.vitalij.fortniteassitant.databinding.ItemInventoryContentBinding
import robin.vitalij.fortniteassitant.model.network.shop.ShopItem

class InventoryContentHolder(
    private val binding: ItemInventoryContentBinding,
    private val onClick: (inventoryModel: ShopItem) -> Unit,
    private val widthPixels: Int
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: ShopItem) {
        binding.item = item
        itemView.oldPrice.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
        itemView.cardView.layoutParams.width = widthPixels
        itemView.cardView.requestLayout()

        itemView.setOnClickListener {
            onClick(item)
        }
    }
}