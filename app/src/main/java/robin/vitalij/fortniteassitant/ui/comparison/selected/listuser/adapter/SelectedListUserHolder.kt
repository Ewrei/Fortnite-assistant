package robin.vitalij.fortniteassitant.ui.comparison.selected.listuser.adapter

import androidx.recyclerview.widget.RecyclerView
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.common.extensions.loadProfileImage
import robin.vitalij.fortniteassitant.common.extensions.setTextPercent
import robin.vitalij.fortniteassitant.common.extensions.setValueText
import robin.vitalij.fortniteassitant.databinding.ItemSelectedUserBinding
import robin.vitalij.fortniteassitant.model.comparison.PlayerModel

class SelectedListUserHolder(
    val binding: ItemSelectedUserBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: PlayerModel) {
        binding.cardView.isChecked = item.isSelected

        binding.matches.text = binding.root.context.getString(
            R.string.matches_format, item.userEntity.all?.overall?.getMatchesString()
        )

        binding.kills.setValueText(item.userEntity.all?.overall?.kills ?: 0)
        binding.wins.setValueText(item.userEntity.all?.overall?.wins ?: 0)
        binding.winRate.setTextPercent(item.userEntity.all?.overall?.winRate ?: 0.0)
        binding.deaths.setValueText(item.userEntity.all?.overall?.deaths ?: 0)

        binding.profileImage.loadProfileImage(item.userEntity.image)

        binding.profileName.text = item.userEntity.name
    }
}
