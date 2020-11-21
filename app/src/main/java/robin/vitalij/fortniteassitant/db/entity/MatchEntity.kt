package robin.vitalij.fortniteassitant.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "Match")
class MatchEntity(
    @ColumnInfo(name = "platform") val platform: String,
    @ColumnInfo(name = "date") val date: String,
    @ColumnInfo(name = "mode") val mode: String,
    @ColumnInfo(name = "readable_name") val readableName: String,
    @ColumnInfo(name = "kills") val kills: Int,
    @ColumnInfo(name = "matches_played") val matchesPlayed: Int,
    @ColumnInfo(name = "minutesp_layed") val minutesPlayed: Int,
    @ColumnInfo(name = "playersout_lived") val playersoutLived: Int,
    @ColumnInfo(name = "score") val score: Int,
    @ColumnInfo(name = "placetop1") val placetop1: Int,
    @ColumnInfo(name = "placetop3") val placetop3: Int,
    @ColumnInfo(name = "placetop5") val placetop5: Int,
    @ColumnInfo(name = "placetop6") val placetop6: Int,
    @ColumnInfo(name = "placetop10") val placetop10: Int,
    @ColumnInfo(name = "placetop12") val placetop12: Int,
    @ColumnInfo(name = "placetop25") val placetop25: Int
) {

    @ForeignKey(
        entity = UserEntity::class,
        parentColumns = ["playerId"],
        childColumns = ["playerId"],
        onDelete = ForeignKey.CASCADE
    )
    var playerId: String = ""

    @PrimaryKey
    var id: String = "" //date_playerId

}