package robin.vitalij.fortniteassitant.ui.comparison.selected

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.core.content.ContextCompat
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.common.extensions.replaceFragment
import robin.vitalij.fortniteassitant.common.extensions.setToolbarTitle
import robin.vitalij.fortniteassitant.ui.common.BaseActivity

class ComparisonSelectedActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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