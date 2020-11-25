package robin.vitalij.fortniteassitant.ui.comparison.selected.listuser

import android.content.Intent
import android.graphics.Canvas
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.rewarded.*
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator
import kotlinx.android.synthetic.main.fragment_selected_user.*
import robin.vitalij.fortniteassitant.BuildConfig
import robin.vitalij.fortniteassitant.FortniteApplication
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.common.extensions.*
import robin.vitalij.fortniteassitant.databinding.FragmentSelectedUserBinding
import robin.vitalij.fortniteassitant.model.comparison.PlayerModel
import robin.vitalij.fortniteassitant.model.enums.ComparisonDataType
import robin.vitalij.fortniteassitant.ui.common.BaseFragment
import robin.vitalij.fortniteassitant.ui.comparison.ComparisonActivity
import robin.vitalij.fortniteassitant.ui.comparison.selected.listuser.adapter.SelectedListUserAdapter
import robin.vitalij.fortniteassitant.ui.comparison.selected.manyaccount.ManyAccountActivity
import robin.vitalij.fortniteassitant.ui.subscription.SubscriptionActivity
import javax.inject.Inject


class SelectedListUserFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: SelectedListViewModelFactory

    private lateinit var viewModel: SelectedListViewModel

    private var selectedListUserAdapter: SelectedListUserAdapter? = null

    private var rewardedAd: RewardedAd? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val dataBinding: FragmentSelectedUserBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_selected_user, container, false)
        dataBinding.lifecycleOwner = this@SelectedListUserFragment
        dataBinding.viewModel = viewModel
        return dataBinding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FortniteApplication.appComponent.inject(this)
        viewModel = ViewModelProvider(viewModelStore, viewModelFactory)
            .get(SelectedListViewModel::class.java).apply {
                observeToProgressBar(this@SelectedListUserFragment)
                observeToError(this@SelectedListUserFragment)
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setToolbarTitle(R.string.selected_user)
        setListener()

        viewModel.data.observe(viewLifecycleOwner, Observer{
            it?.let(::initAdapter)
            comparisonButton.setVisibility(it.isNotEmpty())
        })

        viewModel.loadData()

        initRewardedAdd()

        val itemTouchHelper = ItemTouchHelper(simpleCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    private fun setListener() {
        comparisonButton.setOnClickListener {
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

            recyclerView.adapter = selectedListUserAdapter
            selectedListUserAdapter?.setData(list)

            recyclerView.run {
                layoutManager = LinearLayoutManager(context)
            }
        }
    }

    private fun initRewardedAdd() {
        rewardedAd = RewardedAd(context, BuildConfig.VIDEO_ID)
        val serverSideVerificationOptions: ServerSideVerificationOptions =
            ServerSideVerificationOptions.Builder().build()
        rewardedAd?.setServerSideVerificationOptions(serverSideVerificationOptions)
        val adRequest: AdRequest = AdRequest.Builder().build()
        rewardedAd?.loadAd(adRequest, object : RewardedAdLoadCallback() {
            override fun onRewardedAdLoaded() {
                //do nothing
            }
        })
    }

    private fun onDisplayButtonClicked(getAnWard: () -> Unit) {
        if (rewardedAd?.isLoaded == true) {
            rewardedAd?.show(activity, object : RewardedAdCallback() {
                override fun onUserEarnedReward(reward: RewardItem) {
                    getAnWard()
                }
            })
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

    companion object {

        fun newInstance() = SelectedListUserFragment()
    }
}