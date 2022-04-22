package robin.vitalij.fortniteassitant.repository.unity

import android.util.Log
import com.unity3d.ads.IUnityAdsLoadListener
import com.unity3d.ads.UnityAds
import com.unity3d.ads.UnityAds.UnityAdsLoadError
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class RewardAdUnityRepository @Inject constructor() {

    var isLoadAds: Boolean = false

    fun load() {
        UnityAds.load(PLACEMENT_ID, loadListener);
    }

    private val loadListener: IUnityAdsLoadListener = object : IUnityAdsLoadListener {
        override fun onUnityAdsAdLoaded(placementId: String) {
            isLoadAds = true

            Log.e(
                "UnityAdsExample",
                "onUnityAdsAdLoaded"
            )
        }

        override fun onUnityAdsFailedToLoad(
            placementId: String,
            error: UnityAdsLoadError,
            message: String
        ) {
            Log.e(
                "UnityAdsExample",
                "Unity Ads failed to load ad for $placementId with error: [$error] $message"
            )
        }
    }

 companion object {
     const val PLACEMENT_ID = "Rewarded_Android"
 }
}