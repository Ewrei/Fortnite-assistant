package robin.vitalij.fortniteassitant.model

import robin.vitalij.fortniteassitant.common.extensions.DATE_PATTERN_YEAR_TIME
import robin.vitalij.fortniteassitant.common.extensions.getDateStringFormat
import robin.vitalij.fortniteassitant.db.entity.UserEntity

class UserModel(val userEntity: UserEntity, val isSelected: Boolean) {

    fun getLastData(): String = userEntity.timeUpdate.getDateStringFormat(DATE_PATTERN_YEAR_TIME)

}