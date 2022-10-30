package robin.vitalij.fortniteassitant.ui.bottomsheet.fish

import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import robin.vitalij.fortniteassitant.FortniteApplication
import robin.vitalij.fortniteassitant.common.extensions.initBottomSheetInternal
import robin.vitalij.fortniteassitant.common.extensions.observeToError
import robin.vitalij.fortniteassitant.common.extensions.observeToProgressBar
import robin.vitalij.fortniteassitant.databinding.BottomSheetMvvmBinding
import robin.vitalij.fortniteassitant.db.entity.FishEntity
import robin.vitalij.fortniteassitant.ui.bottomsheet.fish.adapter.FishResultAdapter
import javax.inject.Inject

const val BOTTOM_SHEET_MARGIN_TOP = 200

class FishResultFragment : BottomSheetDialogFragment() {

    @Inject
    lateinit var viewModelFactory: FishResultViewModelFactory

    private lateinit var viewModel: FishResultViewModel

    private lateinit var binding: BottomSheetMvvmBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dialog?.initBottomSheetInternal()
        binding = BottomSheetMvvmBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FortniteApplication.appComponent.inject(this)
        viewModel = ViewModelProvider(viewModelStore, viewModelFactory)
            .get(FishResultViewModel::class.java).apply {
                observeToProgressBar(this@FishResultFragment)
                observeToError(this@FishResultFragment)
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            viewModel.loadData((it.getString(ARG_FISH_ID) ?: ""))
        }

        viewModel.mutableLiveData.observe(viewLifecycleOwner) {
            it.let(::initAdapter)
        }
    }

    override fun onStart() {
        super.onStart()
        val displayMetrics = DisplayMetrics()
        activity?.windowManager?.defaultDisplay?.getMetrics(displayMetrics)
        val sheetContainer = requireView().parent as? ViewGroup ?: return
        sheetContainer.layoutParams.height = (displayMetrics.heightPixels - BOTTOM_SHEET_MARGIN_TOP)
    }

    private fun initAdapter(list: List<FishEntity>) {
        binding.recyclerViewInclude.recyclerView.run {
            adapter = FishResultAdapter()
            (adapter as FishResultAdapter).setData(list)
            layoutManager = LinearLayoutManager(context)
        }
    }

    companion object {

        private const val TAG = "FishResultFragment"
        private const val ARG_FISH_ID = "arg_fish_id"

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
