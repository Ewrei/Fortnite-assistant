package robin.vitalij.fortniteassitant.repository

import android.app.Activity
import android.content.Context
import com.google.android.gms.ads.*
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.unity3d.ads.IUnityAdsShowListener
import com.unity3d.ads.UnityAds
import com.unity3d.ads.UnityAdsShowOptions
import robin.vitalij.fortniteassitant.BuildConfig
import robin.vitalij.fortniteassitant.repository.storage.PreferenceManager
import robin.vitalij.fortniteassitant.repository.unity.InterstitialUnityAdRepository
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class InterstitialAdRepository @Inject constructor(
    private val preferenceManager: PreferenceManager,
    private val interstitialUnityAdRepository: InterstitialUnityAdRepository,
    private val context: Context
) {

    private var ad: InterstitialAd? = null

    private var isAdsShow: Boolean = false


    fun showInterstitial(activity: Activity) {
        if (ad != null && !isAdsShow) {
            isAdsShow = true
            interstitialUnityAdRepository.isInterstitialUnityAdLoad = false

            ad?.fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdDismissedFullScreenContent() {
                    interstitialUnityAdRepository.isInterstitialUnityAdLoad = false
                    ad = null
                }

                override fun onAdFailedToShowFullScreenContent(adError: AdError?) {
                    isAdsShow = false
                }

                override fun onAdShowedFullScreenContent() {
                    interstitialUnityAdRepository.isInterstitialUnityAdLoad = false
                    ad = null
                }
            }

            ad?.show(activity)
        } else
            if (interstitialUnityAdRepository.isInterstitialUnityAdLoad && !isAdsShow) {
                isAdsShow = true
                UnityAds.show(activity,
                    InterstitialUnityAdRepository.PLACEMENT_ID,
                    UnityAdsShowOptions(),
                    object : IUnityAdsShowListener {
                        override fun onUnityAdsShowFailure(
                            placementId: String?,
                            error: UnityAds.UnityAdsShowError?,
                            message: String?
                        ) {
                            isAdsShow = false
                        }

                        override fun onUnityAdsShowStart(placementId: String?) {
                            //do nothing
                            interstitialUnityAdRepository.isInterstitialUnityAdLoad = false
                        }

                        override fun onUnityAdsShowClick(placementId: String?) {
                            //do nothing
                        }

                        override fun onUnityAdsShowComplete(
                            placementId: String?,
                            state: UnityAds.UnityAdsShowCompletionState?
                        ) {
                            interstitialUnityAdRepository.isInterstitialUnityAdLoad = false
                        }
                    })
            }
    }

    fun initInterstitialAd(interstitialAdCallback: () -> Unit) {
        if (preferenceManager.getDisableAdvertising() <= Date().time && !(preferenceManager.getIsSubscription())) {
            interstitialUnityAdRepository.loadInterstitial()

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