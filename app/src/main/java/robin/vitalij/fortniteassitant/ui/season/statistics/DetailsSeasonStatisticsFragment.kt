package robin.vitalij.fortniteassitant.ui.season.statistics

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import robin.vitalij.fortniteassitant.FortniteApplication
import robin.vitalij.fortniteassitant.common.extensions.observeToEmpty
import robin.vitalij.fortniteassitant.common.extensions.observeToError
import robin.vitalij.fortniteassitant.common.extensions.observeToProgressBar
import robin.vitalij.fortniteassitant.databinding.FragmentHomeBinding
import robin.vitalij.fortniteassitant.model.enums.BattlesType
import robin.vitalij.fortniteassitant.model.enums.GameType
import robin.vitalij.fortniteassitant.ui.common.BaseFragment
import robin.vitalij.fortniteassitant.ui.home.adapter.viewholder.statistics.adapter.HomeBodyStatsAdapter
import javax.inject.Inject

class DetailsSeasonStatisticsFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: DetailsSeasonStatisticsViewModelFactory

    private lateinit var viewModel: DetailsSeasonStatisticsViewModel

    private lateinit var binding: FragmentHomeBinding

    private val homeBodyStatsAdapter = HomeBodyStatsAdapter({
        //do nothing
    }, {
        //do nothing
    })

    override fun onAttach(context: Context) {
        super.onAttach(context)
        FortniteApplication.appComponent.inject(this)
    }

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
            .get(DetailsSeasonStatisticsViewModel::class.java).apply {
                observeToProgressBar(this@DetailsSeasonStatisticsFragment)
                observeToError(this@DetailsSeasonStatisticsFragment)
                observeToEmpty(this@DetailsSeasonStatisticsFragment)
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeRecyclerView()

        viewModel.mutableLiveData.observe(viewLifecycleOwner) {
            homeBodyStatsAdapter.updateData(it)
        }

        arguments?.let {
            viewModel.loadData(
                it.getSerializable(ARG_BATTLES_TYPE) as BattlesType,
                it.getSerializable(ARG_GAME_TYPE) as GameType
            )
        }
    }

    private fun initializeRecyclerView() {
        binding.recyclerViewInclude.recyclerView.run {
            adapter = homeBodyStatsAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    companion object {
        private const val ARG_BATTLES_TYPE = "arg_battles_type"
        private const val ARG_GAME_TYPE = "arg_game_type"

        fun newInstance(battlesType: BattlesType, gameType: GameType) =
            DetailsSeasonStatisticsFragment().apply {
                arguments = bundleOf(ARG_GAME_TYPE to gameType, ARG_BATTLES_TYPE to battlesType)
            }
    }
}