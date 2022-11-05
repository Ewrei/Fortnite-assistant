package robin.vitalij.fortniteassitant.ui.home

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import robin.vitalij.fortniteassitant.FortniteApplication
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.common.extensions.setErrorView
import robin.vitalij.fortniteassitant.databinding.FragmentHomeBinding
import robin.vitalij.fortniteassitant.model.DetailStatisticsModel
import robin.vitalij.fortniteassitant.model.ErrorModelListItem
import robin.vitalij.fortniteassitant.model.FullHomeModel
import robin.vitalij.fortniteassitant.model.LoadingState
import robin.vitalij.fortniteassitant.model.actions.HomeActions
import robin.vitalij.fortniteassitant.ui.details.viewpager.AdapterDetailsStatisticsFragment.Companion.ARG_DETAIL_STATISTICS
import robin.vitalij.fortniteassitant.ui.home.adapter.HomeAdapter
import robin.vitalij.fortniteassitant.ui.session.viewpager.AdapterSessionFragment
import javax.inject.Inject

class HomeFragment : Fragment(R.layout.fragment_home) {

    @Inject
    lateinit var viewModelFactory: HomeViewModelFactory

    private val viewModel: HomeViewModel by viewModels { viewModelFactory }

    private val binding by viewBinding(FragmentHomeBinding::bind)

    private var homeAdapter = HomeAdapter(::onHomeActions)

    override fun onAttach(context: Context) {
        super.onAttach(context)
        FortniteApplication.appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListener()
        initializeRecyclerView()

        viewModel.loadData()

        lifecycleScope.launch {
            viewModel.detailsStatisticsResult.collect {
                handleDetailsStatisticsResult(it)
            }
        }

        viewModel.loadData()
    }

    private fun initializeRecyclerView() {
        binding.recyclerViewInclude.recyclerView.run {
            adapter = homeAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun setListener() {
        binding.errorViewInclude.errorResolveButton.setOnClickListener {
            viewModel.loadData()
        }
    }

    private fun handleDetailsStatisticsResult(result: LoadingState<FullHomeModel>) {
        when (result) {
            is LoadingState.Loading -> {
                binding.progressViewInclude.progressContainer.isVisible = true
                binding.errorViewInclude.errorView.isVisible = false
            }
            is LoadingState.Success -> {
                binding.progressViewInclude.progressContainer.isVisible = false
                homeAdapter.updateData(result.data.homes)
            }
            is LoadingState.Error -> {
                binding.progressViewInclude.progressContainer.isVisible = false

                if (result.cause is ErrorModelListItem.ErrorItem) {
                    binding.errorViewInclude.setErrorView(result.cause.errorModel)
                }
            }
        }
    }

    private fun onHomeActions(homeActions: HomeActions) {
        when (homeActions) {
            is HomeActions.OpenParameterList -> {
                findNavController().navigate(
                    R.id.navigation_charts_type,
                    bundleOf(ARG_DETAIL_STATISTICS to viewModel.detailsStatistics)
                )
            }
            is HomeActions.OpenDetailsStatistics -> {
                findNavController().navigate(
                    R.id.navigation_adapter_details_statistics,
                    bundleOf(ARG_DETAIL_STATISTICS to viewModel.detailsStatistics)
                )
            }
            is HomeActions.OpenSessions -> {
                findNavController().navigate(R.id.navigation_history)
            }
            is HomeActions.OpenSession -> {
                findNavController().navigate(
                    R.id.navigation_adapter_session, bundleOf(
                        AdapterSessionFragment.ARG_SESSION_ID to homeActions.sessionId,
                        AdapterSessionFragment.ARG_SESSION_LAST_ID to homeActions.sessionLast,
                        AdapterSessionFragment.ARG_DATE to homeActions.sessionDate,
                        ARG_DETAIL_STATISTICS to homeActions.detailsStats as ArrayList<DetailStatisticsModel>
                    )
                )
            }
            is HomeActions.OpenSeason -> {
                findNavController().navigate(R.id.navigation_adapter_details_season_statistics)
            }
        }
    }
}