package robin.vitalij.fortniteassitant.ui.comparison.selected

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import robin.vitalij.fortniteassitant.FortniteApplication
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.common.extensions.closeKeyboard
import robin.vitalij.fortniteassitant.common.extensions.observeToError
import robin.vitalij.fortniteassitant.common.extensions.observeToProgressBar
import robin.vitalij.fortniteassitant.common.extensions.setErrorView
import robin.vitalij.fortniteassitant.common.extensions.setSafeOnClickListener
import robin.vitalij.fortniteassitant.common.extensions.setVisibility
import robin.vitalij.fortniteassitant.databinding.FragmentComparionBinding
import robin.vitalij.fortniteassitant.interfaces.EmptyBarController
import robin.vitalij.fortniteassitant.interfaces.ErrorController
import robin.vitalij.fortniteassitant.interfaces.ProgressBarController
import robin.vitalij.fortniteassitant.interfaces.RegistrationProfileCallback
import robin.vitalij.fortniteassitant.model.EmptyTextModel
import robin.vitalij.fortniteassitant.model.ErrorModel
import robin.vitalij.fortniteassitant.model.enums.ProfileResultType
import robin.vitalij.fortniteassitant.model.network.search.SearchSteamUserModel
import robin.vitalij.fortniteassitant.model.network.stats.FortniteProfileResponse
import robin.vitalij.fortniteassitant.ui.bottomsheet.profile.ProfileResultFragment
import robin.vitalij.fortniteassitant.ui.comparison.selected.listuser.SelectedListUserActivity
import robin.vitalij.fortniteassitant.ui.main.MainActivity
import robin.vitalij.fortniteassitant.ui.search.adapter.SearchAdapter
import robin.vitalij.fortniteassitant.utils.view.SelectedComparisonImageView
import java.util.Date
import javax.inject.Inject

class ComparisonSelectedFragment : Fragment(R.layout.fragment_comparion), ErrorController,
    ProgressBarController, EmptyBarController {

    @Inject
    lateinit var viewModelFactory: ComparisonSelectedViewModelFactory

    private lateinit var viewModel: ComparisonSelectedViewModel

    private var selectedComparisonImageView: SelectedComparisonImageView? = null

    private val binding by viewBinding(FragmentComparionBinding::bind)

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
        viewModel.mutableLiveData.observe(viewLifecycleOwner) {
            it?.let(::initAdapter)
        }


        viewModel.mutableSizeLiveData.observe(viewLifecycleOwner) {
            selectedComparisonImageView?.setFilterSize(it)
        }

        setListeners()
        initBanner()
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

        binding.viewErrorInclude.errorResolveButton.setOnClickListener {
            viewModel.loadPlayerComparisonSize()
        }

    }

    private fun initAdapter(list: List<SearchSteamUserModel>) {
        binding.recyclerViewInclude.recyclerView.run {
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
            (adapter as SearchAdapter).submitList(list)
        }
    }

    private fun initBanner() {
        if (viewModel.preferenceManager.getIsSubscription() || viewModel.preferenceManager.getDisableAdvertising() >= Date().time) {
            binding.customBannerView.setVisibility(false)
        } else {
            binding.customBannerView.setVisibility(true)
            binding.customBannerView.startBanner(getString(R.string.BANNER_ID), activity)
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

    override fun setEmptyText(emptyTextModel: EmptyTextModel) {
        binding.viewEmptyInclude.emptyView.setVisibility(emptyTextModel.isVisibility)
        binding.viewEmptyInclude.empty.text = emptyTextModel.emptyText
    }

    companion object {

        fun newInstance() = ComparisonSelectedFragment()
    }
}