package robin.vitalij.fortniteassitant.model

import robin.vitalij.fortniteassitant.model.enums.BattlesType
import robin.vitalij.fortniteassitant.model.enums.GameType
import robin.vitalij.fortniteassitant.model.enums.TopType
import java.io.Serializable

class TopFullModel(
    val topType: TopType = TopType.KD,
    val gameType: GameType = GameType.ALL,
    val battlesType: BattlesType = BattlesType.OVERALL
): Serializable