package robin.vitalij.fortniteassitant.ui.shop.upcoming

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import robin.vitalij.fortniteassitant.FortniteApplication
import robin.vitalij.fortniteassitant.common.extensions.observeToError
import robin.vitalij.fortniteassitant.common.extensions.observeToProgressBar
import robin.vitalij.fortniteassitant.databinding.FragmentRecyclerViewBinding
import robin.vitalij.fortniteassitant.model.network.shop.ItemShopUpcoming
import robin.vitalij.fortniteassitant.ui.bottomsheet.upcomingshop.UpcomingShopResultFragment
import robin.vitalij.fortniteassitant.ui.common.BaseFragment
import robin.vitalij.fortniteassitant.ui.shop.upcoming.adapter.UpcomingShopAdapter
import javax.inject.Inject


class UpcomingShopFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: UpcomingShopViewModelFactory

    private lateinit var viewModel: UpcomingShopViewModel

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
            .get(UpcomingShopViewModel::class.java).apply {
                observeToProgressBar(this@UpcomingShopFragment)
                observeToError(this@UpcomingShopFragment)
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

    private fun initAdapter(list: List<ItemShopUpcoming>) {
        binding.recyclerViewInclude.recyclerView.run {
            adapter = UpcomingShopAdapter {
                UpcomingShopResultFragment.show(
                    childFragmentManager,
                    it,
                )
            }
            (adapter as UpcomingShopAdapter).setData(list)
            layoutManager = LinearLayoutManager(context)
        }
    }
}