package robin.vitalij.fortniteassitant.db.projection

import androidx.room.Embedded
import androidx.room.Relation
import robin.vitalij.fortniteassitant.db.entity.UserEntity

class User {

    @Embedded
    lateinit var userEntity: UserEntity

}