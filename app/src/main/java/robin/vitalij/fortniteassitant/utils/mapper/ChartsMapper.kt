package robin.vitalij.fortniteassitant.utils.mapper

import robin.vitalij.fortniteassitant.db.entity.UserEntity
import robin.vitalij.fortniteassitant.db.projection.UserHistory
import robin.vitalij.fortniteassitant.model.ChartsModel
import robin.vitalij.fortniteassitant.model.SessionModel
import robin.vitalij.fortniteassitant.model.enums.ChartsType
import robin.vitalij.fortniteassitant.utils.mapper.base.Mapper

private const val EMPTY_SESSION = 1

class ChartsMapper(private val chartsType: ChartsType) :
    Mapper<List<UserHistory>, ChartsModel> {

    override fun transform(obj: List<UserHistory>): ChartsModel {
        val chartsModel = ChartsModel()
        if (obj.size > EMPTY_SESSION) {
            chartsModel.sessionModels =
                generateSessionModels(obj)
        }
        chartsModel.header = getValue(obj.last().user)
        return chartsModel
    }

    private fun generateSessionModels(
        sessions: List<UserHistory>
    ): List<SessionModel> {
        val sessionModels = arrayListOf<SessionModel>()
        sessions.forEach {
            sessionModels.add(
                SessionModel(
                    it.playerSession.timestamp,
                    getValue(it.user)
                )
            )
        }
        return sessionModels
    }

    private fun getValue(userEntity: UserEntity) = when (chartsType) {
        ChartsType.KD -> userEntity.all?.overall?.kd ?: 0.0
        ChartsType.WIN_RATE -> userEntity.all?.overall?.winRate ?: 0.0
        ChartsType.AVG_SCORE -> userEntity.all?.overall?.getAvgScore() ?: 0.0
        ChartsType.SCORE_PER_MATCH -> userEntity.all?.overall?.scorePerMatch ?: 0.0
        ChartsType.SCORE_PER_MIN -> userEntity.all?.overall?.scorePerMin ?: 0.0
        ChartsType.KILLS_PER_MIN -> userEntity.all?.overall?.killsPerMin ?: 0.0
        ChartsType.KILLS_PER_MATCH -> userEntity.all?.overall?.killsPerMatch ?: 0.0
    }
}