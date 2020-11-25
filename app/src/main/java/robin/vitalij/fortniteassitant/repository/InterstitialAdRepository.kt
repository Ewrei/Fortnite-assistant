package robin.vitalij.fortniteassitant.repository

import android.content.Context
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.InterstitialAd
import com.google.android.gms.ads.LoadAdError
import robin.vitalij.fortniteassitant.BuildConfig
import robin.vitalij.fortniteassitant.repository.storage.PreferenceManager
import robin.vitalij.fortniteassitant.utils.InterstitialAdAd
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class InterstitialAdRepository @Inject constructor(
    private val preferenceManager: PreferenceManager,
    private val context: Context
) {

    private var interstitialAd: InterstitialAd? = null

    fun showInterstitial() {
        interstitialAd?.let {
            InterstitialAdAd.showInterstitial(interstitialAd)
        }
    }

    fun initInterstitialAd(interstitialAdCallback: () -> Unit) {
        if (preferenceManager.getDisableAdvertising() <= Date().time && !(preferenceManager.getIsSubscription())
        ) {
            interstitialAd = InterstitialAd(context)
            interstitialAd?.adUnitId = BuildConfig.INTERSTITIAL_ID
            interstitialAd?.adListener = object : AdListener() {
                override fun onAdClosed() {
                    InterstitialAdAd.startGame(interstitialAd!!)
                }

                override fun onAdFailedToLoad(p0: Int) {
                    super.onAdFailedToLoad(p0)
                    interstitialAdCallback()
                }

                override fun onAdFailedToLoad(p0: LoadAdError?) {
                    super.onAdFailedToLoad(p0)
                    interstitialAdCallback()
                }

                override fun onAdLeftApplication() {
                    super.onAdLeftApplication()
                    interstitialAdCallback()
                }

                override fun onAdOpened() {
                    super.onAdOpened()
                }

                override fun onAdLoaded() {
                    super.onAdLoaded()
                    interstitialAdCallback()
                }

                override fun onAdClicked() {
                    super.onAdClicked()
                }

                override fun onAdImpression() {
                    super.onAdImpression()
                }
            }
            InterstitialAdAd.startGame(interstitialAd!!)
        } else {
            interstitialAdCallback()
        }
    }
}