package robin.vitalij.fortniteassitant.ui.shop.current

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import robin.vitalij.fortniteassitant.FortniteApplication
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.common.extensions.setErrorView
import robin.vitalij.fortniteassitant.databinding.FragmentRecyclerViewBinding
import robin.vitalij.fortniteassitant.model.ErrorModelListItem
import robin.vitalij.fortniteassitant.model.LoadingState
import robin.vitalij.fortniteassitant.model.network.shop.ShopAdapterItem
import robin.vitalij.fortniteassitant.ui.bottomsheet.currentshop.CurrentShopResultFragment
import robin.vitalij.fortniteassitant.ui.shop.current.header_adapter.HeaderShopAdapter
import javax.inject.Inject

class CurrentShopFragment : Fragment(R.layout.fragment_recycler_view) {

    @Inject
    lateinit var viewModelFactory: CurrentShopViewModelFactory

    private val viewModel: CurrentShopViewModel by viewModels { viewModelFactory }

    private val binding by viewBinding(FragmentRecyclerViewBinding::bind)

    private val headerShopAdapter = HeaderShopAdapter(
        onClick = {
            CurrentShopResultFragment.show(
                childFragmentManager,
                it,
            )
        },
    )

    override fun onAttach(context: Context) {
        super.onAttach(context)
        FortniteApplication.appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListener()
        initializeRecyclerView()

        lifecycleScope.launch {
            viewModel.currentShopResult.collect {
                handleCurrentShopResult(it)
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
            adapter = headerShopAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun handleCurrentShopResult(result: LoadingState<List<ShopAdapterItem>>) {
        when (result) {
            is LoadingState.Loading -> {
                binding.progressViewInclude.progressContainer.isVisible = true
                binding.viewErrorInclude.errorView.isVisible = false
            }
            is LoadingState.Success -> {
                binding.progressViewInclude.progressContainer.isVisible = false
                headerShopAdapter.updateData(result.data)
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