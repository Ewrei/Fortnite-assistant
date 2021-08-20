package robin.vitalij.fortniteassitant.ui.bottomsheet.cosmetic.adapter.viewholder.variants.adapter

import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_option.view.*
import robin.vitalij.fortniteassitant.databinding.ItemOptionBinding
import robin.vitalij.fortniteassitant.db.entity.Option

class VariantOptionHolder(
    var binding: ItemOptionBinding,
    private val widthPixels: Int
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Option) {
        binding.item = item
        itemView.cardView.layoutParams.width = widthPixels
        itemView.cardView.requestLayout()
    }
}