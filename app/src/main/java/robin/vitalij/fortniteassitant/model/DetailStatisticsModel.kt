package robin.vitalij.fortniteassitant.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import robin.vitalij.fortniteassitant.model.enums.BattlesType
import robin.vitalij.fortniteassitant.model.enums.GameType

@Parcelize
class DetailStatisticsModel(
    val gameType: GameType,
    val battlesTypes: List<BattlesType>
) : Parcelable