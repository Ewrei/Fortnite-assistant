package robin.vitalij.fortniteassitant.ui.vehicles

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
import robin.vitalij.fortniteassitant.databinding.FragmentRecyclerViewWithToolbarBinding
import robin.vitalij.fortniteassitant.model.ErrorModelListItem
import robin.vitalij.fortniteassitant.model.LoadingState
import robin.vitalij.fortniteassitant.model.network.VehicleModel
import robin.vitalij.fortniteassitant.ui.bottomsheet.vehicles.VehiclesResultFragment
import robin.vitalij.fortniteassitant.ui.vehicles.adapter.VehiclesAdapter
import javax.inject.Inject

class VehiclesFragment : Fragment(R.layout.fragment_recycler_view_with_toolbar) {

    @Inject
    lateinit var viewModelFactory: VehiclesViewModelFactory

    private val viewModel: VehiclesViewModel by viewModels { viewModelFactory }

    private val binding by viewBinding(FragmentRecyclerViewWithToolbarBinding::bind)

    private val vehiclesAdapter = VehiclesAdapter(onClick = {
        VehiclesResultFragment.show(childFragmentManager, it)
    })

    override fun onAttach(context: Context) {
        super.onAttach(context)
        FortniteApplication.appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListener()
        setNavigation()
        initializeRecyclerView()

        binding.viewEmptyInclude.empty.setText(R.string.empty_vehicles)

        lifecycleScope.launch {
            viewModel.gameVehiclesResult.collect {
                handleVehiclesResult(it)
            }
        }

        viewModel.loadData()
    }

    private fun setListener() {
        binding.errorViewInclude.errorResolveButton.setOnClickListener {
            viewModel.loadData()
        }
    }

    private fun setNavigation() {
        val navController = findNavController()
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        binding.toolbarInclude.toolbar.setupWithNavController(navController, appBarConfiguration)
    }

    private fun handleVehiclesResult(result: LoadingState<List<VehicleModel>>) {
        when (result) {
            is LoadingState.Loading -> {
                binding.progressViewInclude.progressContainer.isVisible = true
                binding.errorViewInclude.errorView.isVisible = false
            }
            is LoadingState.Success -> {
                binding.progressViewInclude.progressContainer.isVisible = false
                binding.viewEmptyInclude.emptyView.isVisible = result.data.isEmpty()

                vehiclesAdapter.updateData(result.data)
            }
            is LoadingState.Error -> {
                binding.progressViewInclude.progressContainer.isVisible = false
                if (result.cause is ErrorModelListItem.ErrorItem) {
                    binding.errorViewInclude.setErrorView(result.cause.errorModel)
                }
            }
        }
    }

    private fun initializeRecyclerView() {
        binding.recyclerViewInclude.recyclerView.run {
            adapter = vehiclesAdapter
            layoutManager = GridLayoutManager(activity, MAX_SPAN_COUNT).apply {
                this.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                    override fun getSpanSize(position: Int) = VEHICLES_SPAN_COUNT
                }
            }
        }
    }

    companion object {
        private const val MAX_SPAN_COUNT = 2
        private const val VEHICLES_SPAN_COUNT = 1

    }

}