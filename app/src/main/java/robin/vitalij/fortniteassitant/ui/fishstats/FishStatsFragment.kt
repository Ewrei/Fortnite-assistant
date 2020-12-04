package robin.vitalij.fortniteassitant.ui.fishstats

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.fragment_battle_pass_rewards.*
import kotlinx.android.synthetic.main.recycler_view.*
import kotlinx.android.synthetic.main.toolbar_center_title.*
import kotlinx.android.synthetic.main.view_error.*
import robin.vitalij.fortniteassitant.FortniteApplication
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.common.extensions.observeToError
import robin.vitalij.fortniteassitant.common.extensions.observeToProgressBar
import robin.vitalij.fortniteassitant.model.battlepassreward.SeasonModel
import robin.vitalij.fortniteassitant.model.network.FishStats
import robin.vitalij.fortniteassitant.ui.bottomsheet.fish.FishResultFragment
import robin.vitalij.fortniteassitant.ui.common.BaseFragment
import robin.vitalij.fortniteassitant.ui.fishstats.adapter.FishStatsAdapter
import javax.inject.Inject

private const val MAX_SPAN_COUNT = 3

class FishStatsFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: FishStatsViewModelFactory

    private lateinit var viewModel: FishStatsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = inflater.inflate(R.layout.fragment_battle_pass_rewards, container, false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(viewModelStore, viewModelFactory)
            .get(FishStatsViewModel::class.java).apply {
                observeToProgressBar(this@FishStatsFragment)
                observeToError(this@FishStatsFragment)
            }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        FortniteApplication.appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.mutableLiveData.observe(viewLifecycleOwner, {
            it.let(::initAdapter)
        })

        viewModel.mutableSeasonLiveData.observe(viewLifecycleOwner, {
            seasonSpinner.setItems(it)
        })

        setListener()
        setNavigation()
    }

    private fun setListener() {
        errorResolveButton.setOnClickListener {
            viewModel.loadData()
        }

        seasonSpinner.setOnItemSelectedListener { _, _, _, item ->
            viewModel.changeSeason((item as SeasonModel))
        }
    }

    private fun setNavigation() {
        val navController = findNavController()
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        toolbar.setupWithNavController(navController, appBarConfiguration)
    }

    private fun initAdapter(list: List<FishStats>) {
        recyclerView.run {
            adapter = FishStatsAdapter {
                FishResultFragment.show(childFragmentManager, it.type)
            }
            (adapter as FishStatsAdapter).setData(list)

            val gridlayoutManager = GridLayoutManager(
                activity, MAX_SPAN_COUNT
            )

            gridlayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int) = 1
            }

            layoutManager = gridlayoutManager
        }
    }
}