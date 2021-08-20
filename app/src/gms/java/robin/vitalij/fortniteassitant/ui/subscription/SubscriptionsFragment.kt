package robin.vitalij.fortniteassitant.ui.subscription

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.fragment_subcriptions.*
import robin.vitalij.fortniteassitant.FortniteApplication
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.common.extensions.DATE_PATTERN_YEAR_TIME
import robin.vitalij.fortniteassitant.common.extensions.getDateStringFormat
import robin.vitalij.fortniteassitant.databinding.FragmentSubcriptionsBinding
import robin.vitalij.fortniteassitant.ui.common.BaseFragment
import javax.inject.Inject


class SubscriptionsFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: SubscriptionsViewModelFactory

    private lateinit var viewModel: SubscriptionsViewModel

    private lateinit var dataBinding: FragmentSubcriptionsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_subcriptions, container, false)
        dataBinding.lifecycleOwner = this@SubscriptionsFragment
        dataBinding.viewModel = viewModel
        return dataBinding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(
            viewModelStore,
            viewModelFactory
        ).get(SubscriptionsViewModel::class.java)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        FortniteApplication.appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.data.observe(viewLifecycleOwner, Observer {
            dataBinding.item = it
        })

        setListeners()

//        viewModel.checkout = Checkout.forActivity(requireActivity(), requireContext().getBilling())
//        viewModel.loadData()
    }


    override fun onDestroy() {
        // viewModel.checkout?.stop()
        super.onDestroy()
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        //  viewModel.checkout?.onActivityResult(requestCode, resultCode, data)
    }

    private fun setListeners() {
        closeImage.setOnClickListener { activity?.finish() }
        watchClick.setOnClickListener { onDisplayButtonClicked({}) }
    }

    private fun onDisplayButtonClicked(getAnWard: () -> Unit) {
        if (viewModel.rewardedAdRepository.defaultRewardedAd != null) {
            viewModel.rewardedAdRepository.defaultRewardedAd?.show(requireActivity()) {
                Toast.makeText(
                    activity,
                    String.format(
                        getString(R.string.reclam_info) + viewModel.saveData().time.getDateStringFormat(
                            DATE_PATTERN_YEAR_TIME,
                            true
                        )
                    ),
                    Toast.LENGTH_SHORT
                ).show()
                getAnWard()
            }
        }
    }

    companion object {
        fun newInstance() = SubscriptionsFragment()
    }
}