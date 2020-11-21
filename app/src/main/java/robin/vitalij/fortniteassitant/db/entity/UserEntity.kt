package robin.vitalij.fortniteassitant.db.entity

import androidx.room.*
import robin.vitalij.fortniteassitant.model.network.stats.StatsTypeDevice

@Entity(tableName = "User")
class UserEntity(
    @ColumnInfo(name = "player_id") var playerId: String,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "level") var level: Int,
    @ColumnInfo(name = "progress") var progress: Int,
    @ColumnInfo(name = "image") var image: String?,
    @Embedded(prefix = "all") val all: StatsTypeDevice?,
    @Embedded(prefix = "keyboard_mouse") val keyboardMouse: StatsTypeDevice?,
    @Embedded(prefix = "gamepad") val gamepad: StatsTypeDevice?,
    @Embedded(prefix = "touch") val touch: StatsTypeDevice?
) {

    @PrimaryKey
    @ForeignKey(
        entity = PlayerSession::class,
        parentColumns = ["playerSessionId"],
        childColumns = ["playerSessionId"],
        onDelete = ForeignKey.CASCADE
    )
    var playerSessionId: Long = 0
}