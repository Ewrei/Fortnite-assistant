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
    },
    FOUND_ACCOUNT_ID_IN_EPIC_GAMES {
        override fun getNameRes() = R.string.how_to_find_out_your_account_in_epic_game
        override fun getColorRes() = R.color.color_orange
    },
    FOUND_ACCOUNT_ID_IN_FORTNITE{
        override fun getNameRes() = R.string.how_to_find_out_your_account_in_fortnite
        override fun getColorRes() = R.color.telegram
    };

    abstract fun getNameRes(): Int
    abstract fun getColorRes(): Int
}