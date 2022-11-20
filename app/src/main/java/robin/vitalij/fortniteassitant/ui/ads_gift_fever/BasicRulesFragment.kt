package robin.vitalij.fortniteassitant.ui.ads_gift_fever

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import robin.vitalij.fortniteassitant.FortniteApplication
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.common.extensions.getStatusBarHeight
import robin.vitalij.fortniteassitant.common.extensions.intentView
import robin.vitalij.fortniteassitant.common.extensions.setMarginTop
import robin.vitalij.fortniteassitant.databinding.FragmentBasicRulesBinding
import robin.vitalij.fortniteassitant.repository.storage.PreferenceManager
import robin.vitalij.fortniteassitant.ui.common.BaseFragment
import java.util.*
import javax.inject.Inject


class BasicRulesFragment : BaseFragment() {

    private lateinit var binding: FragmentBasicRulesBinding

    @Inject
    lateinit var preferenceManager: PreferenceManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBasicRulesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()

        binding.logoIconImage.setMarginTop(
            resources.getStatusBarHeight() + resources.getDimensionPixelSize(R.dimen.default_margin_double),
            marginLeft = resources.getDimensionPixelSize(R.dimen.default_margin_double)
        )
        binding.closeImageButton.setMarginTop(
            resources.getStatusBarHeight() + resources.getDimensionPixelSize(R.dimen.default_margin),
            marginRight = resources.getDimensionPixelSize(R.dimen.default_margin_double)
        )
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        FortniteApplication.appComponent.inject(this)
    }


    private fun setListeners() {
        binding.downloadAppButton.setOnClickListener {
            val calendar = Calendar.getInstance()
            calendar.add(Calendar.MONTH, TWO_MOUNT)
            calendar.time
            preferenceManager.setShowBasicRulesDate(calendar.time)

            activity?.intentView(getString(R.string.gift_fever_url))
            activity?.finish()
        }

        binding.closeImageButton.setOnClickListener {
            activity?.finish()
        }
    }

    companion object {
        private const val TWO_MOUNT = 2

        fun newInstance() = BasicRulesFragment()

    }
}