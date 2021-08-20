package robin.vitalij.fortniteassitant.ui.crew.details.adapter

import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_news.view.*
import robin.vitalij.fortniteassitant.databinding.ItemCrewDetailsBinding
import robin.vitalij.fortniteassitant.databinding.ItemGameCrewBinding
import robin.vitalij.fortniteassitant.model.network.CrewModel
import robin.vitalij.fortniteassitant.model.network.CrewRewardsModel

class GameCrewViewDetailsHolder(
    var binding: ItemCrewDetailsBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: CrewRewardsModel) {
        binding.item = item
    }
}