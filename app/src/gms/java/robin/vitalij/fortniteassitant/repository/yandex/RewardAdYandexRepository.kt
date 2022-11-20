package robin.vitalij.fortniteassitant.repository.yandex

import android.content.Context
import com.yandex.mobile.ads.common.AdRequest
import com.yandex.mobile.ads.common.AdRequestError
import com.yandex.mobile.ads.common.ImpressionData
import com.yandex.mobile.ads.rewarded.Reward
import com.yandex.mobile.ads.rewarded.RewardedAd
import com.yandex.mobile.ads.rewarded.RewardedAdEventListener
import javax.inject.Inject


class RewardAdYandexRepository @Inject constructor(private val context: Context) {


    var rewardedAd: RewardedAd? = null

    var onRewarded: () -> Unit = {

    }

    fun load() {
        rewardedAd = RewardedAd(context)
        rewardedAd?.setAdUnitId("R-M-1661871-3")
        rewardedAd?.setRewardedAdEventListener(RewardedAdYandexAdsEventListener())
        rewardedAd?.loadAd(AdRequest.Builder().build())
    }

    private inner class RewardedAdYandexAdsEventListener : RewardedAdEventListener {

        override fun onAdLoaded() {
         //   Logger.debug("onAdLoaded")
        }

        override fun onRewarded(reward: Reward) {
            val message = "onRewarded, amount = ${reward.amount}, type = ${reward.type}"
            onRewarded()
          //  Logger.debug(message)
        }

        override fun onAdFailedToLoad(adRequestError: AdRequestError) {
            val message = "onAdFailedToLoad, error = ${adRequestError.description}"
           // Logger.error(message)
          //  hideLoading()
        }

        override fun onImpression(impressionData: ImpressionData?) {
          //  Logger.debug("onImpression")
        }

        override fun onAdShown() {
         //   Logger.debug("onAdShown")
        }

        override fun onAdDismissed() {
           // Logger.debug("onAdDismissed")
        }

        override fun onAdClicked() {
           // Logger.debug( "onAdClicked")
        }

        override fun onLeftApplication() {
          //  Logger.debug("onLeftApplication")
        }

        override fun onReturnedToApplication() {
          //  Logger.debug("onReturnedToApplication")
        }
    }
}