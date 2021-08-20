package robin.vitalij.fortniteassitant.interfaces

import robin.vitalij.fortniteassitant.model.enums.BattlesType
import robin.vitalij.fortniteassitant.model.enums.ChartsType
import robin.vitalij.fortniteassitant.model.enums.GameType

interface ChartsTypeCallback {

    fun chartsTypeClick(
        chartsType: ChartsType,
        battlesType: BattlesType,
        gameType: GameType,
    )
}