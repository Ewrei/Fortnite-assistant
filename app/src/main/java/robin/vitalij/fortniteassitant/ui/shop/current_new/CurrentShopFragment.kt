package robin.vitalij.fortniteassitant.ui.shop.current_new

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import robin.vitalij.fortniteassitant.FortniteApplication
import robin.vitalij.fortniteassitant.common.extensions.observeToError
import robin.vitalij.fortniteassitant.common.extensions.observeToProgressBar
import robin.vitalij.fortniteassitant.databinding.FragmentRecyclerViewBinding
import robin.vitalij.fortniteassitant.model.network.shop.ShopNewItem
import robin.vitalij.fortniteassitant.ui.bottomsheet.currentshop.CurrentShopResultFragment
import robin.vitalij.fortniteassitant.ui.common.BaseFragment
import robin.vitalij.fortniteassitant.ui.shop.current_new.adapter.CurrentShopAdapter
import robin.vitalij.fortniteassitant.utils.GridSpacingItemDecoration
import javax.inject.Inject

class CurrentShopFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: CurrentShopViewModelFactory

    private lateinit var viewModel: CurrentShopViewModel

    private var _binding: FragmentRecyclerViewBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecyclerViewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(viewModelStore, viewModelFactory)
            .get(CurrentShopViewModel::class.java).apply {
                observeToProgressBar(this@CurrentShopFragment)
                observeToError(this@CurrentShopFragment)
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

        viewModel.loadData()
        setListener()
    }

    private fun setListener() {
        setErrorResolveButtonClick {
            viewModel.loadData()
        }
    }

    private fun initAdapter(list: List<ShopNewItem>) {
        binding.recyclerViewInclude.recyclerView.run {
            adapter = CurrentShopAdapter(
                onClick = {
                    CurrentShopResultFragment.show(
                        childFragmentManager,
                        it,
                    )
                },
            )
            (adapter as CurrentShopAdapter).setData(list)

            layoutManager = GridLayoutManager(requireContext(), MAX_SPAN_COUNT)
            addItemDecoration(GridSpacingItemDecoration(MAX_SPAN_COUNT, 20, true))
        }
    }

    companion object {
        private const val MAX_SPAN_COUNT = 2
    }
}