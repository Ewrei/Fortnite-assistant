package robin.vitalij.fortniteassitant.ui.bottomsheet.fish

import android.content.Context
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import robin.vitalij.fortniteassitant.FortniteApplication
import robin.vitalij.fortniteassitant.common.extensions.initBottomSheetInternal
import robin.vitalij.fortniteassitant.common.extensions.setErrorView
import robin.vitalij.fortniteassitant.databinding.BottomSheetMvvmBinding
import robin.vitalij.fortniteassitant.db.entity.FishEntity
import robin.vitalij.fortniteassitant.model.ErrorModelListItem
import robin.vitalij.fortniteassitant.model.LoadingState
import robin.vitalij.fortniteassitant.ui.bottomsheet.fish.adapter.FishResultAdapter
import javax.inject.Inject

class FishResultFragment : BottomSheetDialogFragment() {

    @Inject
    lateinit var viewModelFactory: FishResultViewModelFactory

    private val viewModel: FishResultViewModel by viewModels { viewModelFactory }

    private var _binding: BottomSheetMvvmBinding? = null

    private val binding get() = _binding!!

    private val fishAdapter = FishResultAdapter()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        FortniteApplication.appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dialog?.initBottomSheetInternal()
        _binding = BottomSheetMvvmBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            viewModel.fishId = it.getString(ARG_FISH_ID) ?: ""
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListener()
        initializeRecyclerView()

        lifecycleScope.launch {
            viewModel.fishResult.collect {
                handleFishResult(it)
            }
        }

        viewModel.loadData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onStart() {
        super.onStart()
        val displayMetrics = DisplayMetrics()
        activity?.windowManager?.defaultDisplay?.getMetrics(displayMetrics)
        val sheetContainer = requireView().parent as? ViewGroup ?: return
        sheetContainer.layoutParams.height = (displayMetrics.heightPixels - BOTTOM_SHEET_MARGIN_TOP)
    }

    private fun setListener() {
        binding.errorViewInclude.errorResolveButton.setOnClickListener {
            viewModel.loadData()
        }
    }

    private fun initializeRecyclerView() {
        binding.recyclerViewInclude.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = fishAdapter
        }
    }

    private fun handleFishResult(result: LoadingState<FishEntity>) {
        when (result) {
            is LoadingState.Loading -> {
                binding.progressViewInclude.progressContainer.isVisible = true
                binding.errorViewInclude.errorView.isVisible = false
            }
            is LoadingState.Success -> {
                binding.progressViewInclude.progressContainer.isVisible = false
                fishAdapter.updateData(mutableListOf(result.data))
            }
            is LoadingState.Error -> {
                binding.progressViewInclude.progressContainer.isVisible = false

                if (result.cause is ErrorModelListItem.ErrorItem) {
                    binding.errorViewInclude.setErrorView(result.cause.errorModel)
                }
            }
        }
    }

    companion object {
        private const val TAG = "FishResultFragment"
        private const val ARG_FISH_ID = "arg_fish_id"
        private const val BOTTOM_SHEET_MARGIN_TOP = 200

        fun show(
            fragmentManager: FragmentManager,
            fishId: String
        ) {
            FishResultFragment().apply {
                arguments = bundleOf(ARG_FISH_ID to fishId)
            }.show(fragmentManager, TAG)
        }
    }
}
