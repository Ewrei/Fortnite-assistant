package robin.vitalij.fortniteassitant.repository.storage

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Singleton

private const val SHARED_PREFERENCES = "shared_preferences"
private const val PLAYER_ID = "player_id"
private const val DISABLE_ADVERTISING = "disable_advertising"
private const val IS_SUBSCRIPTION = "is_subscription"
private const val ADVERTISING_ADS = "advertising_ads"

private const val IS_ESTIMATE = "estimate"
private const val ESTIMATE_SIZE = "estimate_size"

private const val DATE_LAST_UPDATE = "date_last_update"
private val SUBSCRIBE_DIALOG_TIME = "subscribe_dialog_time"
private val SUBSCRIPTION_ACCESS = "subscription_access"
private val SESSION_ID = "session_id"

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
}