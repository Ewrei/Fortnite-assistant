package robin.vitalij.fortniteassitant.ui.comparison.selected.listuser.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.model.comparison.PlayerModel

internal class SelectedListUserAdapter(private val onClick: (playerModel: PlayerModel) -> Unit) :
    RecyclerView.Adapter<SelectedListUserHolder>() {

    val items = mutableListOf<PlayerModel>()

    fun setData(data: List<PlayerModel>) {
        items.clear()
        items.addAll(data)
    }

    fun updateData(data: List<PlayerModel>) {
        if (this.items != data) {
            setData(data)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = SelectedListUserHolder(
        DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_selected_user,
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: SelectedListUserHolder, position: Int) {
        holder.bind(items[position])
        holder.binding.cardView.setOnClickListener {
            items[position].isSelected = !items[position].isSelected
            notifyItemChanged(position)
            onClick(items[position])
        }
    }

    override fun getItemCount() = items.size
}
