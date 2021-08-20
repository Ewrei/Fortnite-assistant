package robin.vitalij.fortniteassitant.ui.cosmetics.catalog

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
import robin.vitalij.fortniteassitant.common.extensions.observeToError
import robin.vitalij.fortniteassitant.common.extensions.observeToProgressBar
import robin.vitalij.fortniteassitant.databinding.FragmentRecyclerViewWithToolbarBinding
import robin.vitalij.fortniteassitant.db.entity.CosmeticsEntity
import robin.vitalij.fortniteassitant.model.enums.ShopType
import robin.vitalij.fortniteassitant.ui.bottomsheet.cosmetic.CosmeticResultFragment
import robin.vitalij.fortniteassitant.ui.common.BaseFragment
import robin.vitalij.fortniteassitant.ui.cosmetics.catalog.adapter.CosmeticsAdapter
import javax.inject.Inject

private const val MAX_SPAN_COUNT = 2

class CosmeticsFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: CosmeticsViewModelFactory

    private lateinit var viewModel: CosmeticsViewModel

    private var _binding: FragmentRecyclerViewWithToolbarBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecyclerViewWithToolbarBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(viewModelStore, viewModelFactory)
            .get(CosmeticsViewModel::class.java).apply {
                observeToProgressBar(this@CosmeticsFragment)
                observeToError(this@CosmeticsFragment)
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

        setListener()
        setNavigation()

        arguments?.let {
            val shopType = it.getSerializable(SHOP_TYPE) as ShopType
            binding.toolbarInclude.toolbar.title = getString(shopType.getTitleRes())
            viewModel.loadData(shopType)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setListener() {
        setErrorResolveButtonClick {
            arguments?.let {
                val shopType = it.getSerializable(SHOP_TYPE) as ShopType
                viewModel.loadData(shopType)
            }
        }
    }

    private fun setNavigation() {
        val navController = findNavController()
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        binding.toolbarInclude.toolbar.setupWithNavController(navController, appBarConfiguration)
    }

    private fun initAdapter(list: List<CosmeticsEntity>) {
        binding.recyclerViewInclude.recyclerView.run {
            adapter = CosmeticsAdapter {
                CosmeticResultFragment.show(childFragmentManager, it.id, false)
            }
            (adapter as CosmeticsAdapter).setData(list)
            val gridlayoutManager = GridLayoutManager(
                activity, MAX_SPAN_COUNT
            )

            gridlayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int) = 1
            }

            layoutManager = gridlayoutManager
        }
    }

    companion object {
        const val SHOP_TYPE = "shop_type"
    }
}