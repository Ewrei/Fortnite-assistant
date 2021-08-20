package robin.vitalij.fortniteassitant.ui.search

import android.os.Bundle
import kotlinx.android.synthetic.main.loading_layout.*
import robin.vitalij.fortniteassitant.common.extensions.setVisibility
import robin.vitalij.fortniteassitant.interfaces.ProgressBarActivityController
import robin.vitalij.fortniteassitant.model.enums.ProfileResultType
import robin.vitalij.fortniteassitant.ui.common.BaseActivity
import robin.vitalij.fortniteassitant.ui.search.fortnite.SearchUserFragment

class SearchActivity : BaseActivity(), ProgressBarActivityController {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        replaceFragment(SearchUserFragment.newInstance(ProfileResultType.NEW))
        loading_container.setVisibility(false)
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStackImmediate()
        } else {
            finish()
        }
    }

    override fun showOrHideProgressBar(show: Boolean, title: String) {
        loading_container.setVisibility(show)
        loadTitle.setVisibility(title.isNotBlank())
        loadTitle.text = title
    }
}