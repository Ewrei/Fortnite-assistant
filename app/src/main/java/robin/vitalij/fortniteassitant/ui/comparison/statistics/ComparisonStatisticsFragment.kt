package robin.vitalij.fortniteassitant.ui.comparison.statistics

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import robin.vitalij.fortniteassitant.FortniteApplication
import robin.vitalij.fortniteassitant.common.extensions.observeToError
import robin.vitalij.fortniteassitant.common.extensions.observeToProgressBar
import robin.vitalij.fortniteassitant.databinding.FragmentRecyclerViewBinding
import robin.vitalij.fortniteassitant.model.enums.BattlesType
import robin.vitalij.fortniteassitant.model.enums.GameType
import robin.vitalij.fortniteassitant.ui.common.BaseFragment
import robin.vitalij.fortniteassitant.ui.comparison.BATTLES_TYPE
import robin.vitalij.fortniteassitant.ui.comparison.GAME_TYPE
import robin.vitalij.fortniteassitant.ui.comparison.PLAYER_ONE
import robin.vitalij.fortniteassitant.ui.comparison.PLAYER_TWO
import robin.vitalij.fortniteassitant.ui.comparison.statistics.adapter.ComparisonStatisticsAdapter
import robin.vitalij.fortniteassitant.ui.comparison.statistics.adapter.viewmodel.ComparisonPlayer
import javax.inject.Inject

class ComparisonStatisticsFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ComparisonStatisticsViewModelFactory

    private lateinit var viewModel: ComparisonStatisticsViewModel

    private var isSchedule: Boolean = false

    private var _binding: FragmentRecyclerViewBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecyclerViewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FortniteApplication.appComponent.inject(this)
        viewModel = ViewModelProvider(viewModelStore, viewModelFactory)
            .get(ComparisonStatisticsViewModel::class.java).apply {
                observeToProgressBar(this@ComparisonStatisticsFragment)
                observeToError(this@ComparisonStatisticsFragment)
            }

        arguments?.let {
            viewModel.playerOneId = it.getString(PLAYER_ONE, "")
            viewModel.playerTwoId = it.getString(PLAYER_TWO, "")
            viewModel.battlesType = it.get(BATTLES_TYPE) as BattlesType
            viewModel.gameType = it.get(GAME_TYPE) as GameType
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.data.observe(viewLifecycleOwner, {
            it.let(::initAdapter)
        })

        viewModel.loadData(isSchedule)
    }

    fun loadSchedule(isSchedule: Boolean) {
        this.isSchedule = isSchedule
        viewModel.loadSchedule(isSchedule)
    }

    fun loadGameType(gameType: GameType) {
        viewModel.gameType = gameType
        viewModel.loadSchedule(isSchedule)
    }

    private fun initAdapter(list: List<ComparisonPlayer>) {
        binding.recyclerViewInclude.recyclerView.run {
            adapter = ComparisonStatisticsAdapter()
            (adapter as ComparisonStatisticsAdapter).setData(list)
            layoutManager = LinearLayoutManager(context)
        }
    }

    companion object {
        fun newInstance(
            playerOneId: String,
            playerTwoId: String,
            battlesType: BattlesType,
            gameType: GameType
        ) = ComparisonStatisticsFragment().apply {
            arguments = Bundle().apply {
                putString(PLAYER_ONE, playerOneId)
                putString(PLAYER_TWO, playerTwoId)
                putSerializable(BATTLES_TYPE, battlesType)
                putSerializable(GAME_TYPE, gameType)
            }
        }
    }
}