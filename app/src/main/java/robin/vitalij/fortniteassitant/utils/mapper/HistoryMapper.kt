package robin.vitalij.fortniteassitant.utils.mapper

import robin.vitalij.fortniteassitant.common.extensions.differenceUser
import robin.vitalij.fortniteassitant.db.entity.UserEntity
import robin.vitalij.fortniteassitant.db.projection.UserHistory
import robin.vitalij.fortniteassitant.model.HistoryUserModel
import robin.vitalij.fortniteassitant.utils.TextUtils
import robin.vitalij.fortniteassitant.utils.mapper.base.Mapper

private const val ONE_SESSION = 1

class HistoryMapper : Mapper<List<UserHistory>, List<HistoryUserModel>> {

    override fun transform(obj: List<UserHistory>): List<HistoryUserModel> {
        val list = mutableListOf<HistoryUserModel>()

        if (obj.size > ONE_SESSION) {
            for (i in ONE_SESSION until obj.size) {
                list.add(
                    generateUser(
                        obj[i].user,
                        obj[i - ONE_SESSION].user
                    )
                )
            }
        }
        return list
    }

    private fun generateUser(
        userEntity: UserEntity,
        userEntityLast: UserEntity
    ): HistoryUserModel {
        val user = userEntity.differenceUser(userEntityLast)
        return HistoryUserModel(
            user,
            TextUtils.getAveragePercent(
                user.all?.overall?.wins?.toDouble(),
                user.all?.overall?.matches?.toDouble()
            ),
            userEntity.playerSessionId,
            userEntityLast.playerSessionId,
            startTimeUpdate = userEntity.timeUpdate,
            endTimeUpdate = userEntityLast.timeUpdate
        )
    }
}