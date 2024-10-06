package robin.vitalij.fortniteassitant.ui.news.fragment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import robin.vitalij.fortniteassitant.databinding.ItemNewsBinding
import robin.vitalij.fortniteassitant.model.network.NewsModel

class NewsAdapter(private val onVideoClick: (videoUrl: String, videoName: String) -> Unit) :
    ListAdapter<NewsModel, NewsHolder>(ACHIEVEMENT_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = NewsHolder(
        ItemNewsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ), onVideoClick
    )

    override fun onBindViewHolder(holder: NewsHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val ACHIEVEMENT_COMPARATOR = object : DiffUtil.ItemCallback<NewsModel>() {
            override fun areItemsTheSame(
                oldItem: NewsModel, newItem: NewsModel
            ): Boolean = oldItem.date == newItem.date

            override fun areContentsTheSame(
                oldItem: NewsModel,
                newItem: NewsModel
            ): Boolean = oldItem == newItem
        }
    }

}