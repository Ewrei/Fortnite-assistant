package robin.vitalij.fortniteassitant.ui.banners

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
import robin.vitalij.fortniteassitant.ui.banners.adapter.BannersAdapter
import robin.vitalij.fortniteassitant.ui.bottomsheet.banner.BannerResultFragment
import robin.vitalij.fortniteassitant.ui.common.BaseFragment
import javax.inject.Inject

class BannersFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: BannersViewModelFactory

    private lateinit var viewModel: BannersViewModel

    private lateinit var binding: FragmentRecyclerViewWithToolbarBinding

    private var bannersAdapter = BannersAdapter {
        BannerResultFragment.show(childFragmentManager, it.id)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        FortniteApplication.appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRecyclerViewWithToolbarBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(viewModelStore, viewModelFactory)
            .get(BannersViewModel::class.java).apply {
                observeToProgressBar(this@BannersFragment)
                observeToError(this@BannersFragment)
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListener()
        setNavigation()
        initializeRecyclerView()

        viewModel.loadData()

        viewModel.mutableLiveData.observe(viewLifecycleOwner) {
            bannersAdapter.updateData(it)
        }
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

    companion object {
        private const val MAX_SPAN_COUNT = 2
        private const val BANNER_SPAN_COUNT = 1
    }
}