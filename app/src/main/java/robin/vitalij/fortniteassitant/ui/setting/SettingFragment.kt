package robin.vitalij.fortniteassitant.ui.setting

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_setting.*
import kotlinx.android.synthetic.main.toolbar_center_title.*
import robin.vitalij.fortniteassitant.FortniteApplication
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.common.extensions.observeToError
import robin.vitalij.fortniteassitant.common.extensions.observeToProgressBar
import robin.vitalij.fortniteassitant.common.extensions.showDialog
import robin.vitalij.fortniteassitant.databinding.FragmentSettingBinding
import robin.vitalij.fortniteassitant.model.enums.ProfileResultType
import robin.vitalij.fortniteassitant.ui.bottomsheet.contactus.ContactUsResultFragment
import robin.vitalij.fortniteassitant.ui.common.BaseFragment
import robin.vitalij.fortniteassitant.ui.comparison.selected.ComparisonSelectedActivity
import robin.vitalij.fortniteassitant.ui.search.fortnite.SearchUserFragment.Companion.IS_COMPARISON_VISIBLE
import robin.vitalij.fortniteassitant.ui.subscription.SubscriptionActivity
import javax.inject.Inject

class SettingFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: SettingViewModelFactory

    private lateinit var viewModel: SettingViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val dataBinding: FragmentSettingBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_setting,
            container,
            false
        )
        dataBinding.lifecycleOwner = this@SettingFragment
        dataBinding.viewModel = viewModel
        return dataBinding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(viewModelStore, viewModelFactory)
            .get(SettingViewModel::class.java).apply {
                observeToProgressBar(this@SettingFragment)
                observeToProgressBar(this@SettingFragment, activity = activity as AppCompatActivity)
                observeToError(this@SettingFragment)
            }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        FortniteApplication.appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
        initToolbar()
        initMenuItem()
    }

    private fun initToolbar() {
        toolbar.title = getString(R.string.title_setting)
    }

    private fun setListeners() {
        val navController = findNavController()

        accountsLayout.setOnClickListener {
            navController.navigate(R.id.navigation_users)
        }

        searchLayout.setOnClickListener {
            val bundle = Bundle().apply {
                putSerializable(IS_COMPARISON_VISIBLE, ProfileResultType.FULL)
            }
            navController.navigate(R.id.navigation_search, bundle)
        }

        comparisonLayout.setOnClickListener {
            val intent = Intent(context, ComparisonSelectedActivity::class.java)
            context?.startActivity(intent)
        }

        applicationInfo.setOnClickListener {
            navController.navigate(R.id.navigation_application_info)
        }

        subscriptionLayout.setOnClickListener {
            val intent = Intent(context, SubscriptionActivity::class.java)
            startActivity(intent)
        }

        shareApp.setOnClickListener {
            val shareActionText =
                "${resources.getString(R.string.app_name)}\n${resources.getString(R.string.application_url)}"
            val sendIntent = Intent()
            sendIntent.action = Intent.ACTION_SEND
            sendIntent.putExtra(Intent.EXTRA_TEXT, shareActionText)
            sendIntent.type = "text/plain"
            startActivity(Intent.createChooser(sendIntent, getString(R.string.share_title)))
        }

        ratePlayMarket.setOnClickListener {
            openUri(Uri.parse(context?.getString(R.string.application_url)))
        }

        contactUs.setOnClickListener {
            ContactUsResultFragment.show(childFragmentManager)
        }

        copyStatsLink.setOnClickListener {
            context?.showDialog(R.string.no_function)
        }

        refresh.setOnClickListener {
            viewModel.update()
        }

        wikiLayout.setOnClickListener {
            findNavController().navigate(R.id.navigation_wiki)
        }
    }

    private fun openUri(uri: Uri) {
        startActivity(Intent(Intent.ACTION_VIEW, uri))
    }

    private fun initMenuItem() {
        toolbar.inflateMenu(R.menu.menu_setting)
        val item: MenuItem = toolbar.menu.findItem(R.id.action_wikipedia)
        item.setOnMenuItemClickListener {
            findNavController().navigate(R.id.navigation_wiki)
            true
        }
    }

    companion object {

        fun newInstance() = SettingFragment()
    }
}