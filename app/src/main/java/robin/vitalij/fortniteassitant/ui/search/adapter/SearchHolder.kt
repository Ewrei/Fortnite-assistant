package robin.vitalij.fortniteassitant.ui.search.adapter

import androidx.recyclerview.widget.RecyclerView
import robin.vitalij.fortniteassitant.databinding.ItemSearchBinding
import robin.vitalij.fortniteassitant.model.network.search.SearchSteamUser

class SearchHolder(
    var binding: ItemSearchBinding,
    val onClick: (accountId: SearchSteamUser) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: SearchSteamUser) {
        binding.item = item
        itemView.setOnClickListener {
            onClick(item)
        }
    }
}