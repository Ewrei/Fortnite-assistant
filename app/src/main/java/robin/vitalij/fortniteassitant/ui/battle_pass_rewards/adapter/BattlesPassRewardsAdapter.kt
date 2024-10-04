package robin.vitalij.fortniteassitant.ui.battle_pass_rewards.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import robin.vitalij.fortniteassitant.databinding.ItemBattlesPassRewardsBinding
import robin.vitalij.fortniteassitant.model.battle_pass_reward.BattlesPassRewardsModel

class BattlesPassRewardsAdapter(
    private val onClick: (battlesPassRewardsModel: BattlesPassRewardsModel) -> Unit
) : ListAdapter<BattlesPassRewardsModel, BattlesPassRewardsHolder>(BATTLES_PASS_REWARDS_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = BattlesPassRewardsHolder(
        ItemBattlesPassRewardsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: BattlesPassRewardsHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
        holder.itemView.setOnClickListener {
            onClick(currentItem)
        }
    }

    override fun getItemId(position: Int) = getItem(position).id

    companion object {
        private val BATTLES_PASS_REWARDS_COMPARATOR =
            object : DiffUtil.ItemCallback<BattlesPassRewardsModel>() {
                override fun areItemsTheSame(
                    oldItem: BattlesPassRewardsModel, newItem: BattlesPassRewardsModel
                ): Boolean = oldItem.id == newItem.id

                override fun areContentsTheSame(
                    oldItem: BattlesPassRewardsModel,
                    newItem: BattlesPassRewardsModel
                ): Boolean = oldItem == newItem
            }
    }

}