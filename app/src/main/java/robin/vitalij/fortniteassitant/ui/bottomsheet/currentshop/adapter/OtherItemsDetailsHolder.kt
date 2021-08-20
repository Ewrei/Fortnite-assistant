package robin.vitalij.fortniteassitant.ui.bottomsheet.currentshop.adapter

import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_inventory_content.view.*
import robin.vitalij.fortniteassitant.databinding.ItemOtherItemsDetailsBinding
import robin.vitalij.fortniteassitant.model.network.shop.OtherItemsDetails

class OtherItemsDetailsHolder(
    var binding: ItemOtherItemsDetailsBinding,
    private val widthPixels: Int
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: OtherItemsDetails) {
        binding.item = item
        itemView.cardView.layoutParams.width = widthPixels
        itemView.cardView.requestLayout()
    }
}