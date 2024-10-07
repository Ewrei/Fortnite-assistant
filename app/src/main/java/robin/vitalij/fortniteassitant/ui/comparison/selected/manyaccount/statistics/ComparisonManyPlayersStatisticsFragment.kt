package robin.vitalij.fortniteassitant.ui.comparison.selected.manyaccount.statistics

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import robin.vitalij.fortniteassitant.FortniteApplication
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.common.extensions.observeToError
import robin.vitalij.fortniteassitant.common.extensions.observeToProgressBar
import robin.vitalij.fortniteassitant.common.extensions.setErrorView
import robin.vitalij.fortniteassitant.databinding.FragmentRecyclerViewBinding
import robin.vitalij.fortniteassitant.interfaces.ErrorController
import robin.vitalij.fortniteassitant.interfaces.ProgressBarController
import robin.vitalij.fortniteassitant.model.ErrorModel
import robin.vitalij.fortniteassitant.model.enums.BattlesType
import robin.vitalij.fortniteassitant.model.enums.GameType
import robin.vitalij.fortniteassitant.repository.storage.PreferenceManager
import robin.vitalij.fortniteassitant.ui.comparison.ComparisonActivity.Companion.ARG_BATTLES_TYPE
import robin.vitalij.fortniteassitant.ui.comparison.ComparisonActivity.Companion.ARG_GAME_TYPE
import robin.vitalij.fortniteassitant.ui.comparison.selected.manyaccount.statistics.adapter.ComparisonManyPlayerAdapter
import robin.vitalij.fortniteassitant.ui.comparison.selected.manyaccount.statistics.adapter.viewmodel.ComparisonManyPlayers
import javax.inject.Inject

class ComparisonManyPlayersStatisticsFragment :
    Fragment(R.layout.fragment_recycler_view), ErrorController,
    ProgressBarController {

    @Inject
    lateinit var viewModelFactory: ComparisonManyPlayersStatisticsViewModelFactory

    @Inject
    lateinit var preferenceManager: PreferenceManager

    private lateinit var viewModel: ComparisonManyPlayersStatisticsViewModel

    private var _binding: FragmentRecyclerViewBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecyclerViewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(
            viewModelStore,
            viewModelFactory
        )[ComparisonManyPlayersStatisticsViewModel::class.java].apply {
            observeToProgressBar(this@ComparisonManyPlayersStatisticsFragment)
            observeToError(this@ComparisonManyPlayersStatisticsFragment)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        FortniteApplication.appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.data.observe(viewLifecycleOwner) {
            it.let(::initAdapter)
        }

        arguments?.let {
            viewModel.battlesType = it.getSerializable(ARG_BATTLES_TYPE) as BattlesType
            viewModel.gameType = it.getSerializable(ARG_GAME_TYPE) as GameType
            viewModel.loadData()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initAdapter(list: List<ComparisonManyPlayers>) {
        binding.recyclerViewInclude.recyclerView.run {
            adapter = ComparisonManyPlayerAdapter {}
            (adapter as ComparisonManyPlayerAdapter).setData(list)
            layoutManager = LinearLayoutManager(context)
        }
    }

    fun loadGameType(gameType: GameType) {
        viewModel.gameType = gameType
        viewModel.loadData()
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

    companion object {
        fun newInstance(battlesType: BattlesType, gameType: GameType) =
            ComparisonManyPlayersStatisticsFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_BATTLES_TYPE, battlesType)
                    putSerializable(ARG_GAME_TYPE, gameType)
                }
            }
    }
}