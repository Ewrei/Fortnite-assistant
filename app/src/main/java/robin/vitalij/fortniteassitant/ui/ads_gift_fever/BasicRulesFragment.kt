package robin.vitalij.fortniteassitant.ui.ads_gift_fever

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import robin.vitalij.fortniteassitant.FortniteApplication
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.common.extensions.getStatusBarHeight
import robin.vitalij.fortniteassitant.common.extensions.intentView
import robin.vitalij.fortniteassitant.common.extensions.setMarginTop
import robin.vitalij.fortniteassitant.databinding.FragmentBasicRulesBinding
import robin.vitalij.fortniteassitant.repository.storage.PreferenceManager
import java.util.*
import javax.inject.Inject


class BasicRulesFragment : Fragment(R.layout.fragment_basic_rules) {

    @Inject
    lateinit var preferenceManager: PreferenceManager

    private val binding by viewBinding(FragmentBasicRulesBinding::bind)

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
            downloadAppClickHandle()
        }

        binding.closeImageButton.setOnClickListener {
            activity?.finish()
        }
    }

    private fun downloadAppClickHandle() {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.MONTH, TWO_MOUNT)
        preferenceManager.setShowBasicRulesDate(calendar.time)

        activity?.intentView(getString(R.string.gift_fever_url))
        activity?.finish()
    }

    companion object {
        private const val TWO_MOUNT = 2

        fun newInstance() = BasicRulesFragment()

    }
}