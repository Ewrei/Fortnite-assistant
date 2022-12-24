package robin.vitalij.fortniteassitant.ui.search.fortnite

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import robin.vitalij.fortniteassitant.FortniteApplication
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.common.extensions.*
import robin.vitalij.fortniteassitant.databinding.FragmentSearchUserBinding
import robin.vitalij.fortniteassitant.interfaces.InputAccountIdCallback
import robin.vitalij.fortniteassitant.interfaces.RegistrationProfileCallback
import robin.vitalij.fortniteassitant.model.ErrorModelListItem
import robin.vitalij.fortniteassitant.model.LoadingState
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
import java.util.*
import javax.inject.Inject

class SearchUserFragment : Fragment(R.layout.fragment_search_user) {

    @Inject
    lateinit var viewModelFactory: SearchUserViewModelFactory

    private val viewModel: SearchUserViewModel by viewModels { viewModelFactory }

    private val binding by viewBinding(FragmentSearchUserBinding::bind)

    private val searchAdapter = SearchAdapter { it ->
        context.closeKeyboard(view)
        ProfileResultFragment.show(
            childFragmentManager,
            it.accountId,
            it.avatarImage,
            viewModel.profileResultType,
            object : RegistrationProfileCallback {
                override fun addedProfile(fortniteProfileResponse: FortniteProfileResponse) {
                    viewModel.textActivityVisibility.set(getString(R.string.save_the_user))
                    viewModel.saveUser(fortniteProfileResponse)
                }
            })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FortniteApplication.appComponent.inject(this)


        arguments?.let {
            viewModel.profileResultType =
                it.getSerializable(ARG_PROFILE_RESULT_TYPE) as ProfileResultType
        }

        viewModel.apply {
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
        initToolbar()
        initBanner()
        setListeners()
        initializeRecyclerView()

        binding.viewEmptyInclude.empty.setText(R.string.empty_search_user)

        viewModel.checkFirebaseDynamicLink()

        lifecycleScope.launch {
            viewModel.seasonsResult.collect {
                handleBattlesPassRewardsResult(it)
            }
        }
    }

    private fun initToolbar() {
        if (viewModel.profileResultType == ProfileResultType.FULL) {
            setNavigation()
        } else {
            binding.include.toolbar.title = getString(R.string.search_player)
        }
    }

    private fun initBanner() {
        if (viewModel.preferenceManager.getIsSubscription() || viewModel.preferenceManager.getDisableAdvertising() >= Date().time
            || viewModel.profileResultType == ProfileResultType.FULL
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
        binding.include.toolbar.setupWithNavController(navController, appBarConfiguration)
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
                viewModel.clearSearch()
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

    private fun initializeRecyclerView() {
        binding.recyclerViewInclude.recyclerView.run {
            adapter = searchAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun handleBattlesPassRewardsResult(result: LoadingState<List<SearchSteamUser>>) {
        when (result) {
            is LoadingState.Loading -> {
                binding.progressViewInclude.progressContainer.isVisible = true
                binding.errorViewInclude.errorView.isVisible = false
                binding.recyclerViewInclude.recyclerView.isVisible = false
            }
            is LoadingState.Success -> {
                binding.progressViewInclude.progressContainer.isVisible = false
                binding.recyclerViewInclude.recyclerView.isVisible = true

                searchAdapter.submitList(result.data)
                binding.viewEmptyInclude.emptyView.isVisible =
                    result.data.isEmpty() && binding.searchInputEditText.text.toString()
                        .isNotBlank()
            }
            is LoadingState.Error -> {
                binding.progressViewInclude.progressContainer.isVisible = false
                binding.recyclerViewInclude.recyclerView.isVisible = false

                if (result.cause is ErrorModelListItem.ErrorItem) {
                    binding.errorViewInclude.setErrorView(result.cause.errorModel)
                }
            }
        }
    }

    companion object {
        const val ARG_PROFILE_RESULT_TYPE = "arg_profile_result_type"

        fun newInstance(profileResultType: ProfileResultType) = SearchUserFragment().apply {
            arguments = bundleOf(ARG_PROFILE_RESULT_TYPE to profileResultType)
        }
    }
}