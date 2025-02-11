package robin.vitalij.fortniteassitant.ui.home.adapter.viewholder.session.adapter

import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.common.extensions.loadDrawable
import robin.vitalij.fortniteassitant.common.extensions.setTextPercent
import robin.vitalij.fortniteassitant.common.extensions.setValueText
import robin.vitalij.fortniteassitant.databinding.ItemHomeSessionOtherBinding

class HomeOtherViewHolder(
    private val binding: ItemHomeSessionOtherBinding,
    private val openSessions: () -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: HomeSessionListItem.OtherItem) {
        binding.matchesPlayed.setValueText(item.matches)
        binding.rounds.setTextPercent(item.winRate)
        binding.leftBottom.setValueText(item.kd)


        binding.mapImage.loadDrawable(
            ContextCompat.getDrawable(
                binding.root.context,
                R.drawable.img_profile_phone
            )
        )

        binding.root.setOnClickListener {
            openSessions()
        }
    }
}