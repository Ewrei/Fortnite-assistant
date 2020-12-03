package robin.vitalij.fortniteassitant.ui.battlepassrewards.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.model.battlepassreward.BattlesPassRewardsModel

class BattlesPassRewardsAdapter(
    private val onClick: (battlesPassRewardsModel: BattlesPassRewardsModel) -> Unit
) : RecyclerView.Adapter<BattlesPassRewardsHolder>() {

    private val items = arrayListOf<BattlesPassRewardsModel>()

    fun setData(data: List<BattlesPassRewardsModel>) {
        items.clear()
        items.addAll(data)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = BattlesPassRewardsHolder(
        DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_battles_pass_rewards,
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
