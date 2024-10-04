package robin.vitalij.fortniteassitant.ui.search.fortnite

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import robin.vitalij.fortniteassitant.FortniteApplication
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.common.extensions.closeKeyboard
import robin.vitalij.fortniteassitant.common.extensions.observeToEmpty
import robin.vitalij.fortniteassitant.common.extensions.observeToError
import robin.vitalij.fortniteassitant.common.extensions.observeToProgressBar
import robin.vitalij.fortniteassitant.common.extensions.setErrorView
import robin.vitalij.fortniteassitant.common.extensions.setSafeOnClickListener
import robin.vitalij.fortniteassitant.common.extensions.setVisibility
import robin.vitalij.fortniteassitant.databinding.FragmentSearchUserBinding
import robin.vitalij.fortniteassitant.interfaces.ErrorController
import robin.vitalij.fortniteassitant.interfaces.InputAccountIdCallback
import robin.vitalij.fortniteassitant.interfaces.ProgressBarController
import robin.vitalij.fortniteassitant.interfaces.RegistrationProfileCallback
import robin.vitalij.fortniteassitant.model.ErrorModel
import robin.vitalij.fortniteassitant.model.enums.AvatarType
import robin.vitalij.fortniteassitant.model.enums.FirebaseDynamicLinkType
import robin.vitalij.fortniteassitant.model.enums.ProfileResultType
import robin.vitalij.fortniteassitant.model.network.search.SearchSteamUser
import robin.vitalij.fortniteassitant.model.network.stats.FortniteProfileResponse
import robin.vitalij.fortniteassitant.ui.bottomsheet.contactus.ContactUsResultFragment
import robin.vitalij.fortniteassitant.ui.bottomsheet.input_account_id.InputAccountIdResultFragment
import robin.vitalij.fortniteassitant.ui.bottomsheet.profile.ProfileResultFragment
import robin.vitalij.fortniteassitant.ui.main.MainActivity
import robin.vitalij.fortniteassitant.ui.search.SearchUserViewModel
import robin.vitalij.fortniteassitant.ui.search.SearchUserViewModelFactory
import robin.vitalij.fortniteassitant.ui.search.adapter.SearchAdapter
import java.util.Date
import javax.inject.Inject

