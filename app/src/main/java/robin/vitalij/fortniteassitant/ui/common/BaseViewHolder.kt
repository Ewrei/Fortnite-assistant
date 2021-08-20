package robin.vitalij.fortniteassitant.ui.common

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

open class BaseViewHolder<T>(
    protected open val binding: ViewDataBinding
) : RecyclerView.ViewHolder(binding.root) {

    open fun bind(item: T) {

    }
}