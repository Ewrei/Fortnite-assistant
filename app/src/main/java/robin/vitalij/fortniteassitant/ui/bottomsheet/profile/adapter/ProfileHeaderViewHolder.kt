package robin.vitalij.fortniteassitant.ui.bottomsheet.profile.adapter

import androidx.recyclerview.widget.RecyclerView
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.common.binding.ImageViewBinging.loadImage
import robin.vitalij.fortniteassitant.common.binding.TextViewBinding.setTextPercent
import robin.vitalij.fortniteassitant.common.binding.TextViewBinding.setValueText
import robin.vitalij.fortniteassitant.databinding.ItemProfileHeaderBinding

class ProfileHeaderViewHolder(
    private val binding: ItemProfileHeaderBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: ProfileListItem.HeaderItem) {
        binding.avatarImage.loadImage(item.avatarUrl)
        binding.profileName.text = item.userName
        binding.playerId.text = item.playerId
        binding.playTime.text = item.playTime
        binding.matches.text = binding.root.context.getString(R.string.level_format, item.level)

        binding.allMatches.setValueText(item.matches)
        binding.kd.setValueText(item.kd)
        binding.winRate.setTextPercent(item.winRate)
    }
}