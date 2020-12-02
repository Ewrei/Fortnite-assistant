package robin.vitalij.fortniteassitant.ui.chartlist

import androidx.lifecycle.MutableLiveData
import robin.vitalij.fortniteassitant.model.enums.BattlesType
import robin.vitalij.fortniteassitant.model.enums.ChartsType
import robin.vitalij.fortniteassitant.model.enums.GameType
import robin.vitalij.fortniteassitant.ui.common.BaseViewModel
import robin.vitalij.fortniteassitant.utils.mapper.ChartsTypeMapper

class ChartsTypeViewModel : BaseViewModel() {

    val mutableLiveData = MutableLiveData<List<ChartsType>>()

    fun loadData(battlesType: BattlesType, gameType: GameType) {
        mutableLiveData.value = ChartsTypeMapper(gameType).transform(battlesType)
    }
}