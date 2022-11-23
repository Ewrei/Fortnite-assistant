package robin.vitalij.fortniteassitant.ui.crew.preview.adapter

import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import robin.vitalij.fortniteassitant.common.binding.ImageViewBinging.loadImage
import robin.vitalij.fortniteassitant.databinding.ItemGameCrewBinding
import robin.vitalij.fortniteassitant.model.network.CrewModel

class GameCrewHolder(
    private val binding: ItemGameCrewBinding,
    private val onVideoClick: (videoUrl: String, videoName: String) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: CrewModel) {
        binding.image.loadImage(item.images.apiRender)
        binding.descriptions.text = item.descriptions.title
        binding.type.text = item.type
        binding.date.text = item.date

        binding.videoButton.isVisible = item.video != null

        binding.videoButton.setOnClickListener {
            item.video?.let {
                onVideoClick(it, item.date)
            }
        }
    }
}