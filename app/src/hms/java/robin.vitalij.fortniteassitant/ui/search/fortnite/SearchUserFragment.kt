package robin.vitalij.fortniteassitant.ui.search.fortnite

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.huawei.hms.ads.AdListener
import com.huawei.hms.ads.AdParam
import com.huawei.hms.ads.BannerAdSize
import kotlinx.android.synthetic.hms.fragment_search_user.*
import kotlinx.android.synthetic.main.recycler_view.*
import kotlinx.android.synthetic.main.toolbar_center_title.*
import robin.vitalij.fortniteassitant.BuildConfig
import robin.vitalij.fortniteassitant.FortniteApplication
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.common.extensions.*
import robin.vitalij.fortniteassitant.interfaces.InputAccountIdCallback
import robin.vitalij.fortniteassitant.interfaces.RegistrationProfileCallback
import robin.vitalij.fortniteassitant.model.enums.AvatarType
import robin.vitalij.fortniteassitant.model.enums.ProfileResultType
import robin.vitalij.fortniteassitant.model.network.search.SearchSteamUser
import robin.vitalij.fortniteassitant.model.network.stats.FortniteProfileResponse
import robin.vitalij.fortniteassitant.ui.bottomsheet.input_account_id.InputAccountIdResultFragment
import robin.vitalij.fortniteassitant.ui.bottomsheet.profile.ProfileResultFragment
import robin.vitalij.fortniteassitant.ui.common.BaseFragment
import robin.vitalij.fortniteassitant.ui.main.MainActivity
import robin.vitalij.fortniteassitant.ui.search.SearchUserViewModel
import robin.vitalij.fortniteassitant.ui.search.SearchUserViewModelFactory
import robin.vitalij.fortniteassitant.ui.search.adapter.SearchAdapter
import robin.vitalij.fortniteassitant.ui.web.WebActivity
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
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.mutableLiveData.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            it?.let(::initAdapter)
        })

        arguments?.let {
            if (it.getSerializable(IS_COMPARISON_VISIBLE) as ProfileResultType == ProfileResultType.FULL) {
                setNavigation()
            } else {
                toolbar.title = getString(R.string.search_player)
            }
        }

        initBanner()
        setListeners()
    }

    private fun initBanner() {
        if (viewModel.preferenceManager.getIsSubscription() || viewModel.preferenceManager.getDisableAdvertising() >= Date().time) {
            adView.setVisibility(false)
        } else {
            val adParam = AdParam.Builder().build()
            adView.loadAd(adParam)
            adView.adId = BuildConfig.HUAWEI_BANNER_ID
            adView.bannerAdSize = BannerAdSize.BANNER_SIZE_SMART
            adView.loadAd(adParam)
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

        adView?.adListener = object : AdListener() {
            override fun onAdLoaded() {
                adView?.setVisibility(true)
            }

            override fun onAdFailed(adError: Int) {
                adView?.setVisibility(false)
            }

            override fun onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
            }

            override fun onAdClicked() {
                // Code to be executed when the user clicks on an ad.
            }

            override fun onAdClosed() {
                // Code to be executed when the user is about to return
                // to the app after tapping on an ad.
            }
        }

        searchButton.setSafeOnClickListener {
            context?.closeKeyboard(view)
            if (searchInputEditText.text.toString().isEmpty()) {
                viewModel.mutableLiveData.value = arrayListOf()
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
                                bundle.getSerializable(IS_COMPARISON_VISIBLE) as ProfileResultType,
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
                        bundle.getSerializable(IS_COMPARISON_VISIBLE) as ProfileResultType,
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
        const val IS_COMPARISON_VISIBLE = "is_comparison_visible"

        fun newInstance(profileResultType: ProfileResultType) = SearchUserFragment().apply {
            arguments = Bundle().apply {
                putSerializable(IS_COMPARISON_VISIBLE, profileResultType)
            }
        }
    }
}