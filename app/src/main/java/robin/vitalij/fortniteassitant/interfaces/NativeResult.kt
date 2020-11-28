package robin.vitalij.fortniteassitant.interfaces

import com.google.android.gms.ads.formats.UnifiedNativeAd

interface NativeResult {
    fun onSusses(data: List<UnifiedNativeAd>)
}