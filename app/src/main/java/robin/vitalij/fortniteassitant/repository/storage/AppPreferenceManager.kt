package robin.vitalij.fortniteassitant.repository.storage

import android.content.Context
import android.content.SharedPreferences
import java.util.*
import javax.inject.Singleton

private const val SHARED_PREFERENCES = "shared_preferences"
private const val PLAYER_ID = "player_id"
private const val DISABLE_ADVERTISING = "disable_advertising"
private const val IS_SUBSCRIPTION = "is_subscription"
private const val ADVERTISING_ADS = "advertising_ads"

private const val IS_ESTIMATE = "estimate"
private const val ESTIMATE_SIZE = "estimate_size"

private const val DATE_LAST_UPDATE = "date_last_update"
private const val WEAPON_DATE_LAST_UPDATE = "weapon_date_last_update"
private const val FISH_DATE_LAST_UPDATE = "fish_date_last_update"
private const val ACHIEVEMENT_DATE_LAST_UPDATE = "achievement_date_last_update"
private const val COSMETICS_DATE_LAST_UPDATE = "cosmetics_date_last_update"
private const val COSMETICS_NEW_DATE_LAST_UPDATE = "cosmetics_new_date_last_update"
private const val BANNER_DATE_LAST_UPDATE = "banner"
private val SUBSCRIBE_DIALOG_TIME = "subscribe_dialog_time"
private val SUBSCRIPTION_ACCESS = "subscription_access"
private val SESSION_ID = "session_id"

private val SHOW_BASIC_RULES_DATE = "show_basic_rules_date"

@Singleton
class AppPreferenceManager(context: Context) :
    PreferenceManager {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE)

    override fun setPlayerId(playerId: String) {
        sharedPreferences.edit().putString(PLAYER_ID, playerId).apply()
    }

    override fun getPlayerId(): String {
        return sharedPreferences.getString(PLAYER_ID, "") ?: ""
    }

    override fun setDisableAdvertising(time: Long) {
        sharedPreferences.edit().putLong(DISABLE_ADVERTISING, time).apply()
    }

    override fun getDisableAdvertising(): Long {
        return sharedPreferences.getLong(DISABLE_ADVERTISING, 0)
    }

    override fun getIsSubscription(): Boolean {
        return sharedPreferences.getBoolean(IS_SUBSCRIPTION, false)
    }

    override fun setIsSubscription(isSubscription: Boolean) {
        sharedPreferences.edit().putBoolean(IS_SUBSCRIPTION, isSubscription).apply()
    }

    override fun setTimeAdd(date: Long) {
        sharedPreferences.edit().putLong(ADVERTISING_ADS, date).apply()
    }

    override fun getTimeAdd(): Long {
        return sharedPreferences.getLong(ADVERTISING_ADS, 0)
    }

    override fun getEstimate(): Int {
        return sharedPreferences.getInt(ESTIMATE_SIZE, 0)
    }

    override fun setEstimate(estimateSize: Int) {
        sharedPreferences.edit().putInt(ESTIMATE_SIZE, estimateSize).apply()
    }

    override fun getIsEstimate(): Boolean {
        return sharedPreferences.getBoolean(IS_ESTIMATE, true)
    }

    override fun setIsEstimate(isEstimate: Boolean) {
        sharedPreferences.edit().putBoolean(IS_ESTIMATE, isEstimate).apply()
    }

    override fun getDateLastUpdate(): Long {
        return sharedPreferences.getLong(DATE_LAST_UPDATE, 0)
    }

    override fun setDataLastUpdate(dateUpdate: Long) {
        sharedPreferences.edit().putLong(DATE_LAST_UPDATE, dateUpdate).apply()
    }

    override fun getSubscribeDialogTime(): Long {
        return sharedPreferences.getLong(SUBSCRIBE_DIALOG_TIME, 0)
    }

    override fun setSubscribeDialogTime(dataUpdate: Long) {
        sharedPreferences.edit().putLong(SUBSCRIBE_DIALOG_TIME, dataUpdate).apply()
    }

    override fun setSubscriptionAccess(dataUpdate: Long) {
        sharedPreferences.edit().putLong(SUBSCRIPTION_ACCESS, dataUpdate).apply()
    }

    override fun getSubscriptionAccess() = sharedPreferences.getLong(SUBSCRIPTION_ACCESS, 0)

    override fun getSessionId() = sharedPreferences.getString(SESSION_ID, "") ?: ""

    override fun setSessionId(sessionId: String) {
        sharedPreferences.edit().putString(SESSION_ID, sessionId).apply()
    }

    override fun getWeaponDataLastUpdate() = sharedPreferences.getLong(WEAPON_DATE_LAST_UPDATE, 0)

    override fun setWeaponDataLastUpdate(dateUpdate: Long) {
        sharedPreferences.edit().putLong(WEAPON_DATE_LAST_UPDATE, dateUpdate).apply()
    }

    override fun getFishDataLastUpdate() = sharedPreferences.getLong(FISH_DATE_LAST_UPDATE, 0)

    override fun setFishDataLastUpdate(dateUpdate: Long) {
        sharedPreferences.edit().putLong(FISH_DATE_LAST_UPDATE, dateUpdate).apply()
    }

    override fun getAchievementDataLastUpdate() = sharedPreferences.getLong(ACHIEVEMENT_DATE_LAST_UPDATE, 0)

    override fun setAchievementDataLastUpdate(dateUpdate: Long) {
        sharedPreferences.edit().putLong(ACHIEVEMENT_DATE_LAST_UPDATE, dateUpdate).apply()
    }

    override fun getCosmeticsNewDataLastUpdate() = sharedPreferences.getLong(
        COSMETICS_NEW_DATE_LAST_UPDATE, 0)

    override fun setCosmeticsNewDataLastUpdate(dateUpdate: Long) {
        sharedPreferences.edit().putLong(COSMETICS_NEW_DATE_LAST_UPDATE, dateUpdate).apply()
    }

    override fun getCosmeticsDataLastUpdate() = sharedPreferences.getLong(COSMETICS_DATE_LAST_UPDATE, 0)

    override fun setBannerDataLastUpdate(dateUpdate: Long) {
        sharedPreferences.edit().putLong(BANNER_DATE_LAST_UPDATE, dateUpdate).apply()
    }

    override fun getBannerDataLastUpdate() = sharedPreferences.getLong(BANNER_DATE_LAST_UPDATE, 0)

    override fun setCosmeticsDataLastUpdate(dateUpdate: Long) {
        sharedPreferences.edit().putLong(COSMETICS_DATE_LAST_UPDATE, dateUpdate).apply()
    }

    override fun setShowBasicRulesDate(date: Date) {
        sharedPreferences.edit().putLong(SHOW_BASIC_RULES_DATE, date.time).apply()
    }

    override fun getShowBasicRulesDate(): Date = Date(sharedPreferences.getLong(SHOW_BASIC_RULES_DATE, 0))

}