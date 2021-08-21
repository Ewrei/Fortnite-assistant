package robin.vitalij.fortniteassitant.ui.season.statistics

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.recycler_view.*
import kotlinx.android.synthetic.main.view_error.*
import robin.vitalij.fortniteassitant.FortniteApplication
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.common.extensions.observeToEmpty
import robin.vitalij.fortniteassitant.common.extensions.observeToError
import robin.vitalij.fortniteassitant.common.extensions.observeToProgressBar
import robin.vitalij.fortniteassitant.model.enums.BattlesType
import robin.vitalij.fortniteassitant.model.enums.GameType
import robin.vitalij.fortniteassitant.ui.common.BaseFragment
import robin.vitalij.fortniteassitant.ui.home.adapter.viewholder.statistics.adapter.HomeBodyStatsAdapter
import robin.vitalij.fortniteassitant.ui.home.adapter.viewholder.statistics.adapter.viewmodel.HomeBodyStats
import javax.inject.Inject

class DetailsSeasonStatisticsFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: DetailsSeasonStatisticsViewModelFactory

    private lateinit var viewModel: DetailsSeasonStatisticsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = inflater.inflate(R.layout.fragment_home, container, false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(viewModelStore, viewModelFactory)
            .get(DetailsSeasonStatisticsViewModel::class.java).apply {
                observeToProgressBar(this@DetailsSeasonStatisticsFragment)
                observeToError(this@DetailsSeasonStatisticsFragment)
                observeToEmpty(this@DetailsSeasonStatisticsFragment)
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

        arguments?.let {
            viewModel.loadData(
                it.getSerializable(BATTLES_TYPE) as BattlesType,
                it.getSerializable(GAME_TYPE) as GameType
            )
        }
    }

    private fun initAdapter(list: List<HomeBodyStats>) {
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
        private const val BATTLES_TYPE = "battles_type"
        private const val GAME_TYPE = "game_type"

        fun newInstance(battlesType: BattlesType, gameType: GameType) =
            DetailsSeasonStatisticsFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(GAME_TYPE, gameType)
                    putSerializable(BATTLES_TYPE, battlesType)
                }
            }
    }
}