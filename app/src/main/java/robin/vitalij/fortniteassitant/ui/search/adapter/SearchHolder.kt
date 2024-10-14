package robin.vitalij.fortniteassitant.ui.search.adapter

import androidx.recyclerview.widget.RecyclerView
import robin.vitalij.fortniteassitant.common.binding.ImageViewBinging.loadImage
import robin.vitalij.fortniteassitant.databinding.ItemSearchBinding
import robin.vitalij.fortniteassitant.model.network.search.SearchSteamUserModel

class SearchHolder(
    private val binding: ItemSearchBinding,
    private val onClick: (searchSteamUser: SearchSteamUserModel) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: SearchSteamUserModel) {
        binding.userImage.loadImage(item.avatarImage)
        binding.nickName.text = item.name
        binding.playerId.text = item.accountId

        binding.root.setOnClickListener {
            onClick(item)
        }
    }
}