package robin.vitalij.fortniteassitant.ui.news.fragment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.model.network.NewsModel

internal class NewsAdapter(
    private val onVideoClick: (videoUrl: String, videoName: String) -> Unit
) : RecyclerView.Adapter<NewsHolder>() {

    private val items = arrayListOf<NewsModel>()

    fun setData(data: List<NewsModel>) {
        items.clear()
        items.addAll(data)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = NewsHolder(
        DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_news,
            parent,
            false
        ), onVideoClick
    )

    override fun onBindViewHolder(holder: NewsHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size
}
