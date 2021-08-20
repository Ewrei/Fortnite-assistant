package robin.vitalij.fortniteassitant.common.binding

import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import robin.vitalij.fortniteassitant.model.enums.ConfigType

object ButtonBinding {

    @JvmStatic
    @BindingAdapter("configType")
    fun Button.setConfig(configType: ConfigType) {
        setText(configType.getNameRes())
        backgroundTintList = ContextCompat.getColorStateList(context, configType.getColorRes())
    }

}