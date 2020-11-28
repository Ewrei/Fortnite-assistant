package robin.vitalij.fortniteassitant.ui.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.recycler_view.*
import robin.vitalij.fortniteassitant.FortniteApplication
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.common.extensions.observeToEmpty
import robin.vitalij.fortniteassitant.common.extensions.observeToError
import robin.vitalij.fortniteassitant.common.extensions.observeToProgressBar
import robin.vitalij.fortniteassitant.ui.common.BaseFragment
import robin.vitalij.fortniteassitant.ui.details.viewpager.AdapterDetailsStatisticsFragment.Companion.DETAIL_STATISTICS
import robin.vitalij.fortniteassitant.ui.home.adapter.HomeAdapter
import robin.vitalij.fortniteassitant.ui.home.adapter.viewmodel.Home
import robin.vitalij.fortniteassitant.ui.session.viewpager.AdapterSessionFragment
import javax.inject.Inject

class HomeFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: HomeViewModelFactory

    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = inflater.inflate(R.layout.fragment_home, container, false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(viewModelStore, viewModelFactory)
            .get(HomeViewModel::class.java).apply {
                observeToProgressBar(this@HomeFragment)
                observeToError(this@HomeFragment)
                observeToEmpty(this@HomeFragment)
            }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        FortniteApplication.appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.mutableLiveData.observe(viewLifecycleOwner, {
            it.let(::initAdapter)
        })
    }

    private fun initAdapter(list: List<Home>) {
        recyclerView.run {
            adapter = HomeAdapter(openDetailsStatistics = {
                findNavController().navigate(
                    R.id.navigation_adapter_details_statistics,
                    Bundle().apply {
                        putParcelableArrayList(DETAIL_STATISTICS, viewModel.detailsStatistics)
                    })
            },
                openSession = { sessionId: Long, sessionLast: Long, sessionDate: String ->
                    val bundle = Bundle().apply {
                        putLong(AdapterSessionFragment.SESSION_ID, sessionId)
                        putLong(AdapterSessionFragment.SESSION_LAST_ID, sessionLast)
                        putString(AdapterSessionFragment.DATE, sessionDate)
                    }

                    findNavController().navigate(R.id.adapterSessionFragment, bundle)
                },
                openSessions = {
                    findNavController().navigate(R.id.navigation_history)
                })
            (adapter as HomeAdapter).setData(list)
            layoutManager = LinearLayoutManager(context)
        }
    }
}