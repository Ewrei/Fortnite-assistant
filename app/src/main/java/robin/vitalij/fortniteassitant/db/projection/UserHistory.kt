package robin.vitalij.fortniteassitant.db.projection

import androidx.room.Embedded
import androidx.room.Relation
import robin.vitalij.fortniteassitant.db.entity.PlayerSession
import robin.vitalij.fortniteassitant.db.entity.UserEntity

class UserHistory {

    @Embedded
    lateinit var playerSession: PlayerSession

    @Relation(
        parentColumn = "playerSessionId",
        entity = UserEntity::class,
        entityColumn = "playerSessionId"
    )
    lateinit var user: UserEntity

}