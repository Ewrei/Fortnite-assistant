package robin.vitalij.fortniteassitant.ui.charts

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import robin.vitalij.fortniteassitant.FortniteApplication
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.common.binding.LineChartBinding.setSession
import robin.vitalij.fortniteassitant.common.extensions.setErrorView
import robin.vitalij.fortniteassitant.databinding.FragmentChartsBinding
import robin.vitalij.fortniteassitant.model.ChartsModel
import robin.vitalij.fortniteassitant.model.ErrorModelListItem
import robin.vitalij.fortniteassitant.model.LoadingState
import robin.vitalij.fortniteassitant.model.SessionModel
import robin.vitalij.fortniteassitant.model.enums.BattlesType
import robin.vitalij.fortniteassitant.model.enums.ChartsType
import robin.vitalij.fortniteassitant.model.enums.GameType
import robin.vitalij.fortniteassitant.ui.comparison.BATTLES_TYPE
import robin.vitalij.fortniteassitant.ui.comparison.GAME_TYPE
import robin.vitalij.fortniteassitant.ui.main.MainActivity
import robin.vitalij.fortniteassitant.ui.subscription.SubscriptionActivity
import javax.inject.Inject

class ChartsFragment : Fragment(R.layout.fragment_charts) {

    @Inject
    lateinit var viewModelFactory: ChartsViewModelFactory

    private val viewModel: ChartsViewModel by viewModels { viewModelFactory }

    private val binding by viewBinding(FragmentChartsBinding::bind)

    override fun onAttach(context: Context) {
        super.onAttach(context)
        FortniteApplication.appComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            viewModel.chartsType = it.getSerializable(ARG_CHARTS_TYPE) as ChartsType
            viewModel.battlesType = it.getSerializable(BATTLES_TYPE) as BattlesType
            viewModel.gameType = it.getSerializable(GAME_TYPE) as GameType
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setNavigation()
        initToolbar()
        setListeners()

        viewModel.loadData()

        binding.arcProgress.setBottomText(getString((viewModel.chartsType.getTitleShortRes())))
        binding.arcProgress.max = viewModel.chartsType.getProgressMax()

        lifecycleScope.launch {
            viewModel.chartsResult.collect {
                handleChartsResult(it)
            }
        }
    }

    private fun setListeners() {
        binding.noSubscriptionView.subscribe.setOnClickListener {
            startActivity(SubscriptionActivity.newInstance(context))
        }

        binding.noSubscriptionView.watchVideoAds.setOnClickListener {
            (activity as? MainActivity)?.onDisplayButtonClicked {
                binding.noSubscriptionView.noSubscriptionView.isVisible =
                    viewModel.checkSubscriptionAccess()
            }
        }

        binding.errorViewInclude.errorResolveButton.setOnClickListener {
            viewModel.loadData()
        }
    }

    private fun initToolbar() {
        binding.toolbarInclude.toolbar.title = getString(viewModel.chartsType.getTitleRes())
    }

    private fun setNavigation() {
        val navController = findNavController()
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        binding.toolbarInclude.toolbar.setupWithNavController(navController, appBarConfiguration)
    }

    private fun handleChartsResult(result: LoadingState<ChartsModel>) {
        when (result) {
            is LoadingState.Loading -> {
                binding.progressViewInclude.progressContainer.isVisible = true
                binding.errorViewInclude.errorView.isVisible = false
            }
            is LoadingState.Success -> {
                binding.progressViewInclude.progressContainer.isVisible = false
                binding.arcProgress.setAnimatedProgress(result.data.header)
                result.data.sessionModels.let(::initAdapter)
            }
            is LoadingState.Error -> {
                binding.progressViewInclude.progressContainer.isVisible = false

                if (result.cause is ErrorModelListItem.ErrorItem) {
                    binding.errorViewInclude.setErrorView(result.cause.errorModel)
                }
            }
        }
    }

    private fun initAdapter(list: List<SessionModel>) {
        binding.chart.isInvisible = list.size < ONE_SESSION
        binding.empty.isVisible = list.size <= ONE_SESSION
        if (list.size > ONE_SESSION) {
            binding.chart.setSession(list)
        }
    }

    companion object {
        const val ARG_CHARTS_TYPE = "arg_charts_type"
        private const val ONE_SESSION = 1

        fun newInstance() = ChartsFragment()
    }
}