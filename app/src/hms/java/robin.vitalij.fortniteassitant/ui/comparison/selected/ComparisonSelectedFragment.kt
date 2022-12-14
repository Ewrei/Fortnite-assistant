package robin.vitalij.fortniteassitant.ui.comparison.selected

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.huawei.hms.ads.AdListener
import com.huawei.hms.ads.AdParam
import com.huawei.hms.ads.BannerAdSize
import kotlinx.android.synthetic.hms.fragment_comparion.*
import kotlinx.android.synthetic.hms.fragment_comparion.adView
import kotlinx.android.synthetic.main.recycler_view.*
import robin.vitalij.fortniteassitant.BuildConfig
import robin.vitalij.fortniteassitant.FortniteApplication
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.common.extensions.*
import robin.vitalij.fortniteassitant.interfaces.RegistrationProfileCallback
import robin.vitalij.fortniteassitant.model.enums.ProfileResultType
import robin.vitalij.fortniteassitant.model.network.search.SearchSteamUser
import robin.vitalij.fortniteassitant.model.network.stats.FortniteProfileResponse
import robin.vitalij.fortniteassitant.ui.bottomsheet.profile.ProfileResultFragment
import robin.vitalij.fortniteassitant.ui.common.BaseFragment
import robin.vitalij.fortniteassitant.ui.comparison.selected.listuser.SelectedListUserActivity
import robin.vitalij.fortniteassitant.ui.main.MainActivity
import robin.vitalij.fortniteassitant.ui.search.adapter.SearchAdapter
import robin.vitalij.fortniteassitant.utils.view.SelectedComparisonImageView
import java.util.*
import javax.inject.Inject

class ComparisonSelectedFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ComparisonSelectedViewModelFactory

    private lateinit var viewModel: ComparisonSelectedViewModel

    private var selectedComparisonImageView: SelectedComparisonImageView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = inflater.inflate(R.layout.fragment_comparion, container, false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(viewModelStore, viewModelFactory)
            .get(ComparisonSelectedViewModel::class.java).apply {
                observeToProgressBar(this@ComparisonSelectedFragment)
                observeToError(this@ComparisonSelectedFragment)

                openMainScreen = {
                    activity?.finish()
                    val intent = Intent(requireContext(), MainActivity::class.java)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                }
            }
        viewModel.initOwner(this)

        setHasOptionsMenu(true)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        FortniteApplication.appComponent.inject(this)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_friends, menu)
        val filterItem = menu.findItem(R.id.action_list_user)
        filterItem?.let { item ->
            selectedComparisonImageView = item.actionView as SelectedComparisonImageView
            selectedComparisonImageView?.setOnClickListener {
                startActivity(SelectedListUserActivity.getSelectedListUser(requireContext()))
            }
            viewModel.loadPlayerComparisonSize()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.mutableLiveData.observe(viewLifecycleOwner, {
            it?.let(::initAdapter)
        })


        viewModel.mutableSizeLiveData.observe(viewLifecycleOwner, {
            selectedComparisonImageView?.setFilterSize(it)
        })

        setListeners()
        initBanner()

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

    @SuppressLint("ClickableViewAccessibility")
    private fun setListeners() {
        recyclerView.setOnTouchListener { v, event ->
            context.closeKeyboard(view)
            false
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
    }

    private fun initAdapter(list: List<SearchSteamUser>) {
        recyclerView.run {
            adapter = SearchAdapter() {
                ProfileResultFragment.show(
                    childFragmentManager,
                    it.accountId,
                    it.avatarImage,
                    ProfileResultType.FULL,
                    object : RegistrationProfileCallback {
                        override fun addedProfile(fortniteProfileResponse: FortniteProfileResponse) {
                            viewModel.saveUser(fortniteProfileResponse)
                        }

                    })
            }
            layoutManager = LinearLayoutManager(context)
            (adapter as SearchAdapter).setData(list)
        }
    }

    companion object {

        fun newInstance() = ComparisonSelectedFragment()
    }
}