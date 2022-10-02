package robin.vitalij.fortniteassitant.ui.cosmetics

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
import robin.vitalij.fortniteassitant.FortniteApplication
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.common.extensions.observeToError
import robin.vitalij.fortniteassitant.common.extensions.observeToProgressBar
import robin.vitalij.fortniteassitant.databinding.FragmentRecyclerViewWithToolbarBinding
import robin.vitalij.fortniteassitant.model.enums.ShopType
import robin.vitalij.fortniteassitant.ui.common.BaseFragment
import robin.vitalij.fortniteassitant.ui.cosmetics.adapter.CatalogCosmeticsAdapter
import robin.vitalij.fortniteassitant.ui.cosmetics.catalog.CosmeticsFragment.Companion.SHOP_TYPE
import javax.inject.Inject

private const val MAX_SPAN_COUNT = 2

class CatalogCosmeticsFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: CatalogCosmeticsViewModelFactory

    private lateinit var viewModel: CatalogCosmeticsViewModel

    private var _binding: FragmentRecyclerViewWithToolbarBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecyclerViewWithToolbarBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(viewModelStore, viewModelFactory)
            .get(CatalogCosmeticsViewModel::class.java).apply {
                observeToProgressBar(this@CatalogCosmeticsFragment)
                observeToError(this@CatalogCosmeticsFragment)
            }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        FortniteApplication.appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.mutableLiveData.observe(viewLifecycleOwner) {
            it.let(::initAdapter)
        }

        setListener()
        setNavigation()
    }

    private fun setListener() {
        setErrorResolveButtonClick {
            viewModel.loadData()
        }
    }

    private fun setNavigation() {
        val navController = findNavController()
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        binding.toolbarInclude.toolbar.setupWithNavController(navController, appBarConfiguration)
    }

    private fun initAdapter(list: List<ShopType>) {
        binding.recyclerViewInclude.recyclerView.run {
            adapter = CatalogCosmeticsAdapter {
                val bundle = Bundle().apply {
                    putSerializable(SHOP_TYPE, it)
                }
                findNavController().navigate(R.id.navigation_cosmetics, bundle)
            }
            (adapter as CatalogCosmeticsAdapter).setData(list)
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