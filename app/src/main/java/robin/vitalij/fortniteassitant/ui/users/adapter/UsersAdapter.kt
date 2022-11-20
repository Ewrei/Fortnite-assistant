package robin.vitalij.fortniteassitant.ui.users.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import robin.vitalij.fortniteassitant.databinding.ItemUserBinding
import robin.vitalij.fortniteassitant.model.UserModel

class UsersAdapter(val onClick: (accountId: String, playerName: String) -> Unit) :
    RecyclerView.Adapter<UsersHolder>() {

    private val items = mutableListOf<UserModel>()

    fun updateData(data: List<UserModel>) {
        if (items != data) {
            items.clear()
            items.addAll(data)
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = UsersHolder(
        ItemUserBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ), onClick
    )

    override fun onBindViewHolder(holder: UsersHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

}