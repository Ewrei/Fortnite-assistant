package robin.vitalij.fortniteassitant.ui.history

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import robin.vitalij.fortniteassitant.FortniteApplication
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.common.extensions.observeToEmpty
import robin.vitalij.fortniteassitant.common.extensions.observeToError
import robin.vitalij.fortniteassitant.common.extensions.observeToProgressBar
import robin.vitalij.fortniteassitant.databinding.FragmentRecyclerViewWithToolbarBinding
import robin.vitalij.fortniteassitant.model.DetailStatisticsModel
import robin.vitalij.fortniteassitant.model.HistoryUserModel
import robin.vitalij.fortniteassitant.ui.common.BaseFragment
import robin.vitalij.fortniteassitant.ui.details.viewpager.AdapterDetailsStatisticsFragment
import robin.vitalij.fortniteassitant.ui.history.adapter.HistoryAdapter
import robin.vitalij.fortniteassitant.ui.session.viewpager.AdapterSessionFragment.Companion.ARG_DATE
import robin.vitalij.fortniteassitant.ui.session.viewpager.AdapterSessionFragment.Companion.ARG_SESSION_ID
import robin.vitalij.fortniteassitant.ui.session.viewpager.AdapterSessionFragment.Companion.ARG_SESSION_LAST_ID
import javax.inject.Inject


class HistoryFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: HistoryViewModelFactory

    private lateinit var viewModel: HistoryViewModel

    private var _binding: FragmentRecyclerViewWithToolbarBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecyclerViewWithToolbarBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(viewModelStore, viewModelFactory)
            .get(HistoryViewModel::class.java).apply {
                observeToProgressBar(this@HistoryFragment)
                observeToError(this@HistoryFragment)
                observeToEmpty(this@HistoryFragment)
            }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        FortniteApplication.appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.mutableLiveData.observe(viewLifecycleOwner) {
            it.let(::initAdapter)
        }

        setNavigation()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setNavigation() {
        val navController = findNavController()
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        binding.toolbarInclude.toolbar.setupWithNavController(navController, appBarConfiguration)
    }

    private fun initAdapter(list: List<HistoryUserModel>) {
        binding.recyclerViewInclude.recyclerView.run {
            adapter =
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
            (adapter as HistoryAdapter).setData(list)
            layoutManager = LinearLayoutManager(context)
        }
    }
}