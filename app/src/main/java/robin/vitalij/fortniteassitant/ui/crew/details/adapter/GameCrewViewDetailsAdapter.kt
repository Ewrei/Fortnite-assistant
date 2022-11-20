package robin.vitalij.fortniteassitant.ui.crew.details.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import robin.vitalij.fortniteassitant.databinding.ItemCrewDetailsBinding
import robin.vitalij.fortniteassitant.model.network.CrewRewardsModel

class GameCrewViewDetailsAdapter : RecyclerView.Adapter<GameCrewViewDetailsHolder>() {

    private val items = mutableListOf<CrewRewardsModel>()

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(data: List<CrewRewardsModel>) {
        if (items != data) {
            items.clear()
            items.addAll(data)
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = GameCrewViewDetailsHolder(
        ItemCrewDetailsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: GameCrewViewDetailsHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

}
