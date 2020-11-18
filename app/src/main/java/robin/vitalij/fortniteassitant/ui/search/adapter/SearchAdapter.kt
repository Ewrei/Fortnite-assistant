package robin.vitalij.fortniteassitant.ui.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.databinding.ItemSearchBinding
import robin.vitalij.fortniteassitant.model.network.search.SearchSteamUser

internal class SearchAdapter(private val onClick: (accountId: SearchSteamUser) -> Unit) :
    RecyclerView.Adapter<SearchHolder>() {

    private val items = arrayListOf<SearchSteamUser>()

    fun setData(data: List<SearchSteamUser>) {
        items.clear()
        items.addAll(data)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchHolder {
        val binding: ItemSearchBinding =
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_search,
                parent,
                false
            )
        return SearchHolder(binding, onClick)
    }

    override fun onBindViewHolder(holder: SearchHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size
}
