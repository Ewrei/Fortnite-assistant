package robin.vitalij.fortniteassitant.repository

import android.app.Activity
import android.content.Context
import com.google.android.gms.ads.*
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import robin.vitalij.fortniteassitant.BuildConfig
import robin.vitalij.fortniteassitant.repository.storage.PreferenceManager
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class InterstitialAdRepository @Inject constructor(
    private val preferenceManager: PreferenceManager,
    private val context: Context
) {

    private var ad: InterstitialAd? = null

    fun showInterstitial(activity: Activity) {
        if (ad != null) {
            ad?.fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdDismissedFullScreenContent() {
                    //do nothing
                }

                override fun onAdFailedToShowFullScreenContent(adError: AdError?) {
                    //do nothing
                }

                override fun onAdShowedFullScreenContent() {
                    ad = null
                    initInterstitialAd {
                    }
                }
            }

            ad?.show(activity)
        }
    }

    fun initInterstitialAd(interstitialAdCallback: () -> Unit) {
        if (preferenceManager.getDisableAdvertising() <= Date().time && !(preferenceManager.getIsSubscription())
        ) {
            val adRequest = AdRequest.Builder().build()
            InterstitialAd.load(
                context,
                BuildConfig.INTERSTITIAL_ID,
                adRequest,
                object : InterstitialAdLoadCallback() {
                    override fun onAdFailedToLoad(adError: LoadAdError) {
                        ad = null
                        interstitialAdCallback()
                    }

                    override fun onAdLoaded(interstitialAd: InterstitialAd) {
                        ad = interstitialAd
                        interstitialAdCallback()
                    }
                })
        } else {
            interstitialAdCallback()
        }
    }
}