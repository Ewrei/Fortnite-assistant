package robin.vitalij.fortniteassitant.common.extensions

import android.view.LayoutInflater
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import robin.vitalij.fortniteassitant.R

fun ChipGroup.initChip(tags: List<String>, layoutInflater: LayoutInflater) {
    removeAllViews()
    tags.forEach {
        addView(
            (layoutInflater.inflate(
                R.layout.layout_chip_choice,
                this,
                false
            ) as Chip).apply {
                text = it
            }
        )
    }
}
