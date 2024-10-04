package robin.vitalij.fortniteassitant.ui.cosmetics_new

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
import kotlinx.coroutines.launch
import robin.vitalij.fortniteassitant.FortniteApplication
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.common.extensions.setErrorView
import robin.vitalij.fortniteassitant.databinding.FragmentRecyclerViewWithToolbarBinding
import robin.vitalij.fortniteassitant.db.entity.CosmeticsNewEntity
import robin.vitalij.fortniteassitant.model.ErrorModelListItem
import robin.vitalij.fortniteassitant.model.LoadingState
import robin.vitalij.fortniteassitant.ui.bottomsheet.cosmetic.CosmeticResultFragment
import robin.vitalij.fortniteassitant.ui.cosmetics_new.adapter.CosmeticsNewAdapter
import javax.inject.Inject


class CosmeticsNewFragment : Fragment(R.layout.fragment_recycler_view_with_toolbar) {

    @Inject
    lateinit var viewModelFactory: CosmeticsNewViewModelFactory

    private val viewModel: CosmeticsNewViewModel by viewModels { viewModelFactory }

    private val binding by viewBinding(FragmentRecyclerViewWithToolbarBinding::bind)

    private val cosmeticsNewAdapter = CosmeticsNewAdapter {
        CosmeticResultFragment.show(childFragmentManager, it.id, true)
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

        lifecycleScope.launch {
            viewModel.cosmeticsNewResult.collect {
                handleCosmeticsNewResult(it)
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

    private fun initializeRecyclerView() {
        binding.recyclerViewInclude.recyclerView.run {
            adapter = cosmeticsNewAdapter
            layoutManager = GridLayoutManager(activity, MAX_SPAN_COUNT).apply {
                spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                    override fun getSpanSize(position: Int) = COSMETICS_SPAN_COUNT
                }
            }
        }
    }

    private fun handleCosmeticsNewResult(result: LoadingState<List<CosmeticsNewEntity>>) {
        when (result) {
            is LoadingState.Loading -> {
                binding.progressViewInclude.progressContainer.isVisible = true
                binding.errorViewInclude.errorView.isVisible = false
            }

            is LoadingState.Success -> {
                binding.progressViewInclude.progressContainer.isVisible = false
                cosmeticsNewAdapter.updateData(result.data)
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
        private const val MAX_SPAN_COUNT = 2
        private const val COSMETICS_SPAN_COUNT = 1
    }
}