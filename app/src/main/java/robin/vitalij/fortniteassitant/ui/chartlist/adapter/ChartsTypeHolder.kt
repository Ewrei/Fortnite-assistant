package robin.vitalij.fortniteassitant.ui.chartlist.adapter

import androidx.recyclerview.widget.RecyclerView
import robin.vitalij.fortniteassitant.databinding.ItemChartsTypeBinding
import robin.vitalij.fortniteassitant.model.enums.ChartsType

class ChartsTypeHolder(
    var binding: ItemChartsTypeBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: ChartsType) {
        binding.item = item
    }
}