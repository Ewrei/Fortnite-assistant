package robin.vitalij.fortniteassitant.db.entity

import androidx.room.*
import robin.vitalij.fortniteassitant.common.extensions.DATE_PATTERN_YEAR_TIME
import robin.vitalij.fortniteassitant.common.extensions.getDateStringFormat
import robin.vitalij.fortniteassitant.model.network.stats.StatsTypeDevice

@Entity(tableName = "User")
class UserEntity(
    @ColumnInfo(name = "player_id") var playerId: String = "",
    @ColumnInfo(name = "name") var name: String = "",
    @ColumnInfo(name = "level") var level: Int = 0,
    @ColumnInfo(name = "progress") var progress: Int = 0,
    @ColumnInfo(name = "image") var image: String? = "",
    @Embedded(prefix = "all") val all: StatsTypeDevice? = null,
    @Embedded(prefix = "keyboard_mouse") val keyboardMouse: StatsTypeDevice? = null,
    @Embedded(prefix = "gamepad") val gamepad: StatsTypeDevice? = null,
    @Embedded(prefix = "touch") val touch: StatsTypeDevice? = null,
    @ColumnInfo(name = "timeUpdate") var timeUpdate: Long = 0L,
) {

    @PrimaryKey
    @ForeignKey(
        entity = PlayerSession::class,
        parentColumns = ["playerSessionId"],
        childColumns = ["playerSessionId"],
        onDelete = ForeignKey.CASCADE
    )
    var playerSessionId: Long = 0

    fun getLastUpdate(): String = timeUpdate.getDateStringFormat(DATE_PATTERN_YEAR_TIME)
}