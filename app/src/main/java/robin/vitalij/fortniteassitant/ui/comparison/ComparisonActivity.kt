package robin.vitalij.fortniteassitant.ui.comparison

import android.content.Context
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.core.content.ContextCompat
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.common.extensions.setToolbarTitle
import robin.vitalij.fortniteassitant.model.enums.ComparisonDataType
import robin.vitalij.fortniteassitant.ui.common.BaseActivity
import robin.vitalij.fortniteassitant.ui.comparison.viewpager.AdapterComparisonFragment

const val PLAYER_ONE = "player_one"
const val PLAYER_TWO = "player_two"
const val BATTLES_TYPE = "battles_type"
const val GAME_TYPE = "game_type"
const val COMPARISON_DATA_TYPE = "comparison_data_type"

class ComparisonActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
                intent?.getSerializableExtra(COMPARISON_DATA_TYPE) as ComparisonDataType
            )
        )
    }

    companion object {
        fun getComparisonActivityIntent(
            context: Context?,
            comparisonDataType: ComparisonDataType
        ) = Intent(context, ComparisonActivity::class.java).apply {
            putExtra(COMPARISON_DATA_TYPE, comparisonDataType)
        }
    }
}