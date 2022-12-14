package robin.vitalij.fortniteassitant.repository.storage

import java.util.*

interface PreferenceManager {

    fun setPlayerId(playerId: String)

    fun getPlayerId(): String

    fun setDisableAdvertising(time: Long)

    fun getDisableAdvertising(): Long

    fun getIsSubscription(): Boolean

    fun setIsSubscription(isSubscription: Boolean)

    fun setTimeAdd(date: Long)

    fun getTimeAdd(): Long

    fun getEstimate(): Int

    fun setEstimate(estimateSize: Int)

    fun getIsEstimate(): Boolean

    fun setIsEstimate(isEstimate: Boolean)

    fun getDateLastUpdate(): Long

    fun setDataLastUpdate(dateUpdate: Long)

    fun setSubscribeDialogTime(dataUpdate: Long)

    fun getSubscribeDialogTime(): Long

    fun setSubscriptionAccess(dataUpdate: Long)

    fun getSubscriptionAccess(): Long

    fun getSessionId(): String

    fun setSessionId(sessionId: String)

    fun setWeaponDataLastUpdate(dataUpdate: Long)

    fun getWeaponDataLastUpdate(): Long

    fun setFishDataLastUpdate(dataUpdate: Long)

    fun getFishDataLastUpdate(): Long

    fun setAchievementDataLastUpdate(dataUpdate: Long)

    fun getAchievementDataLastUpdate(): Long

    fun setCosmeticsNewDataLastUpdate(dataUpdate: Long)

    fun getCosmeticsNewDataLastUpdate(): Long

    fun setCosmeticsDataLastUpdate(dataUpdate: Long)

    fun getCosmeticsDataLastUpdate(): Long

    fun setBannerDataLastUpdate(dataUpdate: Long)

    fun getBannerDataLastUpdate(): Long

    fun setShowBasicRulesDate(date: Date)

    fun getShowBasicRulesDate(): Date
}