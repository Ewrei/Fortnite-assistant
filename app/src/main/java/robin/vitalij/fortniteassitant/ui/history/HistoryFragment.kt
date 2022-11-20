package robin.vitalij.fortniteassitant.ui.history

import android.content.Context
import android.os.Bundle
import android.view.View
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
import robin.vitalij.fortniteassitant.common.extensions.setErrorView
import robin.vitalij.fortniteassitant.databinding.FragmentRecyclerViewWithToolbarBinding
import robin.vitalij.fortniteassitant.model.DetailStatisticsModel
import robin.vitalij.fortniteassitant.model.ErrorModelListItem
import robin.vitalij.fortniteassitant.model.HistoryUserModel
import robin.vitalij.fortniteassitant.model.LoadingState
import robin.vitalij.fortniteassitant.ui.details.viewpager.AdapterDetailsStatisticsFragment
import robin.vitalij.fortniteassitant.ui.history.adapter.HistoryAdapter
import robin.vitalij.fortniteassitant.ui.session.viewpager.AdapterSessionFragment.Companion.ARG_DATE
import robin.vitalij.fortniteassitant.ui.session.viewpager.AdapterSessionFragment.Companion.ARG_SESSION_ID
import robin.vitalij.fortniteassitant.ui.session.viewpager.AdapterSessionFragment.Companion.ARG_SESSION_LAST_ID
import javax.inject.Inject


class HistoryFragment : Fragment(R.layout.fragment_recycler_view_with_toolbar) {

    @Inject
    lateinit var viewModelFactory: HistoryViewModelFactory

    private val viewModel: HistoryViewModel by viewModels { viewModelFactory }

    private val binding by viewBinding(FragmentRecyclerViewWithToolbarBinding::bind)

    private val historyAdapter =
        HistoryAdapter { sessionId: Long, sessionLast: Long, sessionDate: String, detailsStats: List<DetailStatisticsModel> ->
            findNavController().navigate(
                R.id.navigation_adapter_session,
                bundleOf(
                    ARG_SESSION_ID to sessionId,
                    ARG_SESSION_LAST_ID to sessionLast,
                    ARG_DATE to sessionDate,
                    AdapterDetailsStatisticsFragment.ARG_DETAIL_STATISTICS to detailsStats as ArrayList<DetailStatisticsModel>
                )
            )
        }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        FortniteApplication.appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setNavigation()
        initializeRecyclerView()

        binding.viewEmptyInclude.empty.setText(R.string.empty_session)

        lifecycleScope.launch {
            viewModel.historiesResult.collect {
                handleHistoriesResult(it)
            }
        }

        viewModel.loadData()
    }

    private fun setNavigation() {
        val navController = findNavController()
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        binding.toolbarInclude.toolbar.setupWithNavController(navController, appBarConfiguration)
    }

    private fun initializeRecyclerView() {
        binding.recyclerViewInclude.recyclerView.run {
            adapter = historyAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun handleHistoriesResult(result: LoadingState<List<HistoryUserModel>>) {
        when (result) {
            is LoadingState.Loading -> {
                binding.progressViewInclude.progressContainer.isVisible = true
                binding.errorViewInclude.errorView.isVisible = false
            }
            is LoadingState.Success -> {
                binding.progressViewInclude.progressContainer.isVisible = false
                binding.viewEmptyInclude.emptyView.isVisible = result.data.isEmpty()
                historyAdapter.updateData(result.data)
            }
            is LoadingState.Error -> {
                binding.progressViewInclude.progressContainer.isVisible = false

                if (result.cause is ErrorModelListItem.ErrorItem) {
                    binding.errorViewInclude.setErrorView(result.cause.errorModel)
                }
            }
        }
    }

}