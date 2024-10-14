package robin.vitalij.fortniteassitant.ui.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import robin.vitalij.fortniteassitant.databinding.ItemSearchBinding
import robin.vitalij.fortniteassitant.model.network.search.SearchSteamUserModel

class SearchAdapter(private val onClick: (accountId: SearchSteamUserModel) -> Unit) :
    ListAdapter<SearchSteamUserModel, SearchHolder>(SEARCH_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = SearchHolder(
        ItemSearchBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ), onClick
    )

    override fun onBindViewHolder(holder: SearchHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val SEARCH_COMPARATOR = object : DiffUtil.ItemCallback<SearchSteamUserModel>() {
            override fun areItemsTheSame(
                oldItem: SearchSteamUserModel, newItem: SearchSteamUserModel
            ): Boolean = oldItem.accountId == newItem.accountId

            override fun areContentsTheSame(
                oldItem: SearchSteamUserModel,
                newItem: SearchSteamUserModel
            ): Boolean = oldItem == newItem
        }
    }

}
