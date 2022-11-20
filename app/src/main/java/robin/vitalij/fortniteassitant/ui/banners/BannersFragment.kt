package robin.vitalij.fortniteassitant.ui.banners

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
import robin.vitalij.fortniteassitant.db.entity.BannerEntity
import robin.vitalij.fortniteassitant.model.ErrorModelListItem
import robin.vitalij.fortniteassitant.model.LoadingState
import robin.vitalij.fortniteassitant.ui.banners.adapter.BannersAdapter
import robin.vitalij.fortniteassitant.ui.bottomsheet.banner.BannerResultFragment
import javax.inject.Inject

class BannersFragment : Fragment(R.layout.fragment_recycler_view_with_toolbar) {

    @Inject
    lateinit var viewModelFactory: BannersViewModelFactory

    private val viewModel: BannersViewModel by viewModels { viewModelFactory }

    private val binding by viewBinding(FragmentRecyclerViewWithToolbarBinding::bind)

    private var bannersAdapter = BannersAdapter {
        BannerResultFragment.show(childFragmentManager, it.id)
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
            viewModel.bannersResult.collect {
                handleBannersResult(it)
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
            adapter = bannersAdapter
            layoutManager = GridLayoutManager(
                activity, MAX_SPAN_COUNT
            ).apply {
                spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                    override fun getSpanSize(position: Int) = BANNER_SPAN_COUNT
                }
            }
        }
    }

    private fun handleBannersResult(result: LoadingState<List<BannerEntity>>) {
        when (result) {
            is LoadingState.Loading -> {
                binding.progressViewInclude.progressContainer.isVisible = true
                binding.errorViewInclude.errorView.isVisible = false
            }
            is LoadingState.Success -> {
                binding.progressViewInclude.progressContainer.isVisible = false
                bannersAdapter.updateData(result.data)
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
        private const val BANNER_SPAN_COUNT = 1
    }
}