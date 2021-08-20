package robin.vitalij.fortniteassitant.ui.chartlist

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.recycler_view.*
import kotlinx.android.synthetic.main.toolbar_center_title.*
import robin.vitalij.fortniteassitant.FortniteApplication
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.common.extensions.observeToError
import robin.vitalij.fortniteassitant.common.extensions.observeToProgressBar
import robin.vitalij.fortniteassitant.interfaces.ChartsTypeCallback
import robin.vitalij.fortniteassitant.model.enums.BattlesType
import robin.vitalij.fortniteassitant.model.enums.ChartsType
import robin.vitalij.fortniteassitant.model.enums.GameType
import robin.vitalij.fortniteassitant.ui.chartlist.adapter.ChartsTypeAdapter
import robin.vitalij.fortniteassitant.ui.common.BaseFragment
import javax.inject.Inject

class ChartsTypeFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ChartsTypeViewModelFactory

    private lateinit var viewModel: ChartsTypeViewModel

    private var chartsTypeCallback: ChartsTypeCallback? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = inflater.inflate(R.layout.fragment_home, container, false)

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

        arguments?.let {
            viewModel.loadData(
                it.get(BATTLES_TYPE) as BattlesType,
                it.get(GAME_TYPE) as GameType
            )
        }

        viewModel.mutableLiveData.observe(viewLifecycleOwner, {
            it.let(::initAdapter)
        })
    }

    private fun initAdapter(list: List<ChartsType>) {
        recyclerView.run {
            adapter = ChartsTypeAdapter {
                arguments?.let { bundle ->
                    chartsTypeCallback?.chartsTypeClick(
                        it,
                        bundle.get(BATTLES_TYPE) as BattlesType,
                        bundle.get(GAME_TYPE) as GameType
                    )
                }
            }
            (adapter as ChartsTypeAdapter).setData(list)
            layoutManager = LinearLayoutManager(context)
        }
    }

    companion object {
        private const val BATTLES_TYPE = "battles_type"
        private const val GAME_TYPE = "game_type"

        fun newInstance(
            battlesType: BattlesType,
            gameType: GameType,
            chartsTypeCallback: ChartsTypeCallback
        ) =
            ChartsTypeFragment().apply {
                this.chartsTypeCallback = chartsTypeCallback
                arguments = Bundle().apply {
                    putSerializable(GAME_TYPE, gameType)
                    putSerializable(BATTLES_TYPE, battlesType)
                }
            }
    }
}