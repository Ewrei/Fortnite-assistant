package robin.vitalij.fortniteassitant.ui.fishing

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
import robin.vitalij.fortniteassitant.databinding.FragmentFishingBinding
import robin.vitalij.fortniteassitant.db.entity.FishEntity
import robin.vitalij.fortniteassitant.model.ErrorModelListItem
import robin.vitalij.fortniteassitant.model.LoadingState
import robin.vitalij.fortniteassitant.ui.bottomsheet.fish.FishResultFragment
import robin.vitalij.fortniteassitant.ui.fishing.adapter.FishAdapter
import javax.inject.Inject

class FishingFragment : Fragment(R.layout.fragment_fishing) {

    @Inject
    lateinit var viewModelFactory: FishingViewModelFactory

    private val viewModel: FishingViewModel by viewModels { viewModelFactory }

    private val binding by viewBinding(FragmentFishingBinding::bind)

    private val fishAdapter = FishAdapter {
        FishResultFragment.show(childFragmentManager, it.id)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        FortniteApplication.appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
        setNavigation()
        initializeRecyclerView()

        lifecycleScope.launch {
            viewModel.fishResult.collect {
                handleFishResult(it)
            }
        }

        viewModel.loadData()
    }

    private fun setListeners() {
        binding.errorViewInclude.errorResolveButton.setOnClickListener {
            viewModel.loadData()
        }

        binding.statsFish.setOnClickListener {
            findNavController().navigate(R.id.navigation_fish_stats)
        }
    }

    private fun setNavigation() {
        val navController = findNavController()
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        binding.toolbarInclude.toolbar.setupWithNavController(navController, appBarConfiguration)
    }

    private fun initializeRecyclerView() {
        binding.recyclerView.run {
            adapter = fishAdapter

            val gridlayoutManager = GridLayoutManager(
                activity, MAX_SPAN_COUNT
            ).apply {
                this.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                    override fun getSpanSize(position: Int) = FISH_SPAN_COUNT
                }
            }

            layoutManager = gridlayoutManager
        }
    }

    private fun handleFishResult(result: LoadingState<List<FishEntity>>) {
        when (result) {
            is LoadingState.Loading -> {
                binding.progressViewInclude.progressContainer.isVisible = true
                binding.errorViewInclude.errorView.isVisible = false
            }
            is LoadingState.Success -> {
                binding.progressViewInclude.progressContainer.isVisible = false
                fishAdapter.updateData(result.data)
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
        private const val MAX_SPAN_COUNT = 3
        private const val FISH_SPAN_COUNT = 1

    }

}