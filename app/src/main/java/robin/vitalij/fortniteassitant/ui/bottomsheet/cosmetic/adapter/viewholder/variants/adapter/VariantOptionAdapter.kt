package robin.vitalij.fortniteassitant.ui.bottomsheet.cosmetic.adapter.viewholder.variants.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.db.entity.Option

internal class VariantOptionAdapter(
    private val widthPixels: Int
) : RecyclerView.Adapter<VariantOptionHolder>() {

    private val items = arrayListOf<Option>()

    fun setData(data: List<Option>) {
        items.clear()
        items.addAll(data)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = VariantOptionHolder(
        DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_option,
            parent,
            false
        ), widthPixels
    )

    override fun onBindViewHolder(holder: VariantOptionHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size
}
