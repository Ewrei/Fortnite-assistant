package robin.vitalij.fortniteassitant.repository.yandex

import android.content.Context
import com.yandex.mobile.ads.common.AdRequest
import com.yandex.mobile.ads.common.AdRequestError
import com.yandex.mobile.ads.common.ImpressionData
import com.yandex.mobile.ads.interstitial.InterstitialAd
import com.yandex.mobile.ads.interstitial.InterstitialAdEventListener
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class InterstitialYandexAdRepository @Inject constructor(private val context: Context
) {

    var interstitialAd: InterstitialAd? = null

    fun loadInterstitial() {
        interstitialAd = InterstitialAd(context)
        interstitialAd?.loadAd(AdRequest.Builder().build())
        interstitialAd?.setAdUnitId("R-M-1661871-2")
        interstitialAd?.setInterstitialAdEventListener(InterstitialAdYandexAdsEventListener())
    }

    private inner class InterstitialAdYandexAdsEventListener : InterstitialAdEventListener {

        override fun onAdLoaded() {
           // Logger.debug( "onAdLoaded")
        }

        override fun onAdFailedToLoad(adRequestError: AdRequestError) {
          //  Logger.error(adRequestError.description)
            //hideLoading()
        }

        override fun onImpression(impressionData: ImpressionData?) {
           // Logger.debug("onImpression")
        }

        override fun onAdShown() {
          //  Logger.debug( "onAdShown")
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