package robin.vitalij.fortniteassitant.utils.mapper

import robin.vitalij.fortniteassitant.model.enums.BattlesType
import robin.vitalij.fortniteassitant.model.enums.ChartsType
import robin.vitalij.fortniteassitant.model.enums.GameType
import robin.vitalij.fortniteassitant.utils.mapper.base.Mapper

class ChartsTypeMapper(private val gameType: GameType) :
    Mapper<BattlesType, List<ChartsType>> {

    override fun transform(obj: BattlesType): List<ChartsType> {
        return ChartsType.values().toList()
    }
    
}