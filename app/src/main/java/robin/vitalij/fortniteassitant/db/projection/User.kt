package robin.vitalij.fortniteassitant.db.projection

import androidx.room.Embedded
import androidx.room.Relation
import robin.vitalij.fortniteassitant.db.entity.MatchEntity
import robin.vitalij.fortniteassitant.db.entity.UserEntity

class User {

    @Embedded
    lateinit var userEntity: UserEntity

    @Relation(
        parentColumn = "player_id",
        entity = MatchEntity::class,
        entityColumn = "playerId"
    )
    lateinit var matchs: List<MatchEntity>

}