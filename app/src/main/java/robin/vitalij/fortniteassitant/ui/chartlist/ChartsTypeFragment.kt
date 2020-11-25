package robin.vitalij.fortniteassitant.ui.chartlist

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.recycler_view.*
import kotlinx.android.synthetic.main.toolbar_center_title.*
import robin.vitalij.fortniteassitant.FortniteApplication
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.common.extensions.observeToError
import robin.vitalij.fortniteassitant.common.extensions.observeToProgressBar
import robin.vitalij.fortniteassitant.model.enums.ChartsType
import robin.vitalij.fortniteassitant.ui.chartlist.adapter.ChartsTypeAdapter
import robin.vitalij.fortniteassitant.ui.charts.ChartsFragment.Companion.CHARTS_TYPE
import robin.vitalij.fortniteassitant.ui.common.BaseFragment
import javax.inject.Inject

class ChartsTypeFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ChartsTypeViewModelFactory

    private lateinit var viewModel: ChartsTypeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = inflater.inflate(R.layout.fragment_map, container, false)

    override fun onAttach(context: Context) {
        super.onAttach(context)
        FortniteApplication.appComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(viewModelStore, viewModelFactory)
            .get(ChartsTypeViewModel::class.java).apply {
                observeToProgressBar(this@ChartsTypeFragment)
                observeToError(this@ChartsTypeFragment)
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ChartsType.values().let(::initAdapter)
        setNavigation()
    }

    private fun setNavigation() {
        val navController = findNavController()
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        toolbar.setupWithNavController(navController, appBarConfiguration)
    }

    private fun initAdapter(list: Array<ChartsType>) {
        recyclerView.run {
            adapter = ChartsTypeAdapter {
                val navController = findNavController()

                val bundle = Bundle().apply {
                    putSerializable(CHARTS_TYPE, it)
                }
                navController.navigate(R.id.navigation_charts, bundle)
            }
            (adapter as ChartsTypeAdapter).setData(list.toList())
            layoutManager = LinearLayoutManager(context)
        }
    }
}