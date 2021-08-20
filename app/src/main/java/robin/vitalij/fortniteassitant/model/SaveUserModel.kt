package robin.vitalij.fortniteassitant.model

import robin.vitalij.fortniteassitant.db.entity.PlayerSession
import robin.vitalij.fortniteassitant.db.entity.UserEntity

data class SaveUserModel(
    var userEntity: UserEntity,
    var session: PlayerSession
)