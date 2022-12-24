package robin.vitalij.fortniteassitant.ui.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.model.network.search.SearchSteamUser

class SearchAdapter(private val onClick: (accountId: SearchSteamUser) -> Unit) :
    ListAdapter<SearchSteamUser, SearchHolder>(SEARCH_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = SearchHolder(
        DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_search,
            parent,
            false
        ), onClick
    )

    override fun onBindViewHolder(holder: SearchHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val SEARCH_COMPARATOR = object : DiffUtil.ItemCallback<SearchSteamUser>() {
            override fun areItemsTheSame(
                oldItem: SearchSteamUser, newItem: SearchSteamUser
            ): Boolean = oldItem.accountId == newItem.accountId

            override fun areContentsTheSame(
                oldItem: SearchSteamUser,
                newItem: SearchSteamUser
            ): Boolean = oldItem == newItem
        }

    }

}
