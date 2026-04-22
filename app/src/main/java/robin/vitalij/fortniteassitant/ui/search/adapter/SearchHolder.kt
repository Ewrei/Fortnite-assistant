package robin.vitalij.fortniteassitant.ui.search.adapter

import androidx.recyclerview.widget.RecyclerView
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.common.extensions.getStringFormat
import robin.vitalij.fortniteassitant.common.extensions.loadImage
import robin.vitalij.fortniteassitant.common.extensions.loadProfileImage
import robin.vitalij.fortniteassitant.common.extensions.setTextPercent
import robin.vitalij.fortniteassitant.common.extensions.setValueText
import robin.vitalij.fortniteassitant.databinding.ItemSearchBinding
import robin.vitalij.fortniteassitant.model.network.search.SearchSteamUserModel
import robin.vitalij.fortniteassitant.utils.TextUtils

class SearchHolder(
    private val binding: ItemSearchBinding,
    private val onClick: (searchSteamUser: SearchSteamUserModel) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: SearchSteamUserModel) {
        binding.userImage.loadProfileImage(item.avatarImage)
        binding.nickName.text = item.name
        binding.playerId.text = item.accountId

        binding.root.setOnClickListener {
            onClick(item)
        }

        binding.playTime.text = binding.root.context.getString(
            R.string.play_time,
            TextUtils.getAverage(
                item.playerStatsData.stats.all?.overall?.minutesPlayed?.toDouble(),
                60.0
            ).getStringFormat()
        )
        binding.matches.text = binding.root.context.getString(
            R.string.level_format,
            item.playerStatsData.battlePass.level
        )

        binding.allMatches.setValueText(item.playerStatsData.stats.all?.overall?.matches ?: 0)
        binding.kd.setValueText(item.playerStatsData.stats.all?.overall?.kd ?: 0.0)
        binding.winRate.setTextPercent(item.playerStatsData.stats.all?.overall?.winRate ?: 0.0)
    }
}