class SearchUserFragment : Fragment(R.layout.fragment_search_user), ProgressBarController,
    ErrorController {

    @Inject
    lateinit var viewModelFactory: SearchUserViewModelFactory

    private lateinit var viewModel: SearchUserViewModel

    private val binding by viewBinding(FragmentSearchUserBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FortniteApplication.appComponent.inject(this)
        viewModel = ViewModelProvider(
            viewModelStore,
            viewModelFactory
        )[SearchUserViewModel::class.java].apply {
            observeToProgressBar(this@SearchUserFragment)
            observeToError(this@SearchUserFragment)
            observeToEmpty(this@SearchUserFragment)
            observeToProgressBar(
                this@SearchUserFragment,
                activity = activity as AppCompatActivity
            )

            openMainScreen = {
                activity?.finish()
                val intent = Intent(requireContext(), MainActivity::class.java)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }

            openFirebaseDynamicLink = { firebaseDynamicLinkType: FirebaseDynamicLinkType,
                                        id: String ->
                when (firebaseDynamicLinkType) {
                    FirebaseDynamicLinkType.USER -> {
                        ProfileResultFragment.show(
                            childFragmentManager,
                            id,
                            AvatarType.values().random().getImageUrl(),
                            ProfileResultType.FULL,
                            object : RegistrationProfileCallback {
                                override fun addedProfile(fortniteProfileResponse: FortniteProfileResponse) {
                                    saveUser(fortniteProfileResponse)
                                }

                            })

                        viewModel.clearFirebaseDynamicLink()
                    }
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.mutableLiveData.observe(viewLifecycleOwner) {
            it?.let(::initAdapter)
        }

        arguments?.let {
            if (it.getSerializable(ARG_PROFILE_RESULT_TYPE) as ProfileResultType == ProfileResultType.FULL) {
                setNavigation()
            } else {
                binding.toolbarInclude.toolbar.title = getString(R.string.search_player)
            }
        }

        initBanner()
        setListeners()

        viewModel.checkFirebaseDynamicLink()
    }

    private fun initBanner() {
        var profileResultType: ProfileResultType = ProfileResultType.NEW
        arguments?.let {
            profileResultType = it.getSerializable(ARG_PROFILE_RESULT_TYPE) as ProfileResultType
        }

        if (viewModel.preferenceManager.getIsSubscription() || viewModel.preferenceManager.getDisableAdvertising() >= Date().time
            || profileResultType == ProfileResultType.FULL
        ) {
            binding.customBannerView.setVisibility(false)
        } else {
            binding.customBannerView.setVisibility(true)
            binding.customBannerView.startBanner(getString(R.string.BANNER_ID), activity)
        }
    }

    private fun setNavigation() {
        val navController = findNavController()
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        binding.toolbarInclude.toolbar.setupWithNavController(navController, appBarConfiguration)
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setListeners() {
        binding.recyclerViewInclude.recyclerView.setOnTouchListener { v, event ->
            context.closeKeyboard(view)
            false
        }

        binding.searchButton.setSafeOnClickListener {
            context?.closeKeyboard(view)
            if (binding.searchInputEditText.text.toString().isEmpty()) {
                viewModel.mutableLiveData.value = emptyList()
            }
            if (binding.searchInputEditText.text.toString().length >= resources.getInteger(R.integer.min_length)) {
                viewModel.searchPlayer(binding.searchInputEditText.text.toString())
            }
        }

        binding.strictUserSwitch.setOnCheckedChangeListener { it, isChecked ->
            viewModel.strict = !isChecked
        }

        binding.iKnowMyAccountIdButton.setOnClickListener {
            InputAccountIdResultFragment.show(
                childFragmentManager,
                object : InputAccountIdCallback {
                    override fun sendAccountId(accountId: String) {
                        arguments?.let { bundle ->
                            context.closeKeyboard(view)
                            ProfileResultFragment.show(
                                childFragmentManager,
                                accountId,
                                AvatarType.values().random().getImageUrl(),
                                bundle.getSerializable(ARG_PROFILE_RESULT_TYPE) as ProfileResultType,
                                object : RegistrationProfileCallback {
                                    override fun addedProfile(fortniteProfileResponse: FortniteProfileResponse) {
                                        viewModel.textActivityVisibility.set(getString(R.string.save_the_user))
                                        viewModel.saveUser(fortniteProfileResponse)
                                    }
                                })
                        }
                    }
                })
        }

        binding.howToFindAccountIdButton.setOnClickListener {
            ContactUsResultFragment.show(childFragmentManager, false)
        }
    }

    private fun initAdapter(list: List<SearchSteamUser>) {
        binding.recyclerViewInclude.recyclerView.run {
            arguments?.let { bundle ->
                adapter = SearchAdapter { it ->
                    context.closeKeyboard(view)
                    ProfileResultFragment.show(
                        childFragmentManager,
                        it.accountId,
                        it.avatarImage,
                        bundle.getSerializable(ARG_PROFILE_RESULT_TYPE) as ProfileResultType,
                        object : RegistrationProfileCallback {
                            override fun addedProfile(fortniteProfileResponse: FortniteProfileResponse) {
                                viewModel.textActivityVisibility.set(getString(R.string.save_the_user))
                                viewModel.saveUser(fortniteProfileResponse)
                            }
                        })
                }
            }
            layoutManager = LinearLayoutManager(context)
            (adapter as SearchAdapter).setData(list)
        }
    }

    override fun setError(errorModel: ErrorModel) {
        binding.viewErrorInclude.setErrorView(errorModel)
    }

    override fun hideError() {
        binding.viewErrorInclude.errorView.isVisible = false
    }

    override fun showOrHideProgressBar(show: Boolean) {
        binding.progressViewInclude.progressContainer.isVisible = show
    }


    companion object {
        const val ARG_PROFILE_RESULT_TYPE = "arg_profile_result_type"

        fun newInstance(profileResultType: ProfileResultType) = SearchUserFragment().apply {
            arguments = bundleOf(ARG_PROFILE_RESULT_TYPE to profileResultType)
        }
    }
}