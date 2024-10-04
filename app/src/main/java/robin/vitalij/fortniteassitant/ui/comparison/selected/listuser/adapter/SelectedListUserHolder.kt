package robin.vitalij.fortniteassitant.ui.comparison.selected.listuser.adapter

import androidx.recyclerview.widget.RecyclerView
import robin.vitalij.fortniteassitant.databinding.ItemSelectedUserBinding
import robin.vitalij.fortniteassitant.model.comparison.PlayerModel

class SelectedListUserHolder(
    val binding: ItemSelectedUserBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: PlayerModel) {
        binding.item = item
        binding.cardView.isChecked = item.isSelected
    }
}
