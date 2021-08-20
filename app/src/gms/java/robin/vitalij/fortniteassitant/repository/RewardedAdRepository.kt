package robin.vitalij.fortniteassitant.repository

import android.content.Context
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import robin.vitalij.fortniteassitant.BuildConfig
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RewardedAdRepository @Inject constructor(context: Context) {

    var defaultRewardedAd: RewardedAd? = null

    init {
        loadReward(context)
    }

    fun loadReward(context: Context) {
        RewardedAd.load(
            context,
            BuildConfig.VIDEO_ID,
            AdRequest.Builder().build(),
            object : RewardedAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    defaultRewardedAd = null
                }

                override fun onAdLoaded(rewardedAd: RewardedAd) {
                    defaultRewardedAd = rewardedAd
                }
            })

    }
}