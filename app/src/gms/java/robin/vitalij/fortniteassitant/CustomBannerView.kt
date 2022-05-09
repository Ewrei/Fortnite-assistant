package robin.vitalij.fortniteassitant

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.LoadAdError
import com.unity3d.services.banners.BannerErrorInfo
import com.unity3d.services.banners.BannerView
import com.unity3d.services.banners.UnityBannerSize
import com.yandex.mobile.ads.banner.AdSize
import com.yandex.mobile.ads.banner.BannerAdEventListener
import com.yandex.mobile.ads.common.AdRequestError
import com.yandex.mobile.ads.common.ImpressionData
import robin.vitalij.fortniteassitant.common.extensions.checkIfNetworkAvailable
import robin.vitalij.fortniteassitant.common.extensions.setVisibility
import robin.vitalij.fortniteassitant.databinding.CustomBannerBinding

class CustomBannerView: LinearLayout {

    private var attrs: AttributeSet? = null
    private var styleAttr = 0

    private lateinit var binding: CustomBannerBinding

    private val adRequest: AdRequest = AdRequest.Builder().build()

    private var activity: Activity? = null

    private lateinit var bannerAdmodView: AdView
    private lateinit var bannerUnityView: BannerView

    constructor(context: Context) : super(context) {
        initView()
    }

    constructor(
        context: Context,
        attrs: AttributeSet?
    ) : super(context, attrs) {
        this.attrs = attrs
        initView()
    }

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr) {
        this.attrs = attrs
        styleAttr = defStyleAttr
        initView()
    }

    fun startBanner(adUnitId: String, activity: Activity?) {
        this.activity = activity
        initAdmodBanner(adUnitId)
    }

    private fun initView() {
        binding =
            CustomBannerBinding.inflate(context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater)
        addView(binding.root)

    }

    private fun initAdmodBanner(adUnitId: String) {
        if(!::bannerAdmodView.isInitialized) {
            bannerAdmodView = AdView(context)
            bannerAdmodView.adSize = com.google.android.gms.ads.AdSize.SMART_BANNER
            bannerAdmodView.adUnitId = adUnitId
            bannerAdmodView.loadAd(adRequest)
            bannerAdmodView.adListener = object : AdListener() {
                override fun onAdLoaded() {
                    binding.bannerAdmodAdsView?.setVisibility(true)
                    binding.bannerAdsView?.setVisibility(false)
                    binding.bannerYandexView?.setVisibility(false)
                }

                override fun onAdFailedToLoad(adError: LoadAdError) {
                    binding.bannerAdmodAdsView?.setVisibility(false)

                    if (context.checkIfNetworkAvailable()) {
                        initYandexAdsBanner()
                    } else {
                        bannerAdmodView.loadAd(adRequest)
                    }
                }

                override fun onAdOpened() {
                    // Code to be executed when an ad opens an overlay that
                    // covers the screen.
                }

                override fun onAdClicked() {
                    // Code to be executed when the user clicks on an ad.
                }

                override fun onAdClosed() {
                    // Code to be executed when the user is about to return
                    // to the app after tapping on an ad.
                }
            }

            binding.bannerAdmodAdsView?.addView(bannerAdmodView)
        } else {
            bannerAdmodView.loadAd(adRequest)
        }
    }

    private fun initYandexAdsBanner() {
        binding.bannerYandexView.apply {
            // Replace demo Ad Unit ID with actual Ad Unit ID
            setAdUnitId("R-M-1661871-1")
            setAdSize(AdSize.BANNER_320x50)
            gravity = Gravity.CENTER
            setBannerAdEventListener(BannerAdYandexAdsEventListener())
        }
        binding.bannerYandexView.loadAd(com.yandex.mobile.ads.common.AdRequest.Builder().build())
    }

    private fun initUnityAdsBanner() {
        if(!::bannerUnityView.isInitialized) {
            val bannerListener = object : BannerView.IListener {
                override fun onBannerLoaded(bannerAdView: BannerView?) {
                    binding.bannerAdsView?.removeView(bannerAdView)
                    binding.bannerAdsView?.addView(bannerAdView)
                    binding.bannerAdsView?.setVisibility(true)
                    binding.bannerAdmodAdsView?.setVisibility(false)
                    binding.bannerYandexView?.setVisibility(false)
                }

                override fun onBannerClick(bannerAdView: BannerView?) {
                    //do nothing
                }

                override fun onBannerFailedToLoad(
                    bannerAdView: BannerView?,
                    errorInfo: BannerErrorInfo?
                ) {
                    binding.bannerAdsView?.setVisibility(false)
                }

                override fun onBannerLeftApplication(bannerView: BannerView?) {
                    //do nothing
                }
            }

            bannerUnityView = BannerView(activity, "Banner_Android", UnityBannerSize(320, 50))
            bannerUnityView.listener = bannerListener
            bannerUnityView.load()
            binding.bannerAdsView?.addView(bannerUnityView)
        } else {
            bannerUnityView.load()
        }
    }

    private inner class BannerAdYandexAdsEventListener : BannerAdEventListener {

        override fun onAdLoaded() {
            binding.bannerYandexView?.setVisibility(true)
            binding.bannerAdsView?.setVisibility(false)
            binding.bannerAdmodAdsView?.setVisibility(false)
        }

        override fun onAdFailedToLoad(adRequestError: AdRequestError) {
            binding.bannerYandexView?.setVisibility(false)

            if (context.checkIfNetworkAvailable()) {
                initUnityAdsBanner()
            }
        }

        override fun onImpression(impressionData: ImpressionData?) {

        }

        override fun onAdClicked() {
        }

        override fun onLeftApplication() {
        }

        override fun onReturnedToApplication() {
        }
    }
}

