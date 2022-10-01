package robin.vitalij.fortniteassitant.ui.bottomsheet.vehicles.adapter

import robin.vitalij.fortniteassitant.model.network.VehicleGearsModel

sealed class VehiclesResultListItem {

    data class HeaderItem(val name: String, val icon: String) : VehiclesResultListItem()

    data class TagItem(val tags: List<String>) : VehiclesResultListItem()

    data class GearItem(val vehicleGearsModel: VehicleGearsModel) : VehiclesResultListItem()

}