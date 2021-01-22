package robin.vitalij.fortniteassitant.ui.news.fragment.adapter

import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_news.view.*
import robin.vitalij.fortniteassitant.databinding.ItemNewsBinding
import robin.vitalij.fortniteassitant.model.network.NewsModel

class NewsHolder(
    var binding: ItemNewsBinding,
    private val onVideoClick: (videoUrl: String, videoName: String) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: NewsModel) {
        binding.item = item
        itemView.videoButton.setOnClickListener {
            item.video?.let {
                onVideoClick(it, item.title)
            }
        }
    }
}