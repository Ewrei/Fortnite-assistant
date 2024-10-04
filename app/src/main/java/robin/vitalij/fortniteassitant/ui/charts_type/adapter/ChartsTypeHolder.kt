package robin.vitalij.fortniteassitant.ui.charts_type.adapter

import androidx.recyclerview.widget.RecyclerView
import robin.vitalij.fortniteassitant.databinding.ItemChartsTypeBinding
import robin.vitalij.fortniteassitant.model.enums.ChartsType

class ChartsTypeHolder(
    private val binding: ItemChartsTypeBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: ChartsType) {
        binding.chartsType.setText(item.getTitleRes())
    }
}