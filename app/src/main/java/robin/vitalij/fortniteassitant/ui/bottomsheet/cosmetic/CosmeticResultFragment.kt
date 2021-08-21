package robin.vitalij.fortniteassitant.ui.bottomsheet.cosmetic

import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.recycler_view.*
import robin.vitalij.fortniteassitant.FortniteApplication
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.common.extensions.getScreenWidth
import robin.vitalij.fortniteassitant.ui.bottomsheet.cosmetic.adapter.CosmeticsResultAdapter
import robin.vitalij.fortniteassitant.ui.bottomsheet.cosmetic.adapter.viewmodel.Cosmetics
import javax.inject.Inject

const val BOTTOM_SHEET_MARGIN_TOP = 200
private const val WIDTH_PIXELS_PERCENT = 0.35

class CosmeticResultFragment : BottomSheetDialogFragment() {

    @Inject
    lateinit var viewModelFactory: CosmeticResultViewModelFactory

    private lateinit var viewModel: CosmeticResultViewModel

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
        return inflater.inflate(R.layout.bottom_sheet_mvvm, container, false)
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
                it.getString(COSMETIC_ID) ?: "",
                it.getBoolean(IS_COSMETIC_NEW, false)
            )
        }

        viewModel.mutableLiveData.observe(viewLifecycleOwner, {
            it.let(::initAdapter)
        })
    }

    override fun onStart() {
        super.onStart()
        val displayMetrics = DisplayMetrics()
        activity?.windowManager?.defaultDisplay?.getMetrics(displayMetrics)
        val sheetContainer = requireView().parent as? ViewGroup ?: return
        sheetContainer.layoutParams.height = (displayMetrics.heightPixels - BOTTOM_SHEET_MARGIN_TOP)
    }

    private fun initAdapter(list: List<Cosmetics>) {
        recyclerView.run {
            adapter = CosmeticsResultAdapter(
                layoutInflater,
                activity?.getScreenWidth(WIDTH_PIXELS_PERCENT) ?: 0
            )
            (adapter as CosmeticsResultAdapter).setData(list)
            layoutManager = LinearLayoutManager(context)
        }
    }

    companion object {

        private const val TAG = "CosmeticResultFragment"
        private const val COSMETIC_ID = "cosmetic_id"
        private const val IS_COSMETIC_NEW = "is_cosmetic_new"

        fun show(
            fragmentManager: FragmentManager?,
            cosmeticId: String,
            isCosmeticNew: Boolean
        ) {
            fragmentManager?.let {
                CosmeticResultFragment().apply {
                    arguments = Bundle().apply {
                        putString(COSMETIC_ID, cosmeticId)
                        putBoolean(IS_COSMETIC_NEW, isCosmeticNew)
                    }
                }.show(
                    it,
                    TAG
                )
            }
        }
    }
}
