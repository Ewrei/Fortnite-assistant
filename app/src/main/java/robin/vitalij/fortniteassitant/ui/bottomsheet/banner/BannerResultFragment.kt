package robin.vitalij.fortniteassitant.ui.bottomsheet.banner

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
import robin.vitalij.fortniteassitant.common.extensions.observeToError
import robin.vitalij.fortniteassitant.common.extensions.observeToProgressBar
import robin.vitalij.fortniteassitant.databinding.BottomSheetMvvmBinding
import robin.vitalij.fortniteassitant.db.entity.BannerEntity
import robin.vitalij.fortniteassitant.ui.bottomsheet.banner.adapter.BannerResultAdapter
import javax.inject.Inject

class BannerResultFragment : BottomSheetDialogFragment() {

    @Inject
    lateinit var viewModelFactory: BannerResultViewModelFactory

    private lateinit var viewModel: BannerResultViewModel

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
            .get(BannerResultViewModel::class.java).apply {
                observeToProgressBar(this@BannerResultFragment)
                observeToError(this@BannerResultFragment)
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            viewModel.loadData((it.getString(ARG_BANNER_ID) ?: ""))
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

    private fun initAdapter(list: List<BannerEntity>) {
        binding.recyclerViewInclude.recyclerView.run {
            adapter = BannerResultAdapter()
            (adapter as BannerResultAdapter).setData(list)
            layoutManager = LinearLayoutManager(context)
        }
    }

    companion object {

        private const val TAG = "BannerResultFragment"
        private const val ARG_BANNER_ID = "arg_banner_id"
        const val BOTTOM_SHEET_MARGIN_TOP = 200

        fun show(
            fragmentManager: FragmentManager,
            bannerId: String
        ) {
            BannerResultFragment().apply {
                arguments = bundleOf(ARG_BANNER_ID to bannerId)
            }.show(fragmentManager, TAG)
        }
    }
}
