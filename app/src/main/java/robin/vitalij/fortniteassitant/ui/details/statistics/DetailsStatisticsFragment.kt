package robin.vitalij.fortniteassitant.ui.details.statistics

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.recycler_view.*
import robin.vitalij.fortniteassitant.FortniteApplication
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.common.extensions.observeToError
import robin.vitalij.fortniteassitant.common.extensions.observeToProgressBar
import robin.vitalij.fortniteassitant.model.enums.BattlesType
import robin.vitalij.fortniteassitant.model.enums.GameType
import robin.vitalij.fortniteassitant.ui.common.BaseFragment
import robin.vitalij.fortniteassitant.ui.home.adapter.viewholder.statistics.adapter.HomeBodyStatsAdapter
import robin.vitalij.fortniteassitant.ui.home.adapter.viewholder.statistics.adapter.HomeBodyStatsListItem
import javax.inject.Inject

class DetailsStatisticsFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: DetailsStatisticsViewModelFactory

    private lateinit var viewModel: DetailsStatisticsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = inflater.inflate(R.layout.fragment_home, container, false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(viewModelStore, viewModelFactory)
            .get(DetailsStatisticsViewModel::class.java).apply {
                observeToProgressBar(this@DetailsStatisticsFragment)
                observeToError(this@DetailsStatisticsFragment)
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

        arguments?.let {
            viewModel.loadData(
                it.getSerializable(ARG_BATTLES_TYPE) as BattlesType,
                it.getSerializable(ARG_GAME_TYPE) as GameType
            )
        }
    }

    private fun initAdapter(list: List<HomeBodyStatsListItem>) {
        recyclerView.run {
            adapter = HomeBodyStatsAdapter({
                //do nothing
            }, {
                //do nothing
            })
            (adapter as HomeBodyStatsAdapter).setData(list)
            layoutManager = LinearLayoutManager(context)
        }
    }

    companion object {
        private const val ARG_BATTLES_TYPE = "arg_battles_type"
        private const val ARG_GAME_TYPE = "arg_game_type"

        fun newInstance(battlesType: BattlesType, gameType: GameType) =
            DetailsStatisticsFragment().apply {
                arguments = bundleOf(
                    ARG_GAME_TYPE to gameType,
                    ARG_BATTLES_TYPE to battlesType
                )
            }
    }
}