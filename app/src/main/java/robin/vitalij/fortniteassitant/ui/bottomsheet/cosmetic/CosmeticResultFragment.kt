package robin.vitalij.fortniteassitant.ui.bottomsheet.cosmetic

import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import robin.vitalij.fortniteassitant.FortniteApplication
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.common.extensions.getScreenWidth
import robin.vitalij.fortniteassitant.databinding.BottomSheetMvvmBinding
import robin.vitalij.fortniteassitant.ui.bottomsheet.cosmetic.adapter.CosmeticsListItem
import robin.vitalij.fortniteassitant.ui.bottomsheet.cosmetic.adapter.CosmeticsResultAdapter
import javax.inject.Inject

const val BOTTOM_SHEET_MARGIN_TOP = 200
private const val WIDTH_PIXELS_PERCENT = 0.35

class CosmeticResultFragment : BottomSheetDialogFragment() {

    @Inject
    lateinit var viewModelFactory: CosmeticResultViewModelFactory

    private lateinit var viewModel: CosmeticResultViewModel

    private lateinit var binding: BottomSheetMvvmBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dialog?.setOnShowListener { dialog ->
            val d = dialog as BottomSheetDialog
            val bottomSheetInternal =
                d.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            bottomSheetInternal?.setBackgroundResource(R.drawable.bottomsheet_container_background)
            bottomSheetInternal?.let {
                BottomSheetBehavior.from(it).state = BottomSheetBehavior.STATE_EXPANDED
                BottomSheetBehavior.from(it).skipCollapsed = true
            }
        }
        binding = BottomSheetMvvmBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FortniteApplication.appComponent.inject(this)
        viewModel = ViewModelProvider(viewModelStore, viewModelFactory)
            .get(CosmeticResultViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            viewModel.loadData(
                it.getString(ARG_COSMETIC_ID) ?: "",
                it.getBoolean(ARG_IS_COSMETIC_NEW, false)
            )
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
            fragmentManager: FragmentManager?,
            cosmeticId: String,
            isCosmeticNew: Boolean
        ) {
            fragmentManager?.let {
                CosmeticResultFragment().apply {
                    arguments = bundleOf(
                        ARG_COSMETIC_ID to cosmeticId,
                        ARG_IS_COSMETIC_NEW to isCosmeticNew
                    )
                }.show(it, TAG)
            }
        }
    }
}
