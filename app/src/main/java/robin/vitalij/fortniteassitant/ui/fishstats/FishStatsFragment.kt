package robin.vitalij.fortniteassitant.ui.fishstats

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.GridLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import robin.vitalij.fortniteassitant.FortniteApplication
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.common.extensions.observeToEmpty
import robin.vitalij.fortniteassitant.common.extensions.observeToError
import robin.vitalij.fortniteassitant.common.extensions.observeToProgressBar
import robin.vitalij.fortniteassitant.common.extensions.setErrorView
import robin.vitalij.fortniteassitant.databinding.FragmentBattlePassRewardsBinding
import robin.vitalij.fortniteassitant.interfaces.ErrorController
import robin.vitalij.fortniteassitant.interfaces.ProgressBarController
import robin.vitalij.fortniteassitant.model.ErrorModel
import robin.vitalij.fortniteassitant.model.battle_pass_reward.SeasonModel
import robin.vitalij.fortniteassitant.model.network.FishStatsModel
import robin.vitalij.fortniteassitant.ui.bottomsheet.fish.FishResultFragment
import robin.vitalij.fortniteassitant.ui.fishstats.adapter.FishStatsAdapter
import javax.inject.Inject

private const val MAX_SPAN_COUNT = 3
private const val FISH_SPAN_COUNT = 1

class FishStatsFragment : Fragment(R.layout.fragment_battle_pass_rewards), ProgressBarController,
    ErrorController {

    @Inject
    lateinit var viewModelFactory: FishStatsViewModelFactory

    private lateinit var viewModel: FishStatsViewModel

    private val binding by viewBinding(FragmentBattlePassRewardsBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(viewModelStore, viewModelFactory)
            .get(FishStatsViewModel::class.java).apply {
                observeToProgressBar(this@FishStatsFragment)
                observeToError(this@FishStatsFragment)
                observeToEmpty(this@FishStatsFragment)
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

        viewModel.mutableSeasonLiveData.observe(viewLifecycleOwner) {
            binding.seasonSpinner.isVisible = it.isNotEmpty()
            binding.seasonSpinner.setItems(it)
        }

        setListener()
        setNavigation()
    }

    private fun setListener() {
        binding.viewErrorInclude.errorResolveButton.setOnClickListener {
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

    private fun initAdapter(list: List<FishStatsModel>) {
        binding.recyclerViewInclude.recyclerView.run {
            adapter = FishStatsAdapter {
                FishResultFragment.show(childFragmentManager, it.type)
            }
            (adapter as FishStatsAdapter).setData(list)

            layoutManager = GridLayoutManager(
                activity, MAX_SPAN_COUNT
            ).apply {
                spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                    override fun getSpanSize(position: Int) = FISH_SPAN_COUNT
                }
            }
        }
    }

    override fun setError(errorModel: ErrorModel) {
        binding.viewErrorInclude.setErrorView(errorModel)
    }

    override fun hideError() {
        binding.viewErrorInclude.errorView.isVisible = false
    }

    override fun showOrHideProgressBar(show: Boolean) {
        binding.progressViewInclude.progressContainer.isVisible = show
    }
}