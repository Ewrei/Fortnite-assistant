package robin.vitalij.fortniteassitant.ui.crew.details.adapter

import androidx.recyclerview.widget.RecyclerView
import robin.vitalij.fortniteassitant.databinding.ItemCrewDetailsBinding
import robin.vitalij.fortniteassitant.model.network.CrewRewardsModel

class GameCrewViewDetailsHolder(
    private val binding: ItemCrewDetailsBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: CrewRewardsModel) {
        binding.item = item
    }
}