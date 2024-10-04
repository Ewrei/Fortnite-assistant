package robin.vitalij.fortniteassitant.utils.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import robin.vitalij.fortniteassitant.common.extensions.setVisibility
import robin.vitalij.fortniteassitant.databinding.LayoutComparisonSelectedBinding

private const val EMPTY = 0

class SelectedComparisonImageView(context: Context?, attrs: AttributeSet?) :
    LinearLayout(context, attrs) {

    private var binding: LayoutComparisonSelectedBinding = LayoutComparisonSelectedBinding.inflate(
        LayoutInflater.from(context),
        this,
        false
    )

    init {
        this.addView(binding.root)
        binding.countTextView.setVisibility(false)
    }

    fun setFilterSize(size: Int?) {
        binding.countTextView.text = size.toString()
        if (size == null || size == EMPTY) {
            binding.countTextView.setVisibility(false)
        } else {
            binding.countTextView.setVisibility(true)
        }
    }
}