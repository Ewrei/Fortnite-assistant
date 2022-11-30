package robin.vitalij.fortniteassitant.ui.charts_type

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import robin.vitalij.fortniteassitant.FortniteApplication
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.databinding.FragmentHomeBinding
import robin.vitalij.fortniteassitant.interfaces.ChartsTypeCallback
import robin.vitalij.fortniteassitant.model.enums.BattlesType
import robin.vitalij.fortniteassitant.model.enums.GameType
import robin.vitalij.fortniteassitant.ui.charts_type.adapter.ChartsTypeAdapter
import robin.vitalij.fortniteassitant.utils.mapper.ChartsTypeMapper
import javax.inject.Inject

class ChartsTypeFragment : Fragment(R.layout.fragment_home) {

    @Inject
    lateinit var viewModelFactory: ChartsTypeViewModelFactory

    private val viewModel: ChartsTypeViewModel by viewModels { viewModelFactory }

    private val binding by viewBinding(FragmentHomeBinding::bind)

    private var chartsTypeCallback: ChartsTypeCallback? = null

    private val chartsAdapter = ChartsTypeAdapter {
        arguments?.let { bundle ->
            chartsTypeCallback?.chartsTypeClick(
                it,
                bundle.get(ARG_BATTLES_TYPE) as BattlesType,
                bundle.get(ARG_GAME_TYPE) as GameType
            )
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        FortniteApplication.appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeRecyclerView()

        arguments?.let {
            chartsAdapter.submitList(
                ChartsTypeMapper(it.get(ARG_GAME_TYPE) as GameType).transform(
                    it.get(ARG_BATTLES_TYPE) as BattlesType
                )
            )
        }
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