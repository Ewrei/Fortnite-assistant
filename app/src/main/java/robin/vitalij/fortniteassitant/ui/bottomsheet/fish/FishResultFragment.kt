package robin.vitalij.fortniteassitant.ui.bottomsheet.fish

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
import robin.vitalij.fortniteassitant.db.entity.FishEntity
import robin.vitalij.fortniteassitant.ui.bottomsheet.fish.adapter.FishResultAdapter
import javax.inject.Inject

const val BOTTOM_SHEET_MARGIN_TOP = 200

class FishResultFragment : BottomSheetDialogFragment() {

    @Inject
    lateinit var viewModelFactory: FishResultViewModelFactory

    private lateinit var viewModel: FishResultViewModel

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
        return inflater.inflate(R.layout.bottom_sheet_recyclerview, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FortniteApplication.appComponent.inject(this)
        viewModel = ViewModelProvider(viewModelStore, viewModelFactory)
            .get(FishResultViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            val list = arrayListOf(it.getSerializable(FISH) as FishEntity)
            list.let(::initAdapter)
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
        recyclerView.run {
            adapter = FishResultAdapter()
            (adapter as FishResultAdapter).setData(list)
            layoutManager = LinearLayoutManager(context)
        }
    }

    companion object {

        private const val TAG = "FishResultFragment"
        private const val FISH = "Fish"

        fun show(
            fragmentManager: FragmentManager?,
            fishEntity: FishEntity
        ) {
            fragmentManager?.let {
                FishResultFragment().apply {
                    arguments = Bundle().apply {
                        putSerializable(FISH, fishEntity)
                    }
                }.show(
                    it,
                    TAG
                )
            }
        }
    }
}
