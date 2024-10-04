package robin.vitalij.fortniteassitant.ui.shop.upcoming

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import kotlinx.coroutines.launch
import robin.vitalij.fortniteassitant.FortniteApplication
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.common.extensions.setErrorView
import robin.vitalij.fortniteassitant.databinding.FragmentRecyclerViewBinding
import robin.vitalij.fortniteassitant.model.ErrorModelListItem
import robin.vitalij.fortniteassitant.model.LoadingState
import robin.vitalij.fortniteassitant.model.network.shop.ItemShopUpcoming
import robin.vitalij.fortniteassitant.ui.bottomsheet.upcomingshop.UpcomingShopResultFragment
import robin.vitalij.fortniteassitant.ui.shop.upcoming.adapter.UpcomingShopAdapter
import javax.inject.Inject


class UpcomingShopFragment : Fragment(R.layout.fragment_recycler_view) {

    @Inject
    lateinit var viewModelFactory: UpcomingShopViewModelFactory

    private val viewModel: UpcomingShopViewModel by viewModels { viewModelFactory }

    private val binding by viewBinding(FragmentRecyclerViewBinding::bind)

    private val upcomingShopAdapter = UpcomingShopAdapter {
        UpcomingShopResultFragment.show(
            childFragmentManager,
            it,
        )
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        FortniteApplication.appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListener()
        initializeRecyclerView()

        lifecycleScope.launch {
            viewModel.upcomingShopResult.collect {
                handleUpcomingShopResult(it)
            }
        }

        viewModel.loadData()
    }

    private fun setListener() {
        binding.viewErrorInclude.errorResolveButton.setOnClickListener {
            viewModel.loadData()
        }
    }

    private fun initializeRecyclerView() {
        binding.recyclerViewInclude.recyclerView.run {
            adapter = upcomingShopAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun handleUpcomingShopResult(result: LoadingState<List<ItemShopUpcoming>>) {
        when (result) {
            is LoadingState.Loading -> {
                binding.progressViewInclude.progressContainer.isVisible = true
                binding.viewErrorInclude.errorView.isVisible = false
            }

            is LoadingState.Success -> {
                binding.progressViewInclude.progressContainer.isVisible = false
                upcomingShopAdapter.updateData(result.data)
            }

            is LoadingState.Error -> {
                binding.progressViewInclude.progressContainer.isVisible = false

                if (result.cause is ErrorModelListItem.ErrorItem) {
                    binding.viewErrorInclude.setErrorView(result.cause.errorModel)
                }
            }
        }
    }

}