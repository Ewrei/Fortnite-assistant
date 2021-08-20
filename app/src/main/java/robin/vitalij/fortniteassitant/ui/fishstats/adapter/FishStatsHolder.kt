package robin.vitalij.fortniteassitant.ui.fishstats.adapter

import androidx.recyclerview.widget.RecyclerView
import robin.vitalij.fortniteassitant.databinding.ItemFishBinding
import robin.vitalij.fortniteassitant.databinding.ItemFishStatsBinding
import robin.vitalij.fortniteassitant.databinding.ItemWeaponBinding
import robin.vitalij.fortniteassitant.db.entity.FishEntity
import robin.vitalij.fortniteassitant.db.entity.WeaponEntity
import robin.vitalij.fortniteassitant.model.network.FishStats

class FishStatsHolder(
    var binding: ItemFishStatsBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: FishStats) {
        binding.item = item
    }
}