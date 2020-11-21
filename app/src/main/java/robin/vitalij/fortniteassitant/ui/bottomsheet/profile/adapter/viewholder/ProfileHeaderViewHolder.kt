package robin.vitalij.fortniteassitant.ui.bottomsheet.profile.adapter.viewholder

import robin.vitalij.fortniteassitant.databinding.ItemProfileHeaderBinding
import robin.vitalij.fortniteassitant.ui.bottomsheet.profile.adapter.viewmodel.Profile
import robin.vitalij.fortniteassitant.ui.bottomsheet.profile.adapter.viewmodel.ProfileHeaderViewModel
import robin.vitalij.fortniteassitant.ui.common.BaseViewHolder

class ProfileHeaderViewHolder(
    override val binding: ItemProfileHeaderBinding
) : BaseViewHolder<Profile>(binding) {

    override fun bind(item: Profile) {
        if (item is ProfileHeaderViewModel) {
            binding.item = item
        }
    }
}