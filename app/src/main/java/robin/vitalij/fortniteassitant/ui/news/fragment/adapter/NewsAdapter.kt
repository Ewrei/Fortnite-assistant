package robin.vitalij.fortniteassitant.ui.news.fragment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import robin.vitalij.fortniteassitant.databinding.ItemNewsBinding
import robin.vitalij.fortniteassitant.model.network.NewsModel

internal class NewsAdapter(
    private val onVideoClick: (videoUrl: String, videoName: String) -> Unit
) : RecyclerView.Adapter<NewsHolder>() {

    private val items = mutableListOf<NewsModel>()

    fun updateData(data: List<NewsModel>) {
        if (items != data) {
            items.clear()
            items.addAll(data)
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = NewsHolder(
        ItemNewsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ), onVideoClick
    )

    override fun onBindViewHolder(holder: NewsHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

}