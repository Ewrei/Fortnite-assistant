package robin.vitalij.fortniteassitant.ui.comparison.selected.manyaccount

import android.content.Context
import android.content.Intent
import android.os.Bundle
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.common.extensions.replaceFragment
import robin.vitalij.fortniteassitant.common.extensions.setToolbarTitle
import robin.vitalij.fortniteassitant.ui.common.BaseActivity
import robin.vitalij.fortniteassitant.ui.comparison.selected.manyaccount.viewpager.AdapterManyAccountFragment

class ManyAccountActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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