package robin.vitalij.fortniteassitant.ui.comparison.statistics

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import kotlinx.coroutines.launch
import robin.vitalij.fortniteassitant.FortniteApplication
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.common.extensions.setErrorView
import robin.vitalij.fortniteassitant.databinding.FragmentRecyclerViewBinding
import robin.vitalij.fortniteassitant.model.ErrorModelListItem
import robin.vitalij.fortniteassitant.model.LoadingState
import robin.vitalij.fortniteassitant.model.enums.BattlesType
import robin.vitalij.fortniteassitant.model.enums.GameType
import robin.vitalij.fortniteassitant.ui.comparison.ComparisonActivity.Companion.ARG_BATTLES_TYPE
import robin.vitalij.fortniteassitant.ui.comparison.ComparisonActivity.Companion.ARG_GAME_TYPE
import robin.vitalij.fortniteassitant.ui.comparison.ComparisonActivity.Companion.ARG_PLAYER_ONE
import robin.vitalij.fortniteassitant.ui.comparison.ComparisonActivity.Companion.ARG_PLAYER_TWO
import robin.vitalij.fortniteassitant.ui.comparison.statistics.adapter.ComparisonStatisticsAdapter
import robin.vitalij.fortniteassitant.ui.comparison.statistics.adapter.ComparisonStatisticsListItem
import javax.inject.Inject

class ComparisonStatisticsFragment : Fragment(R.layout.fragment_recycler_view) {

    @Inject
    lateinit var viewModelFactory: ComparisonStatisticsViewModelFactory

    private var isSchedule: Boolean = false

    private val viewModel: ComparisonStatisticsViewModel by viewModels { viewModelFactory }

    private val binding by viewBinding(FragmentRecyclerViewBinding::bind)

    private val comparisonStatisticsAdapter = ComparisonStatisticsAdapter()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        FortniteApplication.appComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            viewModel.playerOneId = it.getString(ARG_PLAYER_ONE, "")
            viewModel.playerTwoId = it.getString(ARG_PLAYER_TWO, "")
            viewModel.battlesType = it.get(ARG_BATTLES_TYPE) as BattlesType
            viewModel.gameType = it.get(ARG_GAME_TYPE) as GameType
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeRecyclerView()
        setListener()

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.comparisonStatisticsResult.collect {
                handleComparisonStatisticsResult(it)
            }
        }

        viewModel.loadData(isSchedule)
    }

    fun loadSchedule(isSchedule: Boolean) {
        this.isSchedule = isSchedule
        viewModel.loadData(isSchedule)
    }

    fun loadGameType(gameType: GameType) {
        viewModel.gameType = gameType
        viewModel.loadData(isSchedule)
    }

    private fun setListener() {
        binding.viewErrorInclude.errorResolveButton.setOnClickListener {
            viewModel.loadData(isSchedule)
        }
    }

    private fun initializeRecyclerView() {
        binding.recyclerViewInclude.recyclerView.run {
            adapter = comparisonStatisticsAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun handleComparisonStatisticsResult(result: LoadingState<List<ComparisonStatisticsListItem>>) {
        when (result) {
            is LoadingState.Loading -> {
                binding.progressViewInclude.progressContainer.isVisible = true
            }

            is LoadingState.Success -> {
                binding.progressViewInclude.progressContainer.isVisible = false
                comparisonStatisticsAdapter.updateData(result.data)
            }

            is LoadingState.Error -> {
                binding.progressViewInclude.progressContainer.isVisible = false

                if (result.cause is ErrorModelListItem.ErrorItem) {
                    binding.viewErrorInclude.setErrorView(result.cause.errorModel)
                }
            }
        }
    }

    companion object {
        fun newInstance(
            playerOneId: String,
            playerTwoId: String,
            battlesType: BattlesType,
            gameType: GameType
        ) = ComparisonStatisticsFragment().apply {
            arguments = bundleOf(
                ARG_PLAYER_ONE to playerOneId,
                ARG_PLAYER_TWO to playerTwoId,
                ARG_BATTLES_TYPE to battlesType,
                ARG_GAME_TYPE to gameType
            )
        }
    }
}