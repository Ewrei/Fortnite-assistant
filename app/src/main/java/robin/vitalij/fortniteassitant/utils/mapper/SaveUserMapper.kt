package robin.vitalij.fortniteassitant.utils.mapper

import robin.vitalij.fortniteassitant.common.extensions.converterUserEntity
import robin.vitalij.fortniteassitant.db.entity.PlayerSession
import robin.vitalij.fortniteassitant.model.SaveUserModel
import robin.vitalij.fortniteassitant.model.network.stats.FortniteProfileResponse
import robin.vitalij.fortniteassitant.utils.mapper.base.Mapper
import java.util.*

class SaveUserMapper : Mapper<FortniteProfileResponse, SaveUserModel> {

    override fun transform(obj: FortniteProfileResponse): SaveUserModel {

        return SaveUserModel(
            userEntity = obj.stats.converterUserEntity(),
            session = PlayerSession(
                obj.stats.playerStatsData.account.id,
                Date().time
            ),
        ).apply {
            userEntity.avatar = obj.avatar
        }
    }
}