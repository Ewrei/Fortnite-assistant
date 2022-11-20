package robin.vitalij.fortniteassitant.ui.setting

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.firebase.dynamiclinks.ktx.dynamicLinks
import com.google.firebase.dynamiclinks.ktx.shortLinkAsync
import com.google.firebase.ktx.Firebase
import com.kaopiz.kprogresshud.KProgressHUD
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import robin.vitalij.fortniteassitant.FortniteApplication
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.common.binding.ImageViewBinging.loadImage
import robin.vitalij.fortniteassitant.common.extensions.setErrorView
import robin.vitalij.fortniteassitant.common.extensions.showDialog
import robin.vitalij.fortniteassitant.databinding.FragmentSettingBinding
import robin.vitalij.fortniteassitant.db.entity.UserEntity
import robin.vitalij.fortniteassitant.model.ErrorModelListItem
import robin.vitalij.fortniteassitant.model.LoadingState
import robin.vitalij.fortniteassitant.model.enums.ProfileResultType
import robin.vitalij.fortniteassitant.ui.bottomsheet.contactus.ContactUsResultFragment
import robin.vitalij.fortniteassitant.ui.comparison.selected.ComparisonSelectedActivity
import robin.vitalij.fortniteassitant.ui.search.fortnite.SearchUserFragment.Companion.ARG_PROFILE_RESULT_TYPE
import javax.inject.Inject

class SettingFragment : Fragment(R.layout.fragment_setting) {

    @Inject
    lateinit var viewModelFactory: SettingViewModelFactory

    private val viewModel: SettingViewModel by viewModels { viewModelFactory }

    private val binding by viewBinding(FragmentSettingBinding::bind)

    private val spinner: KProgressHUD by lazy {
        KProgressHUD.create(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.apply {
            openDialogError = {
                context?.showDialog(it)
            }
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

        lifecycleScope.launch {
            viewModel.userResult.collect {
                handleUserResult(it)
            }
        }

        viewModel.loadData()
    }

    private fun handleUserResult(result: LoadingState<UserEntity>) {
        when (result) {
            is LoadingState.Loading -> {
                binding.progressViewInclude.progressContainer.isVisible = true
                binding.errorViewInclude.errorView.isVisible = false
            }
            is LoadingState.Success -> {
                binding.progressViewInclude.progressContainer.isVisible = false
                binding.profileImage.loadImage(result.data.avatar)
                binding.userName.text = result.data.name
                binding.lastUpdate.text =
                    getString(R.string.last_date_update, result.data.getLastUpdate())
            }
            is LoadingState.Error -> {
                binding.progressViewInclude.progressContainer.isVisible = false
                if (result.cause is ErrorModelListItem.ErrorItem) {
                    binding.errorViewInclude.setErrorView(result.cause.errorModel)
                }
            }
        }
    }

    private fun initToolbar() {
        binding.toolbarInclude.toolbar.title = getString(R.string.title_setting)
    }

    private fun initMenuItem() {
        binding.toolbarInclude.toolbar.inflateMenu(R.menu.menu_setting)
        val item: MenuItem = binding.toolbarInclude.toolbar.menu.findItem(R.id.action_wikipedia)
        item.setOnMenuItemClickListener {
            findNavController().navigate(R.id.navigation_wiki)
            true
        }
    }

    private fun setListeners() {
        val navController = findNavController()

        binding.accountsLayout.setOnClickListener {
            navController.navigate(R.id.navigation_users)
        }

        binding.searchLayout.setOnClickListener {
            navController.navigate(
                R.id.navigation_search,
                bundleOf(ARG_PROFILE_RESULT_TYPE to ProfileResultType.FULL)
            )
        }

        binding.comparisonLayout.setOnClickListener {
            context?.startActivity(Intent(context, ComparisonSelectedActivity::class.java))
        }

        binding.applicationInfo.setOnClickListener {
            navController.navigate(R.id.navigation_application_info)
        }

        binding.newsLayout.setOnClickListener {
            navController.navigate(R.id.navigation_news)
        }

        binding.newsLayout.setOnClickListener {
            navController.navigate(R.id.navigation_news)
        }

        binding.crewLayout.setOnClickListener {
            navController.navigate(R.id.navigation_game_crew)
        }

        binding.subscriptionLayout.setOnClickListener {
            startActivity(Intent(context, ComparisonSelectedActivity::class.java))
        }

        binding.shareApp.setOnClickListener {
            shareApp()
        }

        binding.ratePlayMarket.setOnClickListener {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(context?.getString(R.string.application_url))
                )
            )
        }

        binding.contactUs.setOnClickListener {
            ContactUsResultFragment.show(childFragmentManager, true)
        }

        binding.copyStatsLink.setOnClickListener {
            copyStatsLink()
        }

        binding.refresh.setOnClickListener {
            viewModel.update()
        }

        binding.wikiLayout.setOnClickListener {
            findNavController().navigate(R.id.navigation_wiki)
        }

        binding.errorViewInclude.errorResolveButton.setOnClickListener {
            viewModel.loadData()
        }
    }

    private fun shareApp() {
        val shareActionText =
            "${resources.getString(R.string.app_name)}\n${resources.getString(R.string.application_url)}"
        val sendIntent = Intent()
        sendIntent.action = Intent.ACTION_SEND
        sendIntent.putExtra(Intent.EXTRA_TEXT, shareActionText)
        sendIntent.type = "text/plain"
        startActivity(Intent.createChooser(sendIntent, getString(R.string.share_title)))
    }

    private fun copyStatsLink() {
        Firebase.dynamicLinks.shortLinkAsync {
            longLink = Uri.parse(
                "https://fortniteassitant.page.link/?link=" +
                        "https://fastfly-7bcba.firebaseapp.com/user/${viewModel.getPlayerId()}/&apn=robin.vitalij.fortniteassitant"
            )
        }.addOnSuccessListener { shortLink ->
            val shareActionText =
                "${resources.getString(R.string.full_app_name)}\n${
                    String.format(
                        getString(R.string.you_can_see_the_profile_format),
                        viewModel.user.get()?.name
                    )
                }\n${shortLink.shortLink}"
            startActivity(Intent.createChooser(Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, shareActionText)
                type = "text/plain"
            }, getString(R.string.share_user_title)))
        }.addOnFailureListener {
            context?.showDialog(R.string.no_function)
        }
    }

}