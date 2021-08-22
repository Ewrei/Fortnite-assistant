package robin.vitalij.fortniteassitant.ui.crew.details

import androidx.lifecycle.MutableLiveData
import robin.vitalij.fortniteassitant.model.network.CrewRewardsModel
import robin.vitalij.fortniteassitant.ui.common.BaseViewModel

class CrewViewDetailsViewModel : BaseViewModel() {

    val mutableLiveData = MutableLiveData<List<CrewRewardsModel>>()

}