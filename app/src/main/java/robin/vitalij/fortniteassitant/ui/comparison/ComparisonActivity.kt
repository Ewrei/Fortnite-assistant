package robin.vitalij.fortniteassitant.ui.comparison

import android.content.Context
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.core.content.ContextCompat
import by.kirich1409.viewbindingdelegate.viewBinding
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.common.extensions.setToolbarTitle
import robin.vitalij.fortniteassitant.databinding.ActivityBaseBinding
import robin.vitalij.fortniteassitant.model.enums.ComparisonDataType
import robin.vitalij.fortniteassitant.ui.common.BaseActivity
import robin.vitalij.fortniteassitant.ui.comparison.viewpager.AdapterComparisonFragment

class ComparisonActivity : BaseActivity(R.layout.activity_base) {

    private val binding by viewBinding(ActivityBaseBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setFragment()
        setToolbar()
    }

    private fun setToolbar() {
        enableBackButton()
        setToolbarTitle(getString(R.string.compare_players))
        supportActionBar?.setBackgroundDrawable(
            ColorDrawable(
                ContextCompat.getColor(
                    this,
                    R.color.colorPrimary
                )
            )
        )
    }

    private fun setFragment() {
        replaceFragment(
            AdapterComparisonFragment.newInstance(
                intent?.getSerializableExtra(ARG_COMPARISON_DATA_TYPE) as ComparisonDataType
            )
        )
    }

    companion object {
        const val ARG_PLAYER_ONE = "player_one"
        const val ARG_PLAYER_TWO = "player_two"
        const val ARG_BATTLES_TYPE = "battles_type"
        const val ARG_GAME_TYPE = "game_type"
        const val ARG_COMPARISON_DATA_TYPE = "comparison_data_type"

        fun getComparisonActivityIntent(
            context: Context?,
            comparisonDataType: ComparisonDataType
        ) = Intent(context, ComparisonActivity::class.java).apply {
            putExtra(ARG_COMPARISON_DATA_TYPE, comparisonDataType)
        }
    }
}