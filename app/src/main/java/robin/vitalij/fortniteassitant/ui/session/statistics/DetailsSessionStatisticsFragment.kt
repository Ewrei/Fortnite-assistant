package robin.vitalij.fortniteassitant.ui.session.statistics

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

class DetailsSessionStatisticsFragment : Fragment(R.layout.fragment_home) {

    @Inject
    lateinit var viewModelFactory: DetailsSessionStatisticsViewModelFactory

    private val viewModel: DetailsSessionStatisticsViewModel by viewModels { viewModelFactory }

    private val binding by viewBinding(FragmentHomeBinding::bind)

    private val homeBodyStatsAdapter = HomeBodyStatsAdapter({
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
            viewModel.sessionId = it.getLong(ARG_SESSION_ID)
            viewModel.sessionLastId = it.getLong(ARG_SESSION_LAST_ID)

        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListener()
        initializeRecyclerView()

        lifecycleScope.launch {
            viewModel.detailsStatisticsResult.collect {
                handleDetailsStatisticsResult(it)
            }
        }

        viewModel.loadData()
    }

    private fun setListener() {
        binding.viewErrorInclude.errorResolveButton.setOnClickListener {
            viewModel.loadData()
        }
    }

    private fun initializeRecyclerView() {
        binding.recyclerViewInclude.recyclerView.run {
            adapter = homeBodyStatsAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun handleDetailsStatisticsResult(result: LoadingState<List<HomeBodyStatsListItem>>) {
        when (result) {
            is LoadingState.Loading -> {
                binding.progressViewInclude.progressContainer.isVisible = true
                binding.viewErrorInclude.errorView.isVisible = false
            }
            is LoadingState.Success -> {
                binding.progressViewInclude.progressContainer.isVisible = false
                homeBodyStatsAdapter.updateData(result.data)
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
        private const val ARG_BATTLES_TYPE = "arg_battles_type"
        private const val ARG_GAME_TYPE = "arg_game_type"
        private const val ARG_SESSION_ID = "arg_session_id"
        private const val ARG_SESSION_LAST_ID = "arg_session_last_id"

        fun newInstance(
            battlesType: BattlesType,
            gameType: GameType,
            sessionId: Long,
            sessionLastId: Long
        ) =
            DetailsSessionStatisticsFragment().apply {
                arguments = bundleOf(
                    ARG_GAME_TYPE to gameType,
                    ARG_BATTLES_TYPE to battlesType,
                    ARG_SESSION_ID to sessionId,
                    ARG_SESSION_LAST_ID to sessionLastId
                )
            }
    }
}