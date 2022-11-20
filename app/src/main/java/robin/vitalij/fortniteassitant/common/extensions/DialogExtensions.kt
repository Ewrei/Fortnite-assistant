package robin.vitalij.fortniteassitant.common.extensions

import android.app.Dialog
import android.view.View
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import robin.vitalij.fortniteassitant.R

fun Dialog?.initBottomSheetInternal() {
    this?.setOnShowListener { dialog ->
        val d = dialog as BottomSheetDialog
        val bottomSheetInternal =
            d.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
        bottomSheetInternal?.setBackgroundResource(R.drawable.bottomsheet_container_background)
        bottomSheetInternal?.let {
            BottomSheetBehavior.from(it).state = BottomSheetBehavior.STATE_EXPANDED
            BottomSheetBehavior.from(it).skipCollapsed = true
        }
    }
}