package robin.vitalij.fortniteassitant.utils.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.core.view.isVisible
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
        binding.countTextView.isVisible = false
    }

    fun setFilterSize(size: Int?) {
        binding.countTextView.text = size.toString()
        if (size == null || size == EMPTY) {
            binding.countTextView.isVisible = false
        } else {
            binding.countTextView.isVisible = true
        }
    }
}