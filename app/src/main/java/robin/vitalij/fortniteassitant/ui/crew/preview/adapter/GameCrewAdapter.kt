package robin.vitalij.fortniteassitant.ui.crew.preview.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import robin.vitalij.fortniteassitant.databinding.ItemGameCrewBinding
import robin.vitalij.fortniteassitant.model.network.CrewModel

class GameCrewAdapter(
    private val onClick: (crewModel: CrewModel) -> Unit,
    private val onVideoClick: (videoUrl: String, videoName: String) -> Unit
) : RecyclerView.Adapter<GameCrewHolder>() {

    private val items = mutableListOf<CrewModel>()

    fun updateData(data: List<CrewModel>) {
        if (items != data) {
            items.clear()
            items.addAll(data)
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = GameCrewHolder(
        ItemGameCrewBinding.inflate(
            LayoutInflater.from(parent.context),
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
