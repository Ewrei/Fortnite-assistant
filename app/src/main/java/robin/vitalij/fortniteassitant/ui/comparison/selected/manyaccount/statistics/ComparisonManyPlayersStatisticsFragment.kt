package robin.vitalij.fortniteassitant.ui.comparison.selected.manyaccount.statistics

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.recycler_view.*
import robin.vitalij.fortniteassitant.FortniteApplication
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.common.extensions.observeToError
import robin.vitalij.fortniteassitant.common.extensions.observeToProgressBar
import robin.vitalij.fortniteassitant.repository.storage.PreferenceManager
import robin.vitalij.fortniteassitant.ui.common.BaseFragment
import robin.vitalij.fortniteassitant.ui.comparison.selected.manyaccount.statistics.adapter.ComparisonManyPlayerAdapter
import robin.vitalij.fortniteassitant.ui.comparison.selected.manyaccount.statistics.adapter.viewmodel.ComparisonManyPlayers
import javax.inject.Inject

private const val IS_OTHER = "is_other"

class ComparisonManyPlayersStatisticsFragment :
    BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ComparisonManyPlayersStatisticsViewModelFactory

    @Inject
    lateinit var preferenceManager: PreferenceManager

    private lateinit var viewModel: ComparisonManyPlayersStatisticsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(viewModelStore, viewModelFactory)
            .get(ComparisonManyPlayersStatisticsViewModel::class.java).apply {
                observeToProgressBar(this@ComparisonManyPlayersStatisticsFragment)
                observeToError(this@ComparisonManyPlayersStatisticsFragment)
            }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        FortniteApplication.appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.data.observe(viewLifecycleOwner, Observer {
            it.let(::initAdapter)
        })

        arguments?.let {
            viewModel.loadData(it.getBoolean(IS_OTHER, false))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = inflater.inflate(R.layout.fragment_with_recyclerview_and_lce, container, false)

    private fun initAdapter(list: List<ComparisonManyPlayers>) {
        recyclerView.run {
            adapter = ComparisonManyPlayerAdapter {}
            (adapter as ComparisonManyPlayerAdapter).setData(list)
            layoutManager = LinearLayoutManager(context)
        }
    }

    companion object {
        fun newInstance(isOther: Boolean) = ComparisonManyPlayersStatisticsFragment().apply {
            arguments = Bundle().apply {
                putBoolean(IS_OTHER, isOther)
            }
        }
    }
}