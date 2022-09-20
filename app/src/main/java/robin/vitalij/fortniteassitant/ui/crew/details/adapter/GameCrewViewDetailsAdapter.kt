package robin.vitalij.fortniteassitant.ui.crew.details.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.model.network.CrewRewardsModel

class GameCrewViewDetailsAdapter(
) : RecyclerView.Adapter<GameCrewViewDetailsHolder>() {

    private val items = mutableListOf<CrewRewardsModel>()

    fun setData(data: List<CrewRewardsModel>) {
        items.clear()
        items.addAll(data)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = GameCrewViewDetailsHolder(
        DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_crew_details,
            parent,
            false
        ),
    )

    override fun onBindViewHolder(holder: GameCrewViewDetailsHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

}
