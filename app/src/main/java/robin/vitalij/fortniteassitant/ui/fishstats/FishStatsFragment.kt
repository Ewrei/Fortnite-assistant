package robin.vitalij.fortniteassitant.ui.fishstats

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.GridLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import robin.vitalij.fortniteassitant.FortniteApplication
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.common.extensions.setErrorView
import robin.vitalij.fortniteassitant.databinding.FragmentBattlePassRewardsBinding
import robin.vitalij.fortniteassitant.model.ErrorModelListItem
import robin.vitalij.fortniteassitant.model.LoadingState
import robin.vitalij.fortniteassitant.model.battle_pass_reward.SeasonModel
import robin.vitalij.fortniteassitant.model.network.FishStatsModel
import robin.vitalij.fortniteassitant.ui.bottomsheet.fish.FishResultFragment
import robin.vitalij.fortniteassitant.ui.fishstats.adapter.FishStatsAdapter
import javax.inject.Inject

class FishStatsFragment : Fragment(R.layout.fragment_battle_pass_rewards) {

    @Inject
    lateinit var viewModelFactory: FishStatsViewModelFactory

    private val viewModel: FishStatsViewModel by viewModels { viewModelFactory }

    private val binding by viewBinding(FragmentBattlePassRewardsBinding::bind)

    private val fishStatsAdapter = FishStatsAdapter {
        FishResultFragment.show(childFragmentManager, it.type)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        FortniteApplication.appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListener()
        setNavigation()
        initializeRecyclerView()

        binding.viewEmptyInclude.empty.setText(R.string.empty_fish)

        lifecycleScope.launch {
            viewModel.filterResult.collect {
                handleFishStatsResult(it)
            }
        }

        lifecycleScope.launch {
            viewModel.seasonsResult.collect {
                binding.seasonSpinner.isVisible = it.isNotEmpty()
                binding.seasonSpinner.setItems(it)
            }
        }

        viewModel.loadData()
    }

    private fun setListener() {
        binding.errorViewInclude.errorResolveButton.setOnClickListener {
            viewModel.loadData()
        }

        binding.seasonSpinner.setOnItemSelectedListener { _, _, _, item ->
            viewModel.changeSeason((item as SeasonModel))
        }
    }

    private fun setNavigation() {
        val navController = findNavController()
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        binding.toolbarInclude.toolbar.setupWithNavController(navController, appBarConfiguration)
    }

    private fun initializeRecyclerView() {
        binding.recyclerViewInclude.recyclerView.run {
            adapter = fishStatsAdapter
            layoutManager = GridLayoutManager(activity, MAX_SPAN_COUNT).apply {
                spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                    override fun getSpanSize(position: Int) = FISH_SPAN_COUNT
                }
            }
        }
    }

    private fun handleFishStatsResult(result: LoadingState<List<FishStatsModel>>) {
        when (result) {
            is LoadingState.Loading -> {
                binding.progressViewInclude.progressContainer.isVisible = true
                binding.errorViewInclude.errorView.isVisible = false
                binding.recyclerViewInclude.recyclerView.isVisible = false
                binding.viewEmptyInclude.emptyView.isVisible = false
            }
            is LoadingState.Success -> {
                binding.progressViewInclude.progressContainer.isVisible = false
                binding.recyclerViewInclude.recyclerView.isVisible = true

                fishStatsAdapter.submitList(result.data)
                binding.viewEmptyInclude.emptyView.isVisible = result.data.isEmpty()
            }
            is LoadingState.Error -> {
                binding.progressViewInclude.progressContainer.isVisible = false
                binding.recyclerViewInclude.recyclerView.isVisible = false
                binding.viewEmptyInclude.emptyView.isVisible = false

                if (result.cause is ErrorModelListItem.ErrorItem) {
                    binding.errorViewInclude.setErrorView(result.cause.errorModel)
                }
            }
        }
    }

    companion object {
        private const val MAX_SPAN_COUNT = 3
        private const val FISH_SPAN_COUNT = 1
    }

}