package robin.vitalij.fortniteassitant.model.enums

import robin.vitalij.fortniteassitant.R

enum class RarityType(val id: String) {
    UNCOMMON("uncommon") {
        override fun getBackgroundRes() = R.drawable.border_uncommon
    },
    RARE("rare") {
        override fun getBackgroundRes() = R.drawable.border_rare
    },
    EPIC("epic") {
        override fun getBackgroundRes() = R.drawable.border_rare
    },
    MARVEL("marvel") {
        override fun getBackgroundRes() = R.drawable.border_marvel
    },
    DC("dc") {
        override fun getBackgroundRes() = R.drawable.border_dc
    },
    DARK("dark") {
        override fun getBackgroundRes() = R.drawable.border_dark
    },
    STAR_WARS_SERIES("star wars series") {
        override fun getBackgroundRes() = R.drawable.border_star_wars_series
    },
    FROZEN_SERIES("frozen series") {
        override fun getBackgroundRes() = R.drawable.border_frozen_series
    },
    LAVA_SERIES("lava series") {
        override fun getBackgroundRes() = R.drawable.border_lava_series
    },
    SLURP_SERIES("slurp series") {
        override fun getBackgroundRes() = R.drawable.border_slurp_series
    },
    SHADOW_SERIES("shadow series") {
        override fun getBackgroundRes() = R.drawable.border_shadow_series
    },
    LEGENDARY("legendary") {
        override fun getBackgroundRes() = R.drawable.border_legendary
    };

    abstract fun getBackgroundRes(): Int

    companion object {
        fun getRarityType(id: String?): RarityType {
            for (rarityType in values())
                if (rarityType.id == id) {
                    return rarityType
                }

            return RarityType.UNCOMMON
        }
    }
}