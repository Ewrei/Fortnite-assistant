package robin.vitalij.fortniteassitant.repository.yandex

import android.app.Activity
import android.content.Context
import com.yandex.mobile.ads.common.AdError
import com.yandex.mobile.ads.common.AdRequestConfiguration
import com.yandex.mobile.ads.common.AdRequestError
import com.yandex.mobile.ads.common.ImpressionData
import com.yandex.mobile.ads.rewarded.Reward
import com.yandex.mobile.ads.rewarded.RewardedAd
import com.yandex.mobile.ads.rewarded.RewardedAdEventListener
import com.yandex.mobile.ads.rewarded.RewardedAdLoadListener
import com.yandex.mobile.ads.rewarded.RewardedAdLoader
import javax.inject.Inject


class RewardAdYandexRepository @Inject constructor(private val context: Context) {


    var rewardedAd: RewardedAd? = null
    private var rewardedAdLoader: RewardedAdLoader? = null

    var onRewarded: () -> Unit = {

    }

    init {
        rewardedAdLoader = RewardedAdLoader(context).apply {
            setAdLoadListener(object : RewardedAdLoadListener {
                override fun onAdLoaded(ad: RewardedAd) {
                    rewardedAd = ad
                    // The ad was loaded successfully. Now you can show loaded ad.
                }

                override fun onAdFailedToLoad(adRequestError: AdRequestError) {
                    // Ad failed to load with AdRequestError.
                    // Attempting to load a new ad from the onAdFailedToLoad() method is strongly discouraged.
                }
            })
        }
    }

    fun loadRewardedAd() {
        val adRequestConfiguration = AdRequestConfiguration.Builder("R-M-1661871-3").build()
        rewardedAdLoader?.loadAd(adRequestConfiguration)
    }

    fun showAd(activity: Activity) {
        rewardedAd?.apply {
            setAdEventListener(object : RewardedAdEventListener {
                override fun onAdShown() {
                    // Called when ad is shown.
                }

                override fun onAdFailedToShow(adError: AdError) {
                    // Called when an RewardedAd failed to show

                    // Clean resources after Ad failed to show
                    rewardedAd?.setAdEventListener(null)
                    rewardedAd = null

                    // Now you can preload the next rewarded ad.
                    loadRewardedAd()
                }

                override fun onAdDismissed() {
                    // Called when ad is dismissed.
                    // Clean resources after Ad dismissed
                    rewardedAd?.setAdEventListener(null)
                    rewardedAd = null

                    // Now you can preload the next rewarded ad.
                    loadRewardedAd()
                }

                override fun onAdClicked() {
                    // Called when a click is recorded for an ad.
                }

                override fun onAdImpression(impressionData: ImpressionData?) {
                    // Called when an impression is recorded for an ad.
                }

                override fun onRewarded(reward: Reward) {
                    // Called when the user can be rewarded.
                }
            })
            show(activity)
        }
    }
}