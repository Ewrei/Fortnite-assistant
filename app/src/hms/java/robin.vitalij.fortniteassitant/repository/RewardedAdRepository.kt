package robin.vitalij.fortniteassitant.repository

import android.content.Context
import android.widget.Toast
import com.huawei.hms.ads.AdParam
import com.huawei.hms.ads.reward.RewardAd
import com.huawei.hms.ads.reward.RewardAdLoadListener
import robin.vitalij.fortniteassitant.BuildConfig
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RewardedAdRepository @Inject constructor(context: Context) {

    var defaultRewardedAd: RewardAd? = null

    init {
        loadReward(context)
    }

    fun loadReward(context: Context) {
        if (defaultRewardedAd == null) {
            defaultRewardedAd = RewardAd(context, BuildConfig.HUAWEI_VIDEO_ID)
        }
        val rewardAdLoadListener: RewardAdLoadListener = object : RewardAdLoadListener() {
            override fun onRewardAdFailedToLoad(errorCode: Int) {
            }

            override fun onRewardedLoaded() {
            }
        }

        defaultRewardedAd?.loadAd(AdParam.Builder().build(), rewardAdLoadListener)

    }
}