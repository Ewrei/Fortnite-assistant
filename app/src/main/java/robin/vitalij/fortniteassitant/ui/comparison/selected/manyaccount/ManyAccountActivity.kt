package robin.vitalij.fortniteassitant.ui.comparison.selected.manyaccount

import android.content.Context
import android.content.Intent
import android.os.Bundle
import by.kirich1409.viewbindingdelegate.viewBinding
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.common.extensions.replaceFragment
import robin.vitalij.fortniteassitant.common.extensions.setToolbarTitle
import robin.vitalij.fortniteassitant.databinding.ActivityBaseBinding
import robin.vitalij.fortniteassitant.ui.common.BaseActivity
import robin.vitalij.fortniteassitant.ui.comparison.selected.manyaccount.viewpager.AdapterManyAccountFragment

class ManyAccountActivity : BaseActivity(R.layout.activity_base) {

    private val binding by viewBinding(ActivityBaseBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setToolbar()
        setFragment()
    }

    private fun setToolbar() {
        setToolbarTitle(getString(R.string.player_omparison))
        enableBackButton()
    }

    private fun setFragment() {
        val tabFragment = AdapterManyAccountFragment.newInstance()
        replaceFragment(R.id.container, tabFragment, tabFragment.javaClass.simpleName)
    }

    companion object {
        fun getComparisonActivityIntent(
            context: Context?
        ): Intent {
            return Intent(context, ManyAccountActivity::class.java)
        }
    }
}