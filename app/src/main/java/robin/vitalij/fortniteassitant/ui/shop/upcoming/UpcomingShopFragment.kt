package robin.vitalij.fortniteassitant.ui.shop.upcoming

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.recycler_view.*
import robin.vitalij.fortniteassitant.FortniteApplication
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.common.extensions.observeToError
import robin.vitalij.fortniteassitant.common.extensions.observeToProgressBar
import robin.vitalij.fortniteassitant.model.network.shop.ItemShopUpcoming
import robin.vitalij.fortniteassitant.ui.bottomsheet.upcomingshop.UpcomingShopResultFragment
import robin.vitalij.fortniteassitant.ui.common.BaseFragment
import robin.vitalij.fortniteassitant.ui.shop.upcoming.adapter.UpcomingShopAdapter
import javax.inject.Inject


class UpcomingShopFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: UpcomingShopViewModelFactory

    private lateinit var viewModel: UpcomingShopViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = inflater.inflate(R.layout.fragment_achievements, container, false)

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
        viewModel.mutableLiveData.observe(viewLifecycleOwner, Observer {
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
        recyclerView.run {
            adapter = UpcomingShopAdapter {
                UpcomingShopResultFragment.show(
                    childFragmentManager,
                    it,)
            }
            (adapter as UpcomingShopAdapter).setData(list)
            layoutManager = LinearLayoutManager(context)
        }
    }
}