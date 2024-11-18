package robin.vitalij.fortniteassitant.ui.home.adapter.viewholder.session.adapter

import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.common.extensions.DATE_PATTERN_SHORT_TIME
import robin.vitalij.fortniteassitant.common.extensions.getDateStringFormat
import robin.vitalij.fortniteassitant.common.extensions.getDetailStatisticsModelList
import robin.vitalij.fortniteassitant.common.extensions.loadDrawable
import robin.vitalij.fortniteassitant.databinding.ItemHomeSessionCardBinding
import robin.vitalij.fortniteassitant.model.DetailStatisticsModel

class HomeSessionViewHolder(
    private val binding: ItemHomeSessionCardBinding,
    private val openSession: (sessionId: Long, sessionLast: Long, sessionDate: String, detailsStats: List<DetailStatisticsModel>) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: HomeSessionListItem.SessionItem) {
        binding.item = item

        binding.mapImage.loadDrawable(
            ContextCompat.getDrawable(
                binding.root.context,
                R.drawable.img_profile_phone
            )
        )

        binding.cardView.setOnClickListener {
            openSession(
                item.historyUserModel.sessionId,
                item.historyUserModel.lastSessionId,
                "${
                    item.historyUserModel.startTimeUpdate.getDateStringFormat(
                        DATE_PATTERN_SHORT_TIME
                    )
                } - \n${
                    item.historyUserModel.endTimeUpdate.getDateStringFormat(
                        DATE_PATTERN_SHORT_TIME
                    )
                }",
                item.historyUserModel.userEntity.getDetailStatisticsModelList()
            )
        }
    }
}