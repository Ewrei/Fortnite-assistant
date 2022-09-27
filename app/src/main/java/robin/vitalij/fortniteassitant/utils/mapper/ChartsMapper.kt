package robin.vitalij.fortniteassitant.utils.mapper

import robin.vitalij.fortniteassitant.db.entity.UserEntity
import robin.vitalij.fortniteassitant.db.projection.UserHistory
import robin.vitalij.fortniteassitant.model.ChartsModel
import robin.vitalij.fortniteassitant.model.SessionModel
import robin.vitalij.fortniteassitant.model.enums.BattlesType
import robin.vitalij.fortniteassitant.model.enums.ChartsType
import robin.vitalij.fortniteassitant.model.enums.GameType
import robin.vitalij.fortniteassitant.model.network.stats.*
import robin.vitalij.fortniteassitant.utils.mapper.base.Mapper

private const val EMPTY_SESSION = 1

class ChartsMapper(
    private val chartsType: ChartsType,
    private val battlesType: BattlesType,
    private val gameType: GameType
) : Mapper<List<UserHistory>, ChartsModel> {

    override fun transform(obj: List<UserHistory>): ChartsModel {
        var chartsModel = ChartsModel()
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
        val sessionModels = mutableListOf<SessionTempModel>()
        sessions.forEach {
            sessionModels.add(
                SessionTempModel(
                    it.playerSession.timestamp,
                    when (gameType) {
                        GameType.ALL -> {
                            getValue(it.user.all)
                        }
                        GameType.KEYBOARD_MOUSE -> {
                            getValue(it.user.keyboardMouse)
                        }
                        GameType.GAMEPAD -> {
                            getValue(it.user.gamepad)
                        }
                        GameType.TOUCH -> {
                            getValue(it.user.touch)
                        }
                    }
                )
            )
        }
        return sessionModels.filter { it.value != null }.map {
            SessionModel(it.timestamp, it.value ?: 0.0)
        }
    }

    private fun getValue(userEntity: UserEntity): Double {
        return when (gameType) {
            GameType.ALL -> {
                getValue(userEntity.all) ?: 0.0
            }
            GameType.KEYBOARD_MOUSE -> {
                getValue(userEntity.keyboardMouse) ?: 0.0
            }
            GameType.GAMEPAD -> {
                getValue(userEntity.gamepad) ?: 0.0
            }
            GameType.TOUCH -> {
                getValue(userEntity.touch) ?: 0.0
            }
        }
    }

    private fun getValue(statsTypeDevice: StatsTypeDevice?): Double? {
        return when (battlesType) {
            BattlesType.OVERALL -> {
                getValue(statsTypeDevice?.overall)
            }
            BattlesType.SOLO -> {
                getValue(statsTypeDevice?.solo)
            }
            BattlesType.DUO -> {
                getValue(statsTypeDevice?.duo)
            }
            BattlesType.TRIO -> {
                getValue(statsTypeDevice?.trio)
            }
            BattlesType.SQUAD -> {
                getValue(statsTypeDevice?.squad)
            }
            BattlesType.LTM -> {
                getValue(statsTypeDevice?.ltm)
            }
        }
    }

    private fun getValue(overall: Overall?) = when (chartsType) {
        ChartsType.KD -> overall?.kd
        ChartsType.WIN_RATE -> overall?.winRate
        ChartsType.AVG_SCORE -> overall?.getAvgScore()
        ChartsType.SCORE_PER_MATCH -> overall?.scorePerMatch
        ChartsType.SCORE_PER_MIN -> overall?.scorePerMin
        ChartsType.KILLS_PER_MIN -> overall?.killsPerMin
        ChartsType.KILLS_PER_MATCH -> overall?.killsPerMatch
    }

    private fun getValue(overall: SoloMatches?) = when (chartsType) {
        ChartsType.KD -> overall?.kd
        ChartsType.WIN_RATE -> overall?.winRate
        ChartsType.AVG_SCORE -> overall?.getAvgScore()
        ChartsType.SCORE_PER_MATCH -> overall?.scorePerMatch
        ChartsType.SCORE_PER_MIN -> overall?.scorePerMin
        ChartsType.KILLS_PER_MIN -> overall?.killsPerMin
        ChartsType.KILLS_PER_MATCH -> overall?.killsPerMatch
    }

    private fun getValue(overall: DuoMatches?) = when (chartsType) {
        ChartsType.KD -> overall?.kd
        ChartsType.WIN_RATE -> overall?.winRate
        ChartsType.AVG_SCORE -> overall?.getAvgScore()
        ChartsType.SCORE_PER_MATCH -> overall?.scorePerMatch
        ChartsType.SCORE_PER_MIN -> overall?.scorePerMin
        ChartsType.KILLS_PER_MIN -> overall?.killsPerMin
        ChartsType.KILLS_PER_MATCH -> overall?.killsPerMatch
    }

    private fun getValue(overall: TrioMatches?) = when (chartsType) {
        ChartsType.KD -> overall?.kd
        ChartsType.WIN_RATE -> overall?.winRate
        ChartsType.AVG_SCORE -> overall?.getAvgScore()
        ChartsType.SCORE_PER_MATCH -> overall?.scorePerMatch
        ChartsType.SCORE_PER_MIN -> overall?.scorePerMin
        ChartsType.KILLS_PER_MIN -> overall?.killsPerMin
        ChartsType.KILLS_PER_MATCH -> overall?.killsPerMatch
    }

    private fun getValue(overall: Ltm?) = when (chartsType) {
        ChartsType.KD -> overall?.kd
        ChartsType.WIN_RATE -> overall?.winRate
        ChartsType.AVG_SCORE -> overall?.getAvgScore()
        ChartsType.SCORE_PER_MATCH -> overall?.scorePerMatch
        ChartsType.SCORE_PER_MIN -> overall?.scorePerMin
        ChartsType.KILLS_PER_MIN -> overall?.killsPerMin
        ChartsType.KILLS_PER_MATCH -> overall?.killsPerMatch
    }

    data class SessionTempModel(
        val timestamp: Long, val value: Double?
    )
}