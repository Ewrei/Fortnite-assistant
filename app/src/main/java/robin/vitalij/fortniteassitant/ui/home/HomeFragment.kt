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

    private var homeAdapter = HomeAdapter(
        openParameterList = {
            findNavController().navigate(
                R.id.navigation_charts_type,
                bundleOf(DETAIL_STATISTICS to viewModel.detailsStatistics)
            )
        },
        openDetailsStatistics = {
            findNavController().navigate(
                R.id.navigation_adapter_details_statistics,
                bundleOf(DETAIL_STATISTICS to viewModel.detailsStatistics)
            )
        },
        openSession = { sessionId: Long, sessionLast: Long, sessionDate: String, detailsStats: List<DetailStatisticsModel> ->
            findNavController().navigate(
                R.id.navigation_adapter_session, bundleOf(
                    AdapterSessionFragment.SESSION_ID to sessionId,
                    AdapterSessionFragment.SESSION_LAST_ID to sessionLast,
                    AdapterSessionFragment.DATE to sessionDate,
                    DETAIL_STATISTICS to detailsStats as ArrayList<DetailStatisticsModel>
                )
            )
        },
        openSessions = {
            findNavController().navigate(R.id.navigation_history)
        },
        openSeason = {
            findNavController().navigate(R.id.navigation_adapter_details_season_statistics)
        })

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
}