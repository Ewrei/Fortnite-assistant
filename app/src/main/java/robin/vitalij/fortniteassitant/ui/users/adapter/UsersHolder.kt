package robin.vitalij.fortniteassitant.ui.users.adapter

import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_user.view.*
import robin.vitalij.fortniteassitant.databinding.ItemUserBinding

class UsersHolder(
    var binding: ItemUserBinding,
    val onClick: (playerId: String, playerName: String) -> Unit
) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: UserModel) {
        binding.item = item
        itemView.cardView.isChecked = item.isSelected
        itemView.cardView.setOnClickListener {
            onClick(item.userEntity.playerId, item.userEntity.name)
        }
    }
}