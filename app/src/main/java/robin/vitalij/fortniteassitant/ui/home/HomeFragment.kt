package robin.vitalij.fortniteassitant.ui.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import robin.vitalij.fortniteassitant.FortniteApplication
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.common.extensions.observeToError
import robin.vitalij.fortniteassitant.common.extensions.observeToProgressBar
import robin.vitalij.fortniteassitant.databinding.FragmentHomeBinding
import robin.vitalij.fortniteassitant.model.DetailStatisticsModel
import robin.vitalij.fortniteassitant.model.actions.HomeActions
import robin.vitalij.fortniteassitant.ui.common.BaseFragment
import robin.vitalij.fortniteassitant.ui.details.viewpager.AdapterDetailsStatisticsFragment.Companion.DETAIL_STATISTICS
import robin.vitalij.fortniteassitant.ui.home.adapter.HomeAdapter
import robin.vitalij.fortniteassitant.ui.session.viewpager.AdapterSessionFragment
import javax.inject.Inject

class HomeFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: HomeViewModelFactory

    private lateinit var viewModel: HomeViewModel

    private lateinit var binding: FragmentHomeBinding

    private var homeAdapter = HomeAdapter(::onHomeActions)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(viewModelStore, viewModelFactory)
            .get(HomeViewModel::class.java).apply {
                observeToProgressBar(this@HomeFragment)
                observeToError(this@HomeFragment)
            }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        FortniteApplication.appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListener()
        initializeRecyclerView()

        viewModel.mutableLiveData.observe(viewLifecycleOwner) {
            homeAdapter.updateData(it)
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
        setErrorResolveButtonClick {
            viewModel.loadData()
        }
    }

    private fun onHomeActions(homeActions: HomeActions) {
        when (homeActions) {
            is HomeActions.OpenParameterList -> {
                findNavController().navigate(
                    R.id.navigation_charts_type,
                    bundleOf(DETAIL_STATISTICS to viewModel.detailsStatistics)
                )
            }
            is HomeActions.OpenDetailsStatistics -> {
                findNavController().navigate(
                    R.id.navigation_adapter_details_statistics,
                    bundleOf(DETAIL_STATISTICS to viewModel.detailsStatistics)
                )
            }
            is HomeActions.OpenSessions -> {
                findNavController().navigate(R.id.navigation_history)
            }
            is HomeActions.OpenSession -> {
                findNavController().navigate(
                    R.id.navigation_adapter_session, bundleOf(
                        AdapterSessionFragment.SESSION_ID to homeActions.sessionId,
                        AdapterSessionFragment.SESSION_LAST_ID to homeActions.sessionLast,
                        AdapterSessionFragment.DATE to homeActions.sessionDate,
                        DETAIL_STATISTICS to homeActions.detailsStats as ArrayList<DetailStatisticsModel>
                    )
                )
            }
            is HomeActions.OpenSeason -> {
                findNavController().navigate(R.id.navigation_adapter_details_season_statistics)
            }
        }
    }
}