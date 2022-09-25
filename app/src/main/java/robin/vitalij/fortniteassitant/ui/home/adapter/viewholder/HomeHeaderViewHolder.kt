package robin.vitalij.fortniteassitant.ui.home.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.common.binding.ImageViewBinging.loadDrawableRes
import robin.vitalij.fortniteassitant.common.binding.ImageViewBinging.loadImage
import robin.vitalij.fortniteassitant.common.binding.TextViewBinding.setHoursGame
import robin.vitalij.fortniteassitant.common.binding.TextViewBinding.setTextPercent
import robin.vitalij.fortniteassitant.common.extensions.getStringFormat
import robin.vitalij.fortniteassitant.common.extensions.setSafeOnClickListener
import robin.vitalij.fortniteassitant.databinding.ItemHomeHeaderBinding
import robin.vitalij.fortniteassitant.model.actions.HomeActions
import robin.vitalij.fortniteassitant.ui.home.adapter.HomeListItem

class HomeHeaderViewHolder(
    private val binding: ItemHomeHeaderBinding,
    private val onHomeAction: (homeActions: HomeActions) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: HomeListItem.HeaderItem) {
        binding.imageView.loadDrawableRes(R.drawable.img_profile_phone)
        binding.profileImage.loadImage(item.avatarUrl)
        binding.userName.text = item.userName
        binding.steamId.text = item.playerId
        binding.matchesPlayed.text = item.matchesPlayed.getStringFormat()
        binding.hoursGame.setHoursGame(item.timeHours)
        binding.level.text = binding.root.context.getString(R.string.level_format, item.level)
        binding.progress.setTextPercent(item.progress.toDouble())


        binding.currentSeason.setSafeOnClickListener {
            onHomeAction(HomeActions.OpenSeason)
        }

        binding.detailsStatistics.setSafeOnClickListener {
            onHomeAction(HomeActions.OpenDetailsStatistics)
        }

        binding.roundCornerProgressBar.progress = item.progress.toFloat()
    }
}