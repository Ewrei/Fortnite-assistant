package robin.vitalij.fortniteassitant.ui.bottomsheet.profile.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.ui.bottomsheet.profile.adapter.viewholder.body.ProfileBodyViewHolder
import robin.vitalij.fortniteassitant.ui.bottomsheet.profile.adapter.viewholder.ProfileHeaderViewHolder
import robin.vitalij.fortniteassitant.ui.bottomsheet.profile.adapter.viewmodel.Profile
import robin.vitalij.fortniteassitant.ui.bottomsheet.profile.adapter.viewmodel.ProfileType
import robin.vitalij.fortniteassitant.ui.common.BaseViewHolder

class ProfileAdapter(
) : RecyclerView.Adapter<BaseViewHolder<Profile>>() {

    private val items = arrayListOf<Profile>()

    fun setData(data: List<Profile>) {
        items.clear()
        items.addAll(data)
    }

    fun updateData(data: List<Profile>) {
        if (items != data) {
            items.clear()
            items.addAll(data)
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<Profile> {
        val inflater = LayoutInflater.from(parent.context)
        when (viewType) {
            ProfileType.HEADER.id -> {
                return ProfileHeaderViewHolder(
                    DataBindingUtil.inflate(
                        inflater,
                        R.layout.item_profile_header,
                        parent,
                        false
                    )
                )
            }
            ProfileType.BODY.id -> {
                return ProfileBodyViewHolder(
                    DataBindingUtil.inflate(
                        inflater,
                        R.layout.item_profile_body,
                        parent,
                        false
                    )
                )
            }
            else -> {
                return ProfileHeaderViewHolder(
                    DataBindingUtil.inflate(
                        inflater,
                        R.layout.item_profile_header,
                        parent,
                        false
                    )
                )
            }
        }
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: BaseViewHolder<Profile>, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemViewType(position: Int) = items[position].getType().id
}
