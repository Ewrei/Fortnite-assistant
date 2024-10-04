package robin.vitalij.fortniteassitant.ui.users.adapter

import androidx.recyclerview.widget.RecyclerView
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.common.binding.ImageViewBinging.loadImage
import robin.vitalij.fortniteassitant.common.extensions.getStringFormat
import robin.vitalij.fortniteassitant.databinding.ItemUserBinding
import robin.vitalij.fortniteassitant.model.UserModel

class UsersHolder(
    private val binding: ItemUserBinding,
    private val onClick: (playerId: String, playerName: String) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: UserModel) {
        binding.profileImage.loadImage(item.userEntity.image)
        binding.profileName.text = item.userEntity.name
        binding.lifetime.text =
            binding.root.context.getString(R.string.last_date_update, item.getLastData())
        binding.matches.text = binding.root.context.getString(
            R.string.matches_format,
            item.userEntity.all?.overall?.getMatchesString()
        )

        binding.wins.text = item.userEntity.all?.overall?.wins.getStringFormat()
        binding.winRate.text = item.userEntity.all?.overall?.winRate.getStringFormat("%")
        binding.kills.text = item.userEntity.all?.overall?.kills.getStringFormat()
        binding.kd.text = item.userEntity.all?.overall?.kd.getStringFormat()

        binding.cardView.isChecked = item.isSelected
        binding.cardView.setOnClickListener {
            onClick(item.userEntity.playerId, item.userEntity.name)
        }
    }

}