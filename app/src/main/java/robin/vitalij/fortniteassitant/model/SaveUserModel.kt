package robin.vitalij.fortniteassitant.model

import robin.vitalij.fortniteassitant.db.entity.MatchEntity
import robin.vitalij.fortniteassitant.db.entity.PlayerSession
import robin.vitalij.fortniteassitant.db.entity.UserEntity

data class SaveUserModel(
    var userEntity: UserEntity,
    var matches: List<MatchEntity>,
    var session: PlayerSession
)