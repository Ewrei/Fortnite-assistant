package robin.vitalij.fortniteassitant.ui.comparison.selected.listuser

import android.content.Context
import android.content.Intent
import android.os.Bundle
import by.kirich1409.viewbindingdelegate.viewBinding
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.common.extensions.replaceFragment
import robin.vitalij.fortniteassitant.databinding.ActivityBaseBinding
import robin.vitalij.fortniteassitant.ui.common.BaseActivity

class SelectedListUserActivity : BaseActivity(R.layout.activity_base) {

    private val binding by viewBinding(ActivityBaseBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        enableBackButton()
        setFragment()
    }

    private fun setFragment() {
        val tabFragment = SelectedListUserFragment.newInstance()
        replaceFragment(R.id.container, tabFragment, tabFragment.javaClass.simpleName)
    }

    companion object {
        fun getSelectedListUser(
            context: Context
        ): Intent = Intent(context, SelectedListUserActivity::class.java)
    }
}