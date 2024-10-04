package robin.vitalij.fortniteassitant.ui.comparison.selected

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.core.content.ContextCompat
import by.kirich1409.viewbindingdelegate.viewBinding
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.common.extensions.replaceFragment
import robin.vitalij.fortniteassitant.common.extensions.setToolbarTitle
import robin.vitalij.fortniteassitant.databinding.ActivityBaseBinding
import robin.vitalij.fortniteassitant.ui.common.BaseActivity

class ComparisonSelectedActivity : BaseActivity(R.layout.activity_base) {

    private val binding by viewBinding(ActivityBaseBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setToolbar()
        setFragment()
    }

    private fun setToolbar() {
        setToolbarTitle(getString(R.string.added_comparison))
        enableBackButton()
        supportActionBar?.setBackgroundDrawable(
            ColorDrawable(
                ContextCompat.getColor(
                    this,
                    R.color.colorPrimary
                )
            )
        )
    }

    private fun setFragment() {
        val comparisonSelectedFragment = ComparisonSelectedFragment.newInstance()
        replaceFragment(R.id.container, comparisonSelectedFragment, comparisonSelectedFragment.javaClass.simpleName)
    }
}