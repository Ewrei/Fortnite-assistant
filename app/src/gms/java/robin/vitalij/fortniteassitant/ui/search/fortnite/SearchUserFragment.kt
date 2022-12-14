package robin.vitalij.fortniteassitant.ui.search.fortnite

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.gms.fragment_search_user.*
import kotlinx.android.synthetic.gms.fragment_search_user.searchButton
import kotlinx.android.synthetic.gms.fragment_search_user.searchInputEditText
import kotlinx.android.synthetic.main.recycler_view.*
import kotlinx.android.synthetic.main.toolbar_center_title.*
import robin.vitalij.fortniteassitant.FortniteApplication
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.common.extensions.*
import robin.vitalij.fortniteassitant.interfaces.InputAccountIdCallback
import robin.vitalij.fortniteassitant.interfaces.RegistrationProfileCallback
import robin.vitalij.fortniteassitant.model.enums.AvatarType
import robin.vitalij.fortniteassitant.model.enums.FirebaseDynamicLinkType
import robin.vitalij.fortniteassitant.model.enums.ProfileResultType
import robin.vitalij.fortniteassitant.model.network.search.SearchSteamUser
import robin.vitalij.fortniteassitant.model.network.stats.FortniteProfileResponse
import robin.vitalij.fortniteassitant.ui.bottomsheet.contactus.ContactUsResultFragment
import robin.vitalij.fortniteassitant.ui.bottomsheet.input_account_id.InputAccountIdResultFragment
import robin.vitalij.fortniteassitant.ui.bottomsheet.profile.ProfileResultFragment
import robin.vitalij.fortniteassitant.ui.common.BaseFragment
import robin.vitalij.fortniteassitant.ui.main.MainActivity
import robin.vitalij.fortniteassitant.ui.search.SearchUserViewModel
import robin.vitalij.fortniteassitant.ui.search.SearchUserViewModelFactory
import robin.vitalij.fortniteassitant.ui.search.adapter.SearchAdapter
import java.util.*
import javax.inject.Inject

class SearchUserFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: SearchUserViewModelFactory

    private lateinit var viewModel: SearchUserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = inflater.inflate(R.layout.fragment_search_user, container, false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FortniteApplication.appComponent.inject(this)
        viewModel = ViewModelProvider(viewModelStore, viewModelFactory)
            .get(SearchUserViewModel::class.java).apply {
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
                toolbar.title = getString(R.string.search_player)
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
            customBannerView.setVisibility(false)
        } else {
            customBannerView.setVisibility(true)
            customBannerView.startBanner(getString(R.string.BANNER_ID), activity)
        }
    }

    private fun setNavigation() {
        val navController = findNavController()
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        toolbar.setupWithNavController(navController, appBarConfiguration)
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setListeners() {
        recyclerView.setOnTouchListener { v, event ->
            context.closeKeyboard(view)
            false
        }

        searchButton.setSafeOnClickListener {
            context?.closeKeyboard(view)
            if (searchInputEditText.text.toString().isEmpty()) {
                viewModel.mutableLiveData.value = emptyList()
            }
            if (searchInputEditText.text.toString().length >= resources.getInteger(R.integer.min_length)) {
                viewModel.searchPlayer(searchInputEditText.text.toString())
            }
        }

        strictUserSwitch.setOnCheckedChangeListener { it, isChecked ->
            viewModel.strict = !isChecked
        }

        iKnowMyAccountIdButton.setOnClickListener {
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

        howToFindAccountIdButton.setOnClickListener {
            ContactUsResultFragment.show(childFragmentManager, false)
        }
    }

    private fun initAdapter(list: List<SearchSteamUser>) {
        recyclerView.run {
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

    companion object {
        const val ARG_PROFILE_RESULT_TYPE = "arg_profile_result_type"

        fun newInstance(profileResultType: ProfileResultType) = SearchUserFragment().apply {
            arguments = bundleOf(ARG_PROFILE_RESULT_TYPE to profileResultType)
        }
    }
}