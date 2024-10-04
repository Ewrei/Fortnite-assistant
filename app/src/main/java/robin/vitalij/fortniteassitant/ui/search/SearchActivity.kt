package robin.vitalij.fortniteassitant.ui.search

import android.os.Bundle
import by.kirich1409.viewbindingdelegate.viewBinding
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.common.extensions.setVisibility
import robin.vitalij.fortniteassitant.databinding.ActivityBaseBinding
import robin.vitalij.fortniteassitant.interfaces.ProgressBarActivityController
import robin.vitalij.fortniteassitant.model.enums.ProfileResultType
import robin.vitalij.fortniteassitant.ui.common.BaseActivity
import robin.vitalij.fortniteassitant.ui.search.fortnite.SearchUserFragment

class SearchActivity : BaseActivity(R.layout.activity_base), ProgressBarActivityController {

    private val binding by viewBinding(ActivityBaseBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        replaceFragment(SearchUserFragment.newInstance(ProfileResultType.NEW))

        binding.progressViewInclude.loadingContainer.setVisibility(false)
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStackImmediate()
        } else {
            finish()
        }
    }

    override fun showOrHideProgressBar(show: Boolean, title: String) {
        binding.progressViewInclude.loadingContainer.setVisibility(show)
        binding.progressViewInclude.loadTitle.setVisibility(title.isNotBlank())
        binding.progressViewInclude.loadTitle.text = title
    }
}