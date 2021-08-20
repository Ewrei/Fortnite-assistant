package robin.vitalij.fortniteassitant.model.enums

import robin.vitalij.fortniteassitant.R

enum class ConfigType {
    TELEGRAM {
        override fun getNameRes() = R.string.contact_telegram
        override fun getColorRes() = R.color.telegram
    },
    VK {
        override fun getNameRes() = R.string.contact_vk
        override fun getColorRes() = R.color.vk
    },
    GMAIL {
        override fun getNameRes() = R.string.contact_gmail
        override fun getColorRes() = R.color.phone
    };

    abstract fun getNameRes(): Int
    abstract fun getColorRes(): Int
}