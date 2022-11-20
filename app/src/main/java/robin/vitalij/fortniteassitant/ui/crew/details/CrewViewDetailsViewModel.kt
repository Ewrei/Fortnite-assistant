package robin.vitalij.fortniteassitant.ui.crew.details

import androidx.lifecycle.ViewModel
import robin.vitalij.fortniteassitant.model.network.CrewRewardsModel

class CrewViewDetailsViewModel : ViewModel() {

    val crewRewardsModels = mutableListOf<CrewRewardsModel>()
    var toolbarTitle: String = ""

}