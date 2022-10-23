package robin.vitalij.fortniteassitant.ui.history.adapter

import androidx.recyclerview.widget.RecyclerView
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.common.binding.TextViewBinding.setSessionData
import robin.vitalij.fortniteassitant.common.binding.TextViewBinding.setValueText
import robin.vitalij.fortniteassitant.common.extensions.DATE_PATTERN_SHORT_TIME
import robin.vitalij.fortniteassitant.common.extensions.getDateStringFormat
import robin.vitalij.fortniteassitant.common.extensions.getDetailStatisticsModelList
import robin.vitalij.fortniteassitant.databinding.ItemHistoryBinding
import robin.vitalij.fortniteassitant.model.DetailStatisticsModel
import robin.vitalij.fortniteassitant.model.HistoryUserModel

class HistoryHolder(
    private val binding: ItemHistoryBinding,
    private val onClick: (sessionId: Long, sessionLast: Long, sessionDate: String, detailsStats: List<DetailStatisticsModel>) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: HistoryUserModel) {
        binding.date.setSessionData(item.startTimeUpdate, item.endTimeUpdate)
        binding.leftTop.setValueText(item.userEntity.all?.overall?.matches ?: 0)
        binding.rightTop.setValueText(item.userEntity.all?.overall?.kills ?: 0)
        binding.leftBottom.setValueText(item.userEntity.all?.overall?.kd ?: 0.0)
        binding.rightBottom.setValueText(item.userEntity.all?.overall?.minutesPlayed ?: 0)

        binding.cardView.setOnClickListener {
            onClick(
                item.sessionId, item.lastSessionId,
                "${item.startTimeUpdate.getDateStringFormat(DATE_PATTERN_SHORT_TIME)} -\n${
                    item.endTimeUpdate.getDateStringFormat(
                        DATE_PATTERN_SHORT_TIME
                    )
                }",
                item.userEntity.getDetailStatisticsModelList()
            )
        }

        binding.arcProgress.setAnimatedProgress(item.userEntity.all?.overall?.winRate ?: 0.0)
        binding.arcProgress.setBottomText(itemView.context.getString(R.string.win_rate))
    }
}