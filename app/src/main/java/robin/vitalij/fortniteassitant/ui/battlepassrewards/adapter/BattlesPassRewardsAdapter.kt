package robin.vitalij.fortniteassitant.ui.battlepassrewards.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import robin.vitalij.fortniteassitant.databinding.ItemBattlesPassRewardsBinding
import robin.vitalij.fortniteassitant.model.battle_pass_reward.BattlesPassRewardsModel

class BattlesPassRewardsAdapter(
    private val onClick: (battlesPassRewardsModel: BattlesPassRewardsModel) -> Unit
) : RecyclerView.Adapter<BattlesPassRewardsHolder>() {

    private val items = mutableListOf<BattlesPassRewardsModel>()

    fun updateData(data: List<BattlesPassRewardsModel>) {
        if (items != data) {
            items.clear()
            items.addAll(data)
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = BattlesPassRewardsHolder(
        ItemBattlesPassRewardsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: BattlesPassRewardsHolder, position: Int) {
        holder.bind(items[position])
        holder.itemView.setOnClickListener {
            onClick(items[position])
        }
    }

    override fun getItemCount() = items.size

    override fun getItemId(position: Int) = items[position].id
}
