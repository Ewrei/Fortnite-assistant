package robin.vitalij.fortniteassitant.ui.comparison.selected.listuser

import android.content.Intent
import android.graphics.Canvas
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator
import robin.vitalij.fortniteassitant.FortniteApplication
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.common.extensions.observeToError
import robin.vitalij.fortniteassitant.common.extensions.observeToProgressBar
import robin.vitalij.fortniteassitant.common.extensions.setErrorView
import robin.vitalij.fortniteassitant.common.extensions.setToolbarTitle
import robin.vitalij.fortniteassitant.common.extensions.setVisibility
import robin.vitalij.fortniteassitant.common.extensions.showDialog
import robin.vitalij.fortniteassitant.databinding.FragmentSelectedUserBinding
import robin.vitalij.fortniteassitant.interfaces.ErrorController
import robin.vitalij.fortniteassitant.interfaces.ProgressBarController
import robin.vitalij.fortniteassitant.model.ErrorModel
import robin.vitalij.fortniteassitant.model.comparison.PlayerModel
import robin.vitalij.fortniteassitant.model.enums.ComparisonDataType
import robin.vitalij.fortniteassitant.ui.comparison.ComparisonActivity
import robin.vitalij.fortniteassitant.ui.comparison.selected.listuser.adapter.SelectedListUserAdapter
import robin.vitalij.fortniteassitant.ui.comparison.selected.manyaccount.ManyAccountActivity
import robin.vitalij.fortniteassitant.ui.subscription.SubscriptionActivity
import javax.inject.Inject


class SelectedListUserFragment : Fragment(R.layout.fragment_selected_user), ProgressBarController,
    ErrorController {

    @Inject
    lateinit var viewModelFactory: SelectedListViewModelFactory

    private lateinit var viewModel: SelectedListViewModel

    private var selectedListUserAdapter: SelectedListUserAdapter? = null

    private lateinit var dataBinding: FragmentSelectedUserBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dataBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_selected_user, container, false)
        dataBinding.lifecycleOwner = this@SelectedListUserFragment
        dataBinding.viewModel = viewModel
        return dataBinding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FortniteApplication.appComponent.inject(this)
        viewModel = ViewModelProvider(viewModelStore, viewModelFactory)[SelectedListViewModel::class.java].apply {
                observeToProgressBar(this@SelectedListUserFragment)
                observeToError(this@SelectedListUserFragment)
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setToolbarTitle(R.string.selected_user)
        setListener()

        viewModel.data.observe(viewLifecycleOwner) {
            it?.let(::initAdapter)
            dataBinding.comparisonButton.setVisibility(it.isNotEmpty())
        }

        viewModel.loadData()

        val itemTouchHelper = ItemTouchHelper(simpleCallback)
        itemTouchHelper.attachToRecyclerView(dataBinding.recyclerView)
    }

    private fun setListener() {
        dataBinding.viewErrorInclude.errorResolveButton.setOnClickListener {
            viewModel.loadData()
        }

        dataBinding.comparisonButton.setOnClickListener {
            when {
                viewModel.selectedSize < 2 -> {
                    context?.showDialog(R.string.comparison_no_players_selected_dialog_body)
                }

                viewModel.selectedSize == 2 -> {
                    startActivity(
                        ComparisonActivity.getComparisonActivityIntent(
                            context, ComparisonDataType.TWO_PLAYER_USER
                        )
                    )
                }

                else -> {
                    if (viewModel.preferenceManager.getIsSubscription()) {
                        startActivity(ManyAccountActivity.getComparisonActivityIntent(context))
                    } else {
                        context?.showDialog(
                            getString(R.string.app_name),
                            getString(R.string.content_restriction_dialog),
                            positiveClick = {
                                onDisplayButtonClicked {
                                    startActivity(
                                        ManyAccountActivity.getComparisonActivityIntent(
                                            context
                                        )
                                    )
                                }
                                startActivity(
                                    ManyAccountActivity.getComparisonActivityIntent(
                                        context
                                    )
                                )
                            },
                            neutralClick = {
                                val intent = Intent(context, SubscriptionActivity::class.java)
                                startActivity(intent)
                            }
                        )
                    }
                }
            }
        }
    }

    private fun initAdapter(list: List<PlayerModel>) {
        if (selectedListUserAdapter != null) {
            selectedListUserAdapter?.updateData(list)
        } else {
            selectedListUserAdapter = SelectedListUserAdapter(
                onClick = {
                    viewModel.updatePlayerModel(it)
                }
            )

            dataBinding.recyclerView.adapter = selectedListUserAdapter
            selectedListUserAdapter?.setData(list)

            dataBinding.recyclerView.run {
                layoutManager = LinearLayoutManager(context)
            }
        }
    }

    private fun onDisplayButtonClicked(getAnWard: () -> Unit) {
        if (viewModel.rewardedAdRepository.isLoadVideo()) {
            viewModel.rewardedAdRepository.showReward(requireActivity()) {
                getAnWard()
            }
        }
    }

    lateinit var deletedMovie: PlayerModel

    private var simpleCallback: ItemTouchHelper.SimpleCallback =
        object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                when (direction) {
                    ItemTouchHelper.LEFT -> {
                        deletedMovie = selectedListUserAdapter!!.items[position]
                        selectedListUserAdapter?.items?.removeAt(position)
                        selectedListUserAdapter?.notifyItemRemoved(position)
                        viewModel.deleteFromBasket(deletedMovie)
                    }
                }
            }

            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {
                RecyclerViewSwipeDecorator.Builder(
                    context,
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )
                    .addSwipeLeftBackgroundColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.colorPrimary
                        )
                    )
                    .addSwipeLeftLabel(getString(R.string.delete))
                    .setSwipeLeftLabelColor(
                        ContextCompat.getColor(
                            requireContext(),
                            android.R.color.white
                        )
                    )
                    .create()
                    .decorate()
                super.onChildDraw(
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )
            }
        }

    override fun setError(errorModel: ErrorModel) {
        dataBinding.viewErrorInclude.setErrorView(errorModel)
    }

    override fun hideError() {
        dataBinding.viewErrorInclude.errorView.isVisible = false
    }

    override fun showOrHideProgressBar(show: Boolean) {
        dataBinding.progressViewInclude.progressContainer.isVisible = show
    }

    companion object {

        fun newInstance() = SelectedListUserFragment()
    }
}