package robin.vitalij.fortniteassitant.db.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
class PlayerSession(
    @ForeignKey(
        entity = UserEntity::class,
        parentColumns = ["playerId"],
        childColumns = ["playerId"],
        onDelete = ForeignKey.CASCADE
    )
    var accountId: String,
    val timestamp: Long
) : Serializable {

    @PrimaryKey(autoGenerate = true)
    var playerSessionId: Long = 0
}