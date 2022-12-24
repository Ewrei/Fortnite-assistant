package robin.vitalij.fortniteassitant.ui.comparison.selected

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import robin.vitalij.fortniteassitant.FortniteApplication
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.common.extensions.*
import robin.vitalij.fortniteassitant.databinding.FragmentComparionBinding
import robin.vitalij.fortniteassitant.interfaces.RegistrationProfileCallback
import robin.vitalij.fortniteassitant.model.ErrorModelListItem
import robin.vitalij.fortniteassitant.model.LoadingState
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

    private val binding by viewBinding(FragmentComparionBinding::bind)

    private val searchAdapter = SearchAdapter() {
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
        setListeners()
        initBanner()
        initializeRecyclerView()

        lifecycleScope.launch {
            viewModel.seasonsResult.collect {
                handleBattlesPassRewardsResult(it)
            }
        }

        viewModel.mutableSizeLiveData.observe(viewLifecycleOwner) {
            selectedComparisonImageView?.setFilterSize(it)
        }
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
    }

    private fun initializeRecyclerView() {
        binding.recyclerViewInclude.recyclerView.run {
            adapter = searchAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun initBanner() {
        if (viewModel.preferenceManager.getIsSubscription() || viewModel.preferenceManager.getDisableAdvertising() >= Date().time) {
            binding.customBannerView.isVisible = false
        } else {
            binding.customBannerView.isVisible = true
            binding.customBannerView.startBanner(getString(R.string.BANNER_ID), activity)
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

        fun newInstance() = ComparisonSelectedFragment()
    }
}