package robin.vitalij.fortniteassitant.ui.shop.current

import android.content.Context
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.recycler_view.*
import robin.vitalij.fortniteassitant.FortniteApplication
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.common.extensions.observeToError
import robin.vitalij.fortniteassitant.common.extensions.observeToProgressBar
import robin.vitalij.fortniteassitant.ui.bottomsheet.currentshop.CurrentShopResultFragment
import robin.vitalij.fortniteassitant.ui.common.BaseFragment
import robin.vitalij.fortniteassitant.ui.shop.current.adapter.CurrentShopAdapter
import robin.vitalij.fortniteassitant.ui.shop.current.adapter.viewmodel.CurrentShopImpl
import robin.vitalij.fortniteassitant.ui.shop.current.adapter.viewmodel.CurrentShopType
import javax.inject.Inject

private const val MAX_SPAN_COUNT = 2

class CurrentShopFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: CurrentShopViewModelFactory

    private lateinit var viewModel: CurrentShopViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = inflater.inflate(R.layout.fragment_achievements, container, false)

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

    private fun initAdapter(list: List<CurrentShopImpl>) {
        val displayMetrics = DisplayMetrics()
        activity?.windowManager?.defaultDisplay?.getMetrics(displayMetrics)
        val widthPixels = displayMetrics.widthPixels * 0.35

        recyclerView.run {
            adapter = CurrentShopAdapter(
                onClick = {
                    CurrentShopResultFragment.show(
                        childFragmentManager,
                        it,
                    )
                },
                widthPixels = widthPixels.toInt()
            )
            (adapter as CurrentShopAdapter).setData(list)

            val gridlayoutManager = GridLayoutManager(
                activity, MAX_SPAN_COUNT
            )

            gridlayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return when (adapter?.getItemViewType(position)) {
                        CurrentShopType.SHOP_ITEM.id -> 1
                        else -> MAX_SPAN_COUNT
                    }
                }
            }
            layoutManager = gridlayoutManager
        }
    }
}