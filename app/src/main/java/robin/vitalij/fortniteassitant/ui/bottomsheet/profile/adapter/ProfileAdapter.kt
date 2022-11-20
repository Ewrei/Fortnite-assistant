package robin.vitalij.fortniteassitant.ui.bottomsheet.profile.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.databinding.ItemProfileBodyBinding
import robin.vitalij.fortniteassitant.databinding.ItemProfileHeaderBinding

class ProfileAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val items = mutableListOf<ProfileListItem>()

    fun setData(data: List<ProfileListItem>) {
        items.clear()
        items.addAll(data)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(data: List<ProfileListItem>) {
        if (items != data) {
            items.clear()
            items.addAll(data)
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            R.layout.item_profile_header -> {
                ProfileHeaderViewHolder(
                    ItemProfileHeaderBinding.inflate(
                        inflater,
                        parent,
                        false
                    )
                )
            }
            R.layout.item_profile_body -> {
                ProfileBodyViewHolder(
                    ItemProfileBodyBinding.inflate(
                        inflater,
                        parent,
                        false
                    )
                )
            }
            else -> throw UnknownError("Unknown view type $viewType")
        }
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = items[position]) {
            is ProfileListItem.HeaderItem -> (holder as ProfileHeaderViewHolder).bind(item)
            is ProfileListItem.BodyItem -> (holder as ProfileBodyViewHolder).bind(item)
        }
    }

    override fun getItemViewType(position: Int): Int = when (items[position]) {
        is ProfileListItem.HeaderItem -> R.layout.item_profile_header
        is ProfileListItem.BodyItem -> R.layout.item_profile_body
    }

}