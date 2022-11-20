package robin.vitalij.fortniteassitant.common.extensions

import robin.vitalij.fortniteassitant.db.entity.UserEntity
import robin.vitalij.fortniteassitant.model.DetailStatisticsModel
import robin.vitalij.fortniteassitant.model.enums.BattlesType
import robin.vitalij.fortniteassitant.model.enums.GameType
import robin.vitalij.fortniteassitant.model.network.stats.*
import robin.vitalij.fortniteassitant.utils.TextUtils

fun UserEntity.differenceUser(userEntityLast: UserEntity): UserEntity {
    return UserEntity(
        playerId = userEntityLast.playerId,
        name = userEntityLast.name,
        progress = userEntityLast.progress,
        timeUpdate = userEntityLast.timeUpdate,
        level = userEntityLast.level,
        image = userEntityLast.image,
        all = userEntityLast.all - this.all,
        keyboardMouse = userEntityLast.keyboardMouse - this.keyboardMouse,
        gamepad = userEntityLast.gamepad - this.gamepad,
        touch = userEntityLast.touch - this.touch
    )
}

fun UserEntity.getDetailStatisticsModelList(): MutableList<DetailStatisticsModel> {
    val list = mutableListOf<DetailStatisticsModel>()

    all?.let {
        list.add(getDetailStatisticsModel(GameType.ALL, it))
    }

    keyboardMouse?.let {
        list.add(getDetailStatisticsModel(GameType.KEYBOARD_MOUSE, it))
    }

    gamepad?.let {
        list.add(getDetailStatisticsModel(GameType.GAMEPAD, it))
    }

    touch?.let {
        list.add(getDetailStatisticsModel(GameType.TOUCH, it))
    }

    return list
}

private fun getDetailStatisticsModel(
    gameType: GameType,
    statsTypeDevice: StatsTypeDevice
): DetailStatisticsModel {
    val battlesTypes = mutableListOf<BattlesType>()
    statsTypeDevice.overall?.let {
        battlesTypes.add(BattlesType.OVERALL)
    }
    statsTypeDevice.solo?.let {
        battlesTypes.add(BattlesType.SOLO)
    }
    statsTypeDevice.duo?.let {
        battlesTypes.add(BattlesType.DUO)
    }
    statsTypeDevice.trio?.let {
        battlesTypes.add(BattlesType.TRIO)
    }
    statsTypeDevice.squad?.let {
        battlesTypes.add(BattlesType.SQUAD)
    }
    statsTypeDevice.ltm?.let {
        battlesTypes.add(BattlesType.LTM)
    }

    return DetailStatisticsModel(gameType, battlesTypes)
}

operator fun StatsTypeDevice?.minus(oneStatsTypeDevice: StatsTypeDevice?): StatsTypeDevice? {
    return if (oneStatsTypeDevice == null && this == null) {
        null
    } else if (oneStatsTypeDevice == null && this != null && this.overall?.matches ?: 0 >= 1) {
        this
    } else if (oneStatsTypeDevice?.overall?.matches ?: 0 == this?.overall?.matches ?: 0) {
        null
    } else if (this?.overall?.matches ?: 0 >= 1) {
        StatsTypeDevice(
            overall = this?.overall?.minus(oneStatsTypeDevice?.overall),
            solo = this?.solo?.minus(oneStatsTypeDevice?.solo),
            duo = this?.duo?.minus(oneStatsTypeDevice?.duo),
            trio = this?.trio?.minus(oneStatsTypeDevice?.trio),
            squad = this?.squad?.minus(oneStatsTypeDevice?.squad),
            ltm = this?.ltm?.minus(oneStatsTypeDevice?.ltm)
        )
    } else {
        null
    }
}