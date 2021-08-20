package robin.vitalij.fortniteassitant.ui.users.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.model.UserModel

internal class UsersAdapter(val onClick: (accountId: String, playerName: String) -> Unit) :
    RecyclerView.Adapter<UsersHolder>() {

    private val items = arrayListOf<UserModel>()

    fun setData(data: List<UserModel>) {
        items.clear()
        items.addAll(data)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = UsersHolder(
        DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_user,
            parent,
            false
        ), onClick
    )

    override fun onBindViewHolder(holder: UsersHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

}