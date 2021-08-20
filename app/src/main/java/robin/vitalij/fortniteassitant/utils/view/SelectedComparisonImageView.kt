package robin.vitalij.fortniteassitant.utils.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.layout_comparison_selected.view.*
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.common.extensions.setVisibility

private const val EMPTY = 0

class SelectedComparisonImageView(context: Context?, attrs: AttributeSet?) :
    LinearLayout(context, attrs) {

    init {
        val view =
            LayoutInflater.from(context).inflate(R.layout.layout_comparison_selected, this, false)
        this.addView(view)

        countTextView.setVisibility(false)
    }

    fun setFilterSize(size: Int?) {
        countTextView.text = size.toString()
        if (size == null || size == EMPTY) {
            countTextView.setVisibility(false)
        } else {
            countTextView.setVisibility(true)
        }
    }
}