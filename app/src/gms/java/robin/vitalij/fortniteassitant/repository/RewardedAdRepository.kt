package robin.vitalij.fortniteassitant.repository

import android.app.Activity
import android.content.Context
import android.util.Log
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import com.unity3d.ads.IUnityAdsShowListener
import com.unity3d.ads.UnityAds
import com.unity3d.ads.UnityAdsShowOptions
import robin.vitalij.fortniteassitant.BuildConfig
import robin.vitalij.fortniteassitant.repository.unity.RewardAdUnityRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RewardedAdRepository @Inject constructor(
    private val context: Context,
    private val rewardAdUnityRepository: RewardAdUnityRepository
) {

    private var defaultRewardedAd: RewardedAd? = null

    init {
        loadAdmodReward()
        rewardAdUnityRepository.load()
    }

    fun isLoadVideo() = defaultRewardedAd != null || rewardAdUnityRepository.isLoadAds

    fun showReward(activity: Activity, getAnWard: () -> Unit) {
        if(defaultRewardedAd != null) {
            defaultRewardedAd?.show(activity) {
                defaultRewardedAd = null
                loadAdmodReward()
                getAnWard()
            }
        } else
            if(rewardAdUnityRepository.isLoadAds) {
                loadRewardUnityAds(activity, getAnWard)
            }
    }

    fun loadReward() {
        loadAdmodReward()
        rewardAdUnityRepository.load()
    }

    private fun loadAdmodReward() {
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

    private fun loadRewardUnityAds(activity: Activity, getAnWard:() -> Unit) {
        if (rewardAdUnityRepository.isLoadAds) {
            UnityAds.show(
                activity,
                RewardAdUnityRepository.PLACEMENT_ID,
                UnityAdsShowOptions(),
                object : IUnityAdsShowListener {
                    override fun onUnityAdsShowFailure(
                        placementId: String,
                        error: UnityAds.UnityAdsShowError,
                        message: String
                    ) {
                        Log.e(
                            "UnityAdsExample",
                            "Unity Ads failed to show ad for $placementId with error: [$error] $message"
                        )
                    }

                    override fun onUnityAdsShowStart(placementId: String) {
                        Log.v("UnityAdsExample", "onUnityAdsShowStart: $placementId")
                    }

                    override fun onUnityAdsShowClick(placementId: String) {
                        Log.v("UnityAdsExample", "onUnityAdsShowClick: $placementId")
                    }

                    override fun onUnityAdsShowComplete(
                        placementId: String,
                        state: UnityAds.UnityAdsShowCompletionState
                    ) {
                        rewardAdUnityRepository.isLoadAds = false
                        rewardAdUnityRepository.load()
                        getAnWard()

                        Log.v("UnityAdsExample", "onUnityAdsShowComplete: $placementId")

                        if (state == UnityAds.UnityAdsShowCompletionState.COMPLETED) {
                            // Reward the user for watching the ad to completion
                        } else {
                            // Do not reward the user for skipping the ad
                        }
                    }
                })
        }
    }
}