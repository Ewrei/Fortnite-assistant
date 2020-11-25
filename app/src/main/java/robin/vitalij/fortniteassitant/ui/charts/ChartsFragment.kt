package robin.vitalij.fortniteassitant.ui.charts

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.fragment_charts.*
import kotlinx.android.synthetic.main.toolbar_center_title.*
import kotlinx.android.synthetic.main.view_no_subscription.*
import kotlinx.android.synthetic.main.view_no_subscription.empty
import robin.vitalij.fortniteassitant.FortniteApplication
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.common.binding.LineChartBinding.setSession
import robin.vitalij.fortniteassitant.common.extensions.*
import robin.vitalij.fortniteassitant.model.SessionModel
import robin.vitalij.fortniteassitant.model.enums.ChartsType
import robin.vitalij.fortniteassitant.ui.common.BaseFragment
import robin.vitalij.fortniteassitant.ui.main.MainActivity
import robin.vitalij.fortniteassitant.ui.subscription.SubscriptionActivity
import javax.inject.Inject

private const val ONE_SESSION = 1

class ChartsFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ChartsViewModelFactory

    private lateinit var viewModel: ChartsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = inflater.inflate(R.layout.fragment_charts, container, false)

    override fun onAttach(context: Context) {
        super.onAttach(context)
        FortniteApplication.appComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel =
            ViewModelProvider(viewModelStore, viewModelFactory).get(ChartsViewModel::class.java)
                .apply {
                    observeToProgressBar(this@ChartsFragment)
                    observeToError(this@ChartsFragment)
                    observeToSubscriptionAccess(this@ChartsFragment)
                }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.mutableLiveData.observe(viewLifecycleOwner, Observer {
            arcProgress.setAnimatedProgress(it.header)
            it.sessionModels.let(::initAdapter)
        })

        setNavigation()
        initToolbar()
        setListeners()

        arguments?.let {
            viewModel.loadData(it.getSerializable(CHARTS_TYPE) as ChartsType)
            arcProgress.setBottomText(getString((it.getSerializable(CHARTS_TYPE) as ChartsType).getTitleShortRes()))
            arcProgress.max = (it.getSerializable(CHARTS_TYPE) as ChartsType).getProgressMax()
        }
    }

    private fun setListeners() {
        subscribe.setOnClickListener {
            startActivity(SubscriptionActivity.newInstance(context))
        }

        watchVideoAds.setOnClickListener {
            (activity as? MainActivity)?.onDisplayButtonClicked {
                viewModel.checkSubscriptionAccess()
            }
        }
    }

    private fun initToolbar() {
        arguments?.let {
            toolbar.title = getString((it.getSerializable(CHARTS_TYPE) as ChartsType).getTitleRes())
        }
    }

    private fun setNavigation() {
        val navController = findNavController()
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        toolbar.setupWithNavController(navController, appBarConfiguration)
    }

    private fun initAdapter(list: List<SessionModel>) {
        сhart.setInVisibility(list.size > ONE_SESSION)
        empty.setVisibility(list.size <= ONE_SESSION)
        if (list.size > ONE_SESSION) {
            сhart.setSession(list)
        }
    }

    companion object {
        const val CHARTS_TYPE = "charts_type"

        fun newInstance() = ChartsFragment()
    }
}