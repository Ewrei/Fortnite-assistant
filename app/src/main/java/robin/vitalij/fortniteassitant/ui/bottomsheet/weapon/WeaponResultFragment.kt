package robin.vitalij.fortniteassitant.ui.bottomsheet.weapon

import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.recycler_view.*
import robin.vitalij.fortniteassitant.FortniteApplication
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.databinding.BottomSheetWeaponBinding
import robin.vitalij.fortniteassitant.db.entity.WeaponEntity
import robin.vitalij.fortniteassitant.ui.bottomsheet.weapon.adapter.WeaponResultAdapter
import javax.inject.Inject

const val BOTTOM_SHEET_MARGIN_TOP = 200

class WeaponResultFragment : BottomSheetDialogFragment() {

    @Inject
    lateinit var viewModelFactory: WeaponResultViewModelFactory

    private lateinit var viewModel: WeaponResultViewModel

    private lateinit var dataBinding: BottomSheetWeaponBinding

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
        dataBinding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.bottom_sheet_weapon,
                container,
                false
            )
        dataBinding.lifecycleOwner = this@WeaponResultFragment
        dataBinding.viewModel = viewModel
        return dataBinding.root
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
            dataBinding.item = it.getSerializable(WEAPON) as WeaponEntity

            val list = arrayListOf(it.getSerializable(WEAPON) as WeaponEntity)
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

    private fun initAdapter(list: List<WeaponEntity>) {
        recyclerView.run {
            adapter = WeaponResultAdapter {
                show(childFragmentManager, it)
            }
            (adapter as WeaponResultAdapter).setData(list)
            layoutManager = LinearLayoutManager(context)
        }
    }

    companion object {

        private const val TAG = "WeaponResultFragment"
        private const val WEAPON = "Weapon"

        fun show(
            fragmentManager: FragmentManager?,
            itemShopUpcoming: WeaponEntity
        ) {
            fragmentManager?.let {
                WeaponResultFragment().apply {
                    arguments = Bundle().apply {
                        putSerializable(WEAPON, itemShopUpcoming)
                    }
                }.show(
                    it,
                    TAG
                )
            }
        }
    }
}
