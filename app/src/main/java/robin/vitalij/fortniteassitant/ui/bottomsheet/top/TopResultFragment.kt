package robin.vitalij.fortniteassitant.ui.bottomsheet.top

import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.bottom_sheet_top.*
import kotlinx.android.synthetic.main.layout_type_stats_group.*
import kotlinx.android.synthetic.main.recycler_view.recyclerView
import robin.vitalij.fortniteassitant.FortniteApplication
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.common.extensions.initBottomSheetInternal
import robin.vitalij.fortniteassitant.databinding.BottomSheetTopBinding
import robin.vitalij.fortniteassitant.interfaces.TopResultCallback
import robin.vitalij.fortniteassitant.model.TopFullModel
import robin.vitalij.fortniteassitant.model.enums.BattlesType
import robin.vitalij.fortniteassitant.model.enums.GameType
import robin.vitalij.fortniteassitant.ui.bottomsheet.top.adapter.TopResultAdapter
import robin.vitalij.fortniteassitant.ui.bottomsheet.top.adapter.viewmodel.TopResult
import robin.vitalij.fortniteassitant.ui.bottomsheet.top.adapter.viewmodel.TopResultType
import javax.inject.Inject


const val BOTTOM_SHEET_MARGIN_TOP = 200

class TopResultFragment : BottomSheetDialogFragment() {

    @Inject
    lateinit var viewModelFactory: TopResultViewModelFactory

    private lateinit var viewModel: TopResultViewModel

    private var topResultCallback: TopResultCallback? = null

    private var gameType: GameType = GameType.ALL

    private var battlesType: BattlesType = BattlesType.OVERALL

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dialog?.initBottomSheetInternal()
        val dataBinding =
            DataBindingUtil.inflate<BottomSheetTopBinding>(
                inflater,
                R.layout.bottom_sheet_top,
                container,
                false
            )
        dataBinding.lifecycleOwner = this@TopResultFragment
        dataBinding.viewModel = viewModel
        return dataBinding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FortniteApplication.appComponent.inject(this)
        viewModel = ViewModelProvider(viewModelStore, viewModelFactory)
            .get(TopResultViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.mutableLiveData.observe(viewLifecycleOwner, {
            it.let(::initAdapter)
        })

        arguments?.let {
            viewModel.topFullModel = it.getSerializable(TOP_FULL_MODEL) as TopFullModel
        }

        viewModel.loadData()

        setListeners()
    }

    override fun onStart() {
        super.onStart()
        val displayMetrics = DisplayMetrics()
        activity?.windowManager?.defaultDisplay?.getMetrics(displayMetrics)
        val sheetContainer = requireView().parent as? ViewGroup ?: return
        sheetContainer.layoutParams.height = (displayMetrics.heightPixels - BOTTOM_SHEET_MARGIN_TOP)
    }

    private fun initAdapter(list: List<TopResult>) {
        recyclerView.run {
            adapter = TopResultAdapter {
                topResultCallback?.checkTop(TopFullModel(it, gameType, battlesType))
                dismiss()
            }
            (adapter as TopResultAdapter).setData(list)
            val gridlayoutManager = GridLayoutManager(
                activity, MAX_SPAN_COUNT
            )

            gridlayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return when (adapter?.getItemViewType(position)) {
                        TopResultType.HEADER.id -> 2
                        TopResultType.CONTENT.id -> 1
                        else -> 1
                    }
                }
            }

            layoutManager = gridlayoutManager
        }
    }

    private fun setListeners() {
        typeBattlesSpinner.setItems(BattlesType.getTitles(requireContext()))

        typeBattlesSpinner.setOnItemSelectedListener { view, position, id, item ->
            battlesType = BattlesType.values()[position]
        }

        allStats.setOnClickListener {
            gameType = GameType.ALL
        }

        keyboardMouse.setOnClickListener {
            gameType = GameType.KEYBOARD_MOUSE
        }

        gamepad.setOnClickListener {
            gameType = GameType.GAMEPAD
        }

        touch.setOnClickListener {
            gameType = GameType.TOUCH
        }
    }

    companion object {

        private const val TAG = "TopResultFragment"
        private const val TOP_FULL_MODEL = "top_full_model"
        private const val MAX_SPAN_COUNT = 2

        fun show(
            fragmentManager: FragmentManager?,
            topFullModel: TopFullModel,
            topResultCallback: TopResultCallback
        ) {
            fragmentManager?.let {
                TopResultFragment().apply {
                    arguments = Bundle().apply {
                        putSerializable(TOP_FULL_MODEL, topFullModel)
                    }
                    this.topResultCallback = topResultCallback
                }.show(
                    it,
                    TAG
                )
            }
        }
    }
}
