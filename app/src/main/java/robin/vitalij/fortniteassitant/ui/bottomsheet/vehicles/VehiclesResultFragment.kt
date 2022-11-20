package robin.vitalij.fortniteassitant.ui.bottomsheet.vehicles

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
import robin.vitalij.fortniteassitant.databinding.BottomSheetRecyclerviewBinding
import robin.vitalij.fortniteassitant.model.network.VehicleModel
import robin.vitalij.fortniteassitant.ui.bottomsheet.vehicles.adapter.VehiclesResultAdapter
import robin.vitalij.fortniteassitant.ui.bottomsheet.vehicles.adapter.VehiclesResultListItem
import robin.vitalij.fortniteassitant.utils.mapper.VehiclesResultMapper
import javax.inject.Inject

class VehiclesResultFragment : BottomSheetDialogFragment() {

    @Inject
    lateinit var viewModelFactory: VehiclesResultViewModelFactory

    private lateinit var viewModel: VehiclesResultViewModel

    private lateinit var binding: BottomSheetRecyclerviewBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dialog?.initBottomSheetInternal()
        binding = BottomSheetRecyclerviewBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FortniteApplication.appComponent.inject(this)
        viewModel = ViewModelProvider(viewModelStore, viewModelFactory)
            .get(VehiclesResultViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            val vehicleModel: VehicleModel = it.getParcelable(ARG_VEHICLES)!!

            VehiclesResultMapper().transform(vehicleModel).apply {
                this.let(::initAdapter)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        val displayMetrics = DisplayMetrics()
        activity?.windowManager?.defaultDisplay?.getMetrics(displayMetrics)
        val sheetContainer = requireView().parent as? ViewGroup ?: return
        sheetContainer.layoutParams.height = (displayMetrics.heightPixels - BOTTOM_SHEET_MARGIN_TOP)
    }

    private fun initAdapter(list: List<VehiclesResultListItem>) {
        binding.recyclerView.run {
            adapter = VehiclesResultAdapter(
                layoutInflater = layoutInflater
            )
            (adapter as VehiclesResultAdapter).setData(list)
            layoutManager = LinearLayoutManager(context)
        }
    }

    companion object {
        private const val TAG = "VehiclesResultFragment"
        private const val ARG_VEHICLES = "arg_vehicles"
        private const val BOTTOM_SHEET_MARGIN_TOP = 200

        fun show(fragmentManager: FragmentManager, vehicleModel: VehicleModel) {
            VehiclesResultFragment().apply {
                arguments = bundleOf(ARG_VEHICLES to vehicleModel)
            }.show(fragmentManager, TAG)
        }
    }
}
