package robin.vitalij.fortniteassitant.ui.fishing

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
import kotlinx.android.synthetic.main.fragment_fishing.*
import kotlinx.android.synthetic.main.toolbar_center_title.*
import kotlinx.android.synthetic.main.view_error.*
import robin.vitalij.fortniteassitant.FortniteApplication
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.common.extensions.observeToError
import robin.vitalij.fortniteassitant.common.extensions.observeToProgressBar
import robin.vitalij.fortniteassitant.db.entity.FishEntity
import robin.vitalij.fortniteassitant.ui.bottomsheet.fish.FishResultFragment
import robin.vitalij.fortniteassitant.ui.common.BaseFragment
import robin.vitalij.fortniteassitant.ui.fishing.adapter.FishAdapter
import javax.inject.Inject

private const val MAX_SPAN_COUNT = 3
private const val FISH_SPAN_COUNT = 1

class FishingFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: FishingViewModelFactory

    private lateinit var viewModel: FishingViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = inflater.inflate(R.layout.fragment_fishing, container, false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(viewModelStore, viewModelFactory)
            .get(FishingViewModel::class.java).apply {
                observeToProgressBar(this@FishingFragment)
                observeToError(this@FishingFragment)
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

        setListeners()
        setNavigation()
    }

    private fun setListeners() {
        setErrorResolveButtonClick {
            viewModel.loadData()
        }

        statsFish.setOnClickListener {
            findNavController().navigate(R.id.navigation_fish_stats)
        }
    }

    private fun setNavigation() {
        val navController = findNavController()
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        toolbar.setupWithNavController(navController, appBarConfiguration)
    }

    private fun initAdapter(list: List<FishEntity>) {
        recyclerView.run {
            adapter = FishAdapter {
                FishResultFragment.show(childFragmentManager, it.id)
            }
            (adapter as FishAdapter).setData(list)

            val gridlayoutManager = GridLayoutManager(
                activity, MAX_SPAN_COUNT
            )

            gridlayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int) = FISH_SPAN_COUNT
            }

            layoutManager = gridlayoutManager
        }
    }
}