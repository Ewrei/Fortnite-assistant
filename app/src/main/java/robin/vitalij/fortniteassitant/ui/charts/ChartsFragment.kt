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
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import kotlinx.coroutines.launch
import robin.vitalij.fortniteassitant.FortniteApplication
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.common.extensions.setErrorView
import robin.vitalij.fortniteassitant.common.extensions.setSession
import robin.vitalij.fortniteassitant.databinding.FragmentChartsBinding
import robin.vitalij.fortniteassitant.model.ChartsModel
import robin.vitalij.fortniteassitant.model.ErrorModelListItem
import robin.vitalij.fortniteassitant.model.LoadingState
import robin.vitalij.fortniteassitant.model.SessionModel
import robin.vitalij.fortniteassitant.ui.main.MainActivity
import robin.vitalij.fortniteassitant.ui.subscription.SubscriptionActivity
import javax.inject.Inject

class ChartsFragment : Fragment(R.layout.fragment_charts) {

    @Inject
    lateinit var viewModelFactory: ChartsViewModelFactory

    private val viewModel: ChartsViewModel by viewModels { viewModelFactory }

    private val binding by viewBinding(FragmentChartsBinding::bind)

    private val args: ChartsFragmentArgs by navArgs()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        FortniteApplication.appComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.chartsType = args.argChartsType
        viewModel.battlesType = args.argBattlesType
        viewModel.gameType = args.argGameType
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
                result.data.sessionModels.let(::initChart)
            }

            is LoadingState.Error -> {
                binding.progressViewInclude.progressContainer.isVisible = false

                if (result.cause is ErrorModelListItem.ErrorItem) {
                    binding.errorViewInclude.setErrorView(result.cause.errorModel)
                }
            }
        }
    }

    private fun initChart(list: List<SessionModel>) {
        binding.chart.isInvisible = list.size < ONE_SESSION
        binding.empty.isVisible = list.size <= ONE_SESSION
        if (list.size > ONE_SESSION) {
            binding.chart.setSession(list)
        }
    }

    companion object {
        private const val ONE_SESSION = 1
    }
}