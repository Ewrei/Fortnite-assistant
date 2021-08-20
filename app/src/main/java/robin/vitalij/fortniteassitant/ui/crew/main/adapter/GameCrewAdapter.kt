package robin.vitalij.fortniteassitant.ui.crew.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.model.network.CrewModel

class GameCrewAdapter(
    private val onClick: (crewModel: CrewModel) -> Unit,
    private val onVideoClick: (videoUrl: String, videoName: String) -> Unit
) : RecyclerView.Adapter<GameCrewHolder>() {

    private val items = arrayListOf<CrewModel>()

    fun setData(data: List<CrewModel>) {
        items.clear()
        items.addAll(data)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = GameCrewHolder(
        DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_game_crew,
            parent,
            false
        ),
        onVideoClick
    )

    override fun onBindViewHolder(holder: GameCrewHolder, position: Int) {
        holder.bind(items[position])
        holder.itemView.setOnClickListener {
            onClick(items[position])
        }
    }

    override fun getItemCount() = items.size

}
