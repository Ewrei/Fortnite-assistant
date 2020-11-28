package robin.vitalij.fortniteassitant.ui.historymatch

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.recycler_view.*
import kotlinx.android.synthetic.main.toolbar_center_title.*
import robin.vitalij.fortniteassitant.FortniteApplication
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.common.extensions.observeToEmpty
import robin.vitalij.fortniteassitant.common.extensions.observeToError
import robin.vitalij.fortniteassitant.common.extensions.observeToProgressBar
import robin.vitalij.fortniteassitant.common.extensions.pxFromDp
import robin.vitalij.fortniteassitant.ui.common.BaseFragment
import robin.vitalij.fortniteassitant.ui.historymatch.adapter.HistoryMatchAdapter
import robin.vitalij.fortniteassitant.ui.historymatch.adapter.viewmodel.HistoryMatch
import robin.vitalij.fortniteassitant.utils.Space
import javax.inject.Inject

private const val SPAN_COUNT = 0

class HistoryMatchFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: HistoryMatchViewModelFactory

    private lateinit var viewModel: HistoryMatchViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = inflater.inflate(R.layout.fragment_history, container, false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(viewModelStore, viewModelFactory)
            .get(HistoryMatchViewModel::class.java).apply {
                observeToProgressBar(this@HistoryMatchFragment)
                observeToError(this@HistoryMatchFragment)
                observeToEmpty(this@HistoryMatchFragment)
            }

        setHasOptionsMenu(true)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        FortniteApplication.appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setNavigation()

        viewModel.mutableLiveData.observe(viewLifecycleOwner, Observer {
            it.let(::initAdapter)
        })

        initToolbar()
    }

    private fun setNavigation() {
        val navController = findNavController()
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        toolbar.setupWithNavController(navController, appBarConfiguration)
    }

    private fun initAdapter(list: List<HistoryMatch>) {
        recyclerView.run {
            adapter = HistoryMatchAdapter()
            (adapter as HistoryMatchAdapter).setData(list)
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(Space(context.pxFromDp(R.dimen.default_margin), SPAN_COUNT))
        }
    }

    private fun initToolbar() {
        toolbar.title = getString(R.string.title_history_match)
    }
}