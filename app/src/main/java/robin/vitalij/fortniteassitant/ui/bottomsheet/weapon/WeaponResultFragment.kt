package robin.vitalij.fortniteassitant.ui.bottomsheet.weapon

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
import robin.vitalij.fortniteassitant.databinding.BottomSheetRecyclerviewBinding
import robin.vitalij.fortniteassitant.db.entity.WeaponEntity
import robin.vitalij.fortniteassitant.ui.bottomsheet.weapon.adapter.WeaponResultAdapter
import javax.inject.Inject

class WeaponResultFragment : BottomSheetDialogFragment() {

    @Inject
    lateinit var viewModelFactory: WeaponResultViewModelFactory

    private lateinit var viewModel: WeaponResultViewModel

    private lateinit var binding: BottomSheetRecyclerviewBinding

    private val weaponResultAdapter = WeaponResultAdapter()

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

        binding = BottomSheetRecyclerviewBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FortniteApplication.appComponent.inject(this)
        viewModel = ViewModelProvider(viewModelStore, viewModelFactory)
            .get(WeaponResultViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            weaponResultAdapter.updateData(arrayListOf(it.getSerializable(ARG_WEAPON) as WeaponEntity))
        }

        initializeRecyclerView()
    }

    override fun onStart() {
        super.onStart()
        val displayMetrics = DisplayMetrics()
        activity?.windowManager?.defaultDisplay?.getMetrics(displayMetrics)
        val sheetContainer = requireView().parent as? ViewGroup ?: return
        sheetContainer.layoutParams.height = (displayMetrics.heightPixels - BOTTOM_SHEET_MARGIN_TOP)
    }

    private fun initializeRecyclerView() {
        binding.recyclerView.run {
            adapter = weaponResultAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    companion object {

        private const val TAG = "WeaponResultFragment"
        private const val ARG_WEAPON = "arg_weapon"

        private const val BOTTOM_SHEET_MARGIN_TOP = 200

        fun show(fragmentManager: FragmentManager, weaponEntity: WeaponEntity) {
            WeaponResultFragment().apply {
                arguments = bundleOf(ARG_WEAPON to weaponEntity)
            }.show(fragmentManager, TAG)
        }
    }
}
