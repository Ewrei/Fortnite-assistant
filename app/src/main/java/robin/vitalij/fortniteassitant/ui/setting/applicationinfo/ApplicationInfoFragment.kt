package robin.vitalij.fortniteassitant.ui.setting.applicationinfo

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.fragment_application_info.*
import kotlinx.android.synthetic.main.toolbar_center_title.*
import robin.vitalij.fortniteassitant.BuildConfig
import robin.vitalij.fortniteassitant.FortniteApplication
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.databinding.FragmentApplicationInfoBinding
import robin.vitalij.fortniteassitant.ui.common.BaseFragment
import robin.vitalij.fortniteassitant.ui.web.WebActivity
import javax.inject.Inject

class ApplicationInfoFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ApplicationInfoViewModelFactory

    private lateinit var viewModel: ApplicationInfoViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val dataBinding: FragmentApplicationInfoBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_application_info,
            container,
            false
        )
        dataBinding.lifecycleOwner = this@ApplicationInfoFragment
        dataBinding.viewModel = viewModel
        return dataBinding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(viewModelStore, viewModelFactory)
            .get(ApplicationInfoViewModel::class.java)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        FortniteApplication.appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        version.text = getString(R.string.version_format, BuildConfig.VERSION_NAME)
        setNavigation()
        setListener()
    }

    private fun setNavigation() {
        val navController = findNavController()
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        toolbar.setupWithNavController(navController, appBarConfiguration)
    }

    private fun setListener() {
        privacyPolicy.setOnClickListener {
            startActivity(
                WebActivity.newInstance(
                    context,
                    getString(R.string.privacy_policy_url),
                    getString(R.string.privacy_policy)
                )
            )
        }
    }

    companion object {

        fun newInstance() = ApplicationInfoFragment()
    }
}