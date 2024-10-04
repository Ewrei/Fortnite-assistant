package robin.vitalij.fortniteassitant.ui.bottomsheet.current_shop.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import robin.vitalij.fortniteassitant.databinding.ItemOtherItemsDetailsBinding
import robin.vitalij.fortniteassitant.model.network.shop.GrantedModel

internal class OtherItemsDetailsAdapter(
    private val widthPixels: Int
) : RecyclerView.Adapter<OtherItemsDetailsHolder>() {

    private val items = mutableListOf<GrantedModel>()

    fun setData(data: List<GrantedModel>) {
        items.clear()
        items.addAll(data)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = OtherItemsDetailsHolder(
        ItemOtherItemsDetailsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ), widthPixels
    )

    override fun onBindViewHolder(holder: OtherItemsDetailsHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size
}
