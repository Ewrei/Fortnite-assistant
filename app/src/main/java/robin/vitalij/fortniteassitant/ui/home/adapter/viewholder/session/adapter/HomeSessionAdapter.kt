package robin.vitalij.fortniteassitant.ui.home.adapter.viewholder.session.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.ui.common.BaseViewHolder
import robin.vitalij.fortniteassitant.ui.home.adapter.viewholder.session.adapter.viewholder.HomeOtherViewHolder
import robin.vitalij.fortniteassitant.ui.home.adapter.viewholder.session.adapter.viewholder.HomeSessionViewHolder
import robin.vitalij.fortniteassitant.ui.home.adapter.viewholder.session.adapter.viewmodel.HomeSession
import robin.vitalij.fortniteassitant.ui.home.adapter.viewholder.session.adapter.viewmodel.HomeSessionType

class HomeSessionAdapter(
    private val openSessions: () -> Unit,
    private val openSession: (sessionId: Long, sessionLast: Long, sessionDate: String) -> Unit
) : RecyclerView.Adapter<BaseViewHolder<HomeSession>>() {

    private val items = arrayListOf<HomeSession>()

    fun setData(data: List<HomeSession>) {
        items.clear()
        items.addAll(data)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<HomeSession> {
        val inflater = LayoutInflater.from(parent.context)
        when (viewType) {
            HomeSessionType.SESSION.id -> {
                return HomeSessionViewHolder(
                    DataBindingUtil.inflate(
                        inflater,
                        R.layout.item_home_session_card,
                        parent,
                        false
                    ), openSession
                )
            }
            HomeSessionType.OTHER.id -> {
                return HomeOtherViewHolder(
                    DataBindingUtil.inflate(
                        inflater,
                        R.layout.item_home_session_other,
                        parent,
                        false
                    ), openSessions
                )
            }
            else -> {
                return HomeSessionViewHolder(
                    DataBindingUtil.inflate(
                        inflater,
                        R.layout.item_home_session_card,
                        parent,
                        false
                    ), openSession
                )
            }
        }
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: BaseViewHolder<HomeSession>, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemViewType(position: Int) = items[position].getType().id

}