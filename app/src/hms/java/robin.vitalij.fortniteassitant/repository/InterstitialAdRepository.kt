package robin.vitalij.fortniteassitant.repository

import android.content.Context
import com.huawei.hms.ads.AdListener
import com.huawei.hms.ads.AdParam
import com.huawei.hms.ads.InterstitialAd
import robin.vitalij.fortniteassitant.repository.storage.PreferenceManager
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
        // Display an interstitial ad.
        if (interstitialAd != null && interstitialAd!!.isLoaded) {
            interstitialAd?.show()
        }
    }

    fun initInterstitialAd(interstitialAdCallback: () -> Unit) {
        if (preferenceManager.getDisableAdvertising() <= Date().time && !(preferenceManager.getIsSubscription())
        ) {
            interstitialAd = InterstitialAd(context)
            interstitialAd?.adId = "testb4znbuh3n2"
            interstitialAd?.adListener = object : AdListener() {
                override fun onAdLoaded() {
                    super.onAdLoaded()
                    interstitialAdCallback()
                }

                override fun onAdFailed(errorCode: Int) {
                    interstitialAdCallback()
                }
            }

            val adParam = AdParam.Builder().build()
            interstitialAd?.loadAd(adParam)

            interstitialAdCallback()
        } else {
            interstitialAdCallback()
        }
    }
}