package robin.vitalij.fortniteassitant.ui.chartlist

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import robin.vitalij.fortniteassitant.FortniteApplication
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.common.extensions.observeToError
import robin.vitalij.fortniteassitant.common.extensions.observeToProgressBar
import robin.vitalij.fortniteassitant.databinding.FragmentHomeBinding
import robin.vitalij.fortniteassitant.interfaces.ChartsTypeCallback
import robin.vitalij.fortniteassitant.model.enums.BattlesType
import robin.vitalij.fortniteassitant.model.enums.GameType
import robin.vitalij.fortniteassitant.ui.chartlist.adapter.ChartsTypeAdapter
import javax.inject.Inject

class ChartsTypeFragment : Fragment(R.layout.fragment_home) {

    @Inject
    lateinit var viewModelFactory: ChartsTypeViewModelFactory

    private lateinit var viewModel: ChartsTypeViewModel

    private var chartsTypeCallback: ChartsTypeCallback? = null

    private lateinit var binding: FragmentHomeBinding

    private val chartsAdapter = ChartsTypeAdapter {
        arguments?.let { bundle ->
            chartsTypeCallback?.chartsTypeClick(
                it,
                bundle.get(ARG_BATTLES_TYPE) as BattlesType,
                bundle.get(ARG_GAME_TYPE) as GameType
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        FortniteApplication.appComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(
            viewModelStore,
            viewModelFactory
        )[ChartsTypeViewModel::class.java].apply {
            observeToProgressBar(this@ChartsTypeFragment)
            observeToError(this@ChartsTypeFragment)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            viewModel.loadData(
                it.get(ARG_BATTLES_TYPE) as BattlesType,
                it.get(ARG_GAME_TYPE) as GameType
            )
        }

        viewModel.mutableLiveData.observe(viewLifecycleOwner) {
            chartsAdapter.updateData(it)
        }

        initializeRecyclerView()
    }

    private fun initializeRecyclerView() {
        binding.recyclerViewInclude.recyclerView.run {
            adapter = chartsAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    companion object {
        private const val ARG_BATTLES_TYPE = "arg_battles_type"
        private const val ARG_GAME_TYPE = "arg_game_type"

        fun newInstance(
            battlesType: BattlesType,
            gameType: GameType,
            chartsTypeCallback: ChartsTypeCallback
        ) = ChartsTypeFragment().apply {
            this.chartsTypeCallback = chartsTypeCallback
            arguments = bundleOf(
                ARG_GAME_TYPE to gameType,
                ARG_BATTLES_TYPE to battlesType
            )
        }
    }
}