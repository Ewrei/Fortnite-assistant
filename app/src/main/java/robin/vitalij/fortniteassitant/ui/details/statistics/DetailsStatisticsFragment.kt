package robin.vitalij.fortniteassitant.ui.details.statistics

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
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import robin.vitalij.fortniteassitant.FortniteApplication
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.common.extensions.setErrorView
import robin.vitalij.fortniteassitant.databinding.FragmentHomeBinding
import robin.vitalij.fortniteassitant.model.ErrorModelListItem
import robin.vitalij.fortniteassitant.model.LoadingState
import robin.vitalij.fortniteassitant.model.enums.BattlesType
import robin.vitalij.fortniteassitant.model.enums.GameType
import robin.vitalij.fortniteassitant.ui.home.adapter.viewholder.statistics.adapter.HomeBodyStatsAdapter
import robin.vitalij.fortniteassitant.ui.home.adapter.viewholder.statistics.adapter.HomeBodyStatsListItem
import javax.inject.Inject

class DetailsStatisticsFragment : Fragment(R.layout.fragment_home) {

    @Inject
    lateinit var viewModelFactory: DetailsStatisticsViewModelFactory

    private val viewModel: DetailsStatisticsViewModel by viewModels { viewModelFactory }

    private val binding by viewBinding(FragmentHomeBinding::bind)

    private val homeAdapter = HomeBodyStatsAdapter({
        //do nothing
    }, {
        //do nothing
    })

    override fun onAttach(context: Context) {
        super.onAttach(context)
        FortniteApplication.appComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            viewModel.battlesType = it.getSerializable(ARG_BATTLES_TYPE) as BattlesType
            viewModel.gameType = it.getSerializable(ARG_GAME_TYPE) as GameType
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeRecyclerView()
        setListener()

        lifecycleScope.launch {
            viewModel.detailsStatisticsResult.collect {
                handleDetailsStatisticsResult(it)
            }
        }

        viewModel.loadData()
    }

    private fun initializeRecyclerView() {
        binding.recyclerViewInclude.recyclerView.run {
            adapter = homeAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun setListener() {
        binding.errorViewInclude.errorResolveButton.setOnClickListener {
            viewModel.loadData()
        }
    }

    private fun handleDetailsStatisticsResult(result: LoadingState<List<HomeBodyStatsListItem>>) {
        when (result) {
            is LoadingState.Loading -> {
                binding.progressViewInclude.progressContainer.isVisible = true
                binding.errorViewInclude.errorView.isVisible = false
            }
            is LoadingState.Success -> {
                binding.progressViewInclude.progressContainer.isVisible = false
                homeAdapter.updateData(result.data)
            }
            is LoadingState.Error -> {
                binding.progressViewInclude.progressContainer.isVisible = false

                if (result.cause is ErrorModelListItem.ErrorItem) {
                    binding.errorViewInclude.setErrorView(result.cause.errorModel)
                }
            }
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