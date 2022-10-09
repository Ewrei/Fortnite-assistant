package robin.vitalij.fortniteassitant.ui.news.fragment.adapter

import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import robin.vitalij.fortniteassitant.common.binding.ImageViewBinging.loadImage
import robin.vitalij.fortniteassitant.databinding.ItemNewsBinding
import robin.vitalij.fortniteassitant.model.network.NewsModel

class NewsHolder(
    private val binding: ItemNewsBinding,
    private val onVideoClick: (videoUrl: String, videoName: String) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: NewsModel) {
        binding.date.text = item.getDateString()
        binding.title.text = item.title
        binding.body.text = item.body
        binding.imageView.loadImage(item.image)

        binding.videoButton.isVisible = item.video != null

        binding.videoButton.setOnClickListener {
            item.video?.let {
                onVideoClick(it, item.title)
            }
        }
    }

}