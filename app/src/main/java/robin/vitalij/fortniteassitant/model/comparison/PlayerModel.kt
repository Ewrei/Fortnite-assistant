package robin.vitalij.fortniteassitant.model.comparison

import robin.vitalij.fortniteassitant.db.entity.UserEntity

data class PlayerModel(
    var userEntity: UserEntity = UserEntity(),
    var isSelected: Boolean = false
)