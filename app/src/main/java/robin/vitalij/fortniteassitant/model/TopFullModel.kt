package robin.vitalij.fortniteassitant.model

import robin.vitalij.fortniteassitant.model.enums.BattlesType
import robin.vitalij.fortniteassitant.model.enums.GameType
import robin.vitalij.fortniteassitant.model.enums.TopType
import java.io.Serializable

class TopFullModel(
    val topType: TopType,
    val gameType: GameType,
    val battlesType: BattlesType
): Serializable