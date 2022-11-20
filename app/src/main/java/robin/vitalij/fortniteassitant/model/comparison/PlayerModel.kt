package robin.vitalij.fortniteassitant.model.comparison

import robin.vitalij.fortniteassitant.db.entity.UserEntity

data class PlayerModel(
    val userEntity: UserEntity = UserEntity(),
    var isSelected: Boolean = false
)