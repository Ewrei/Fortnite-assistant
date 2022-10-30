package robin.vitalij.fortniteassitant.ui.bottomsheet.banner

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
import robin.vitalij.fortniteassitant.db.entity.BannerEntity
import robin.vitalij.fortniteassitant.model.ErrorModelListItem
import robin.vitalij.fortniteassitant.model.LoadingState
import robin.vitalij.fortniteassitant.ui.bottomsheet.banner.adapter.BannerResultAdapter
import javax.inject.Inject

class BannerResultFragment : BottomSheetDialogFragment() {

    @Inject
    lateinit var viewModelFactory: BannerResultViewModelFactory

    private val viewModel: BannerResultViewModel by viewModels { viewModelFactory }

    private var _binding: BottomSheetMvvmBinding? = null

    private val binding get() = _binding!!

    private val bannerResultAdapter = BannerResultAdapter()

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
            viewModel.bannerId = it.getString(ARG_BANNER_ID) ?: ""
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListener()
        initializeRecyclerView()

        lifecycleScope.launch {
            viewModel.bannerResult.collect {
                handleBannerResult(it)
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

    private fun initializeRecyclerView() {
        binding.recyclerViewInclude.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = bannerResultAdapter
        }
    }

    private fun handleBannerResult(result: LoadingState<List<BannerEntity>>) {
        when (result) {
            is LoadingState.Loading -> {
                binding.progressViewInclude.progressContainer.isVisible = true
                binding.errorViewInclude.errorView.isVisible = false
            }
            is LoadingState.Success -> {
                binding.progressViewInclude.progressContainer.isVisible = false
                bannerResultAdapter.updateData(result.data)
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

        private const val TAG = "BannerResultFragment"
        private const val ARG_BANNER_ID = "arg_banner_id"
        private const val BOTTOM_SHEET_MARGIN_TOP = 200

        fun show(fragmentManager: FragmentManager, bannerId: String) {
            BannerResultFragment().apply {
                arguments = bundleOf(ARG_BANNER_ID to bannerId)
            }.show(fragmentManager, TAG)
        }
    }
}
