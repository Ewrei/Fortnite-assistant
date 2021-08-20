package robin.vitalij.fortniteassitant.ui.achiviements

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
import kotlinx.android.synthetic.main.view_error.*
import robin.vitalij.fortniteassitant.FortniteApplication
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.common.extensions.observeToError
import robin.vitalij.fortniteassitant.common.extensions.observeToProgressBar
import robin.vitalij.fortniteassitant.db.entity.AchievementEntity
import robin.vitalij.fortniteassitant.ui.achiviements.adapter.AchievementsAdapter
import robin.vitalij.fortniteassitant.ui.common.BaseFragment
import javax.inject.Inject


class AchievementsFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: AchievementsViewModelFactory

    private lateinit var viewModel: AchievementsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = inflater.inflate(R.layout.fragment_history, container, false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(viewModelStore, viewModelFactory)
            .get(AchievementsViewModel::class.java).apply {
                observeToProgressBar(this@AchievementsFragment)
                observeToError(this@AchievementsFragment)
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
    }

    private fun setListener() {
        errorResolveButton.setOnClickListener {
            viewModel.loadData()
        }
    }

    private fun setNavigation() {
        val navController = findNavController()
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        toolbar.setupWithNavController(navController, appBarConfiguration)
    }

    private fun initAdapter(list: List<AchievementEntity>) {
        recyclerView.run {
            adapter = AchievementsAdapter()
            (adapter as AchievementsAdapter).setData(list)
            layoutManager = LinearLayoutManager(context)
        }
    }
}