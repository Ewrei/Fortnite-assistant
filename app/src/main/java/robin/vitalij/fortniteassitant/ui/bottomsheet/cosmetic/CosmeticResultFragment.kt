package robin.vitalij.fortniteassitant.ui.bottomsheet.cosmetic

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
import robin.vitalij.fortniteassitant.common.extensions.getScreenWidth
import robin.vitalij.fortniteassitant.common.extensions.initBottomSheetInternal
import robin.vitalij.fortniteassitant.common.extensions.setErrorView
import robin.vitalij.fortniteassitant.databinding.BottomSheetMvvmBinding
import robin.vitalij.fortniteassitant.model.ErrorModelListItem
import robin.vitalij.fortniteassitant.model.LoadingState
import robin.vitalij.fortniteassitant.ui.bottomsheet.cosmetic.adapter.CosmeticsListItem
import robin.vitalij.fortniteassitant.ui.bottomsheet.cosmetic.adapter.CosmeticsResultAdapter
import javax.inject.Inject

const val BOTTOM_SHEET_MARGIN_TOP = 200
private const val WIDTH_PIXELS_PERCENT = 0.35

class CosmeticResultFragment : BottomSheetDialogFragment() {

    @Inject
    lateinit var viewModelFactory: CosmeticResultViewModelFactory

    private val viewModel: CosmeticResultViewModel by viewModels { viewModelFactory }

    private var _binding: BottomSheetMvvmBinding? = null

    private val binding get() = _binding!!

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
            viewModel.cosmeticsId = it.getString(ARG_COSMETIC_ID) ?: ""
            viewModel.isCosmeticNew = it.getBoolean(ARG_IS_COSMETIC_NEW, false)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListener()

        lifecycleScope.launch {
            viewModel.cosmeticsResult.collect {
                handleCosmeticsResult(it)
            }
        }

        viewModel.loadData()
    }

    override fun onStart() {
        super.onStart()
        val displayMetrics = DisplayMetrics()
        activity?.windowManager?.defaultDisplay?.getMetrics(displayMetrics)
        val sheetContainer = requireView().parent as? ViewGroup ?: return
        sheetContainer.layoutParams.height = (displayMetrics.heightPixels - BOTTOM_SHEET_MARGIN_TOP)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setListener() {
        binding.errorViewInclude.errorResolveButton.setOnClickListener {
            viewModel.loadData()
        }
    }

    private fun handleCosmeticsResult(result: LoadingState<List<CosmeticsListItem>>) {
        when (result) {
            is LoadingState.Loading -> {
                binding.progressViewInclude.progressContainer.isVisible = true
                binding.errorViewInclude.errorView.isVisible = false
            }
            is LoadingState.Success -> {
                binding.progressViewInclude.progressContainer.isVisible = false
                result.data.let(::initAdapter)
            }
            is LoadingState.Error -> {
                binding.progressViewInclude.progressContainer.isVisible = false

                if (result.cause is ErrorModelListItem.ErrorItem) {
                    binding.errorViewInclude.setErrorView(result.cause.errorModel)
                }
            }
        }
    }


    private fun initAdapter(list: List<CosmeticsListItem>) {
        binding.recyclerViewInclude.recyclerView.run {
            adapter = CosmeticsResultAdapter(
                layoutInflater,
                activity?.getScreenWidth(WIDTH_PIXELS_PERCENT) ?: 0
            )
            (adapter as CosmeticsResultAdapter).updateData(list)
            layoutManager = LinearLayoutManager(context)
        }
    }

    companion object {

        private const val TAG = "CosmeticResultFragment"
        private const val ARG_COSMETIC_ID = "arg_cosmetic_id"
        private const val ARG_IS_COSMETIC_NEW = "arg_is_cosmetic_new"

        fun show(
            fragmentManager: FragmentManager,
            cosmeticId: String,
            isCosmeticNew: Boolean
        ) {
            CosmeticResultFragment().apply {
                arguments = bundleOf(
                    ARG_COSMETIC_ID to cosmeticId,
                    ARG_IS_COSMETIC_NEW to isCosmeticNew
                )
            }.show(fragmentManager, TAG)
        }
    }
}
