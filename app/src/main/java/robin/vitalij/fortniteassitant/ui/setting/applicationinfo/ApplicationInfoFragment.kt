package robin.vitalij.fortniteassitant.ui.setting.applicationinfo

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import robin.vitalij.fortniteassitant.BuildConfig
import robin.vitalij.fortniteassitant.FortniteApplication
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.databinding.FragmentApplicationInfoBinding
import robin.vitalij.fortniteassitant.ui.web.WebActivity
import javax.inject.Inject

class ApplicationInfoFragment : Fragment(R.layout.fragment_application_info) {

    @Inject
    lateinit var viewModelFactory: ApplicationInfoViewModelFactory

    private val viewModel: ApplicationInfoViewModel by viewModels { viewModelFactory }

    private val binding by viewBinding(FragmentApplicationInfoBinding::bind)

    override fun onAttach(context: Context) {
        super.onAttach(context)
        FortniteApplication.appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.version.text = getString(R.string.version_format, BuildConfig.VERSION_NAME)
        setNavigation()
        setListener()
    }

    private fun setNavigation() {
        val navController = findNavController()
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        binding.toolbarInclude.toolbar.setupWithNavController(navController, appBarConfiguration)
    }

    private fun setListener() {
        binding.privacyPolicy.setOnClickListener {
            startActivity(
                WebActivity.newInstance(
                    context,
                    getString(R.string.privacy_policy_url),
                    getString(R.string.privacy_policy)
                )
            )
        }
    }
}