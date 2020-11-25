package robin.vitalij.fortniteassitant.utils

import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd

object InterstitialAdAd {

    fun showInterstitial(mInterstitialAd: InterstitialAd?) {
        if (mInterstitialAd != null && mInterstitialAd.isLoaded) {
            mInterstitialAd.show()
        } else {
            startGame(
                mInterstitialAd!!
            )
        }
    }

    fun startGame(mInterstitialAd: InterstitialAd) {
        if (!mInterstitialAd.isLoading && !mInterstitialAd.isLoaded) {
            val adRequest = AdRequest.Builder().build()
            mInterstitialAd.loadAd(adRequest)
        }
    }
}