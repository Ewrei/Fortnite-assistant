package robin.vitalij.fortniteassitant.utils.mapper

import robin.vitalij.fortniteassitant.model.network.VehicleModel
import robin.vitalij.fortniteassitant.ui.bottomsheet.vehicles.adapter.VehiclesResultListItem
import robin.vitalij.fortniteassitant.utils.mapper.base.Mapper

class VehiclesResultMapper() :
    Mapper<VehicleModel, List<VehiclesResultListItem>> {

    override fun transform(obj: VehicleModel): List<VehiclesResultListItem> {
        val list = arrayListOf<VehiclesResultListItem>()

        list.add(VehiclesResultListItem.HeaderItem(obj.name, obj.icon))

        obj.gears.forEach {
            list.add(VehiclesResultListItem.GearItem(it))
        }

        list.add(VehiclesResultListItem.TagItem(obj.spawnNames))

        return list
    }
}