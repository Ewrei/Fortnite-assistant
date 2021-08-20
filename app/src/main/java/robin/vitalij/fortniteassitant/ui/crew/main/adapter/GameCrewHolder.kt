package robin.vitalij.fortniteassitant.ui.crew.main.adapter

import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_news.view.*
import robin.vitalij.fortniteassitant.databinding.ItemGameCrewBinding
import robin.vitalij.fortniteassitant.model.network.CrewModel

class GameCrewHolder(
    var binding: ItemGameCrewBinding,
    private val onVideoClick: (videoUrl: String, videoName: String) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: CrewModel) {
        binding.item = item

        itemView.videoButton.setOnClickListener {
            item.video?.let {
                onVideoClick(it, item.date)
            }
        }
    }
}