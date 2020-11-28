package robin.vitalij.fortniteassitant.ui.history

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.recycler_view.*
import kotlinx.android.synthetic.main.toolbar_center_title.*
import robin.vitalij.fortniteassitant.FortniteApplication
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.common.extensions.observeToError
import robin.vitalij.fortniteassitant.common.extensions.observeToProgressBar
import robin.vitalij.fortniteassitant.model.HistoryUserModel
import robin.vitalij.fortniteassitant.ui.common.BaseFragment
import robin.vitalij.fortniteassitant.ui.history.adapter.HistoryAdapter
import robin.vitalij.fortniteassitant.ui.session.viewpager.AdapterSessionFragment.Companion.DATE
import robin.vitalij.fortniteassitant.ui.session.viewpager.AdapterSessionFragment.Companion.SESSION_ID
import robin.vitalij.fortniteassitant.ui.session.viewpager.AdapterSessionFragment.Companion.SESSION_LAST_ID
import javax.inject.Inject


class HistoryFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: HistoryViewModelFactory

    private lateinit var viewModel: HistoryViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = inflater.inflate(R.layout.fragment_history, container, false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(viewModelStore, viewModelFactory)
            .get(HistoryViewModel::class.java).apply {
                observeToProgressBar(this@HistoryFragment)
                observeToError(this@HistoryFragment)
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

        setNavigation()
    }

    private fun setNavigation() {
        val navController = findNavController()
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        toolbar.setupWithNavController(navController, appBarConfiguration)
    }

    private fun initAdapter(list: List<HistoryUserModel>) {
        recyclerView.run {
            adapter = HistoryAdapter { sessionId: Long, sessionLast: Long, sessionDate: String ->
                val bundle = Bundle().apply {
                    putLong(SESSION_ID, sessionId)
                    putLong(SESSION_LAST_ID, sessionLast)
                    putString(DATE, sessionDate)
                }

                findNavController().navigate(R.id.adapterSessionFragment, bundle)
            }
            (adapter as HistoryAdapter).setData(list)
            layoutManager = LinearLayoutManager(context)
        }
    }
}