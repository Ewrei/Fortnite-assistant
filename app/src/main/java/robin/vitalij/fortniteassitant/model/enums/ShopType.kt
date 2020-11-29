package robin.vitalij.fortniteassitant.model.enums

import robin.vitalij.fortniteassitant.R

enum class ShopType(val id: String) {

    EMOTE("emote") {
        override fun getTitleRes() = R.string.emote
    },
    UNKNOWN("Unknown") {
        override fun getTitleRes() = R.string.unknown
    },
    OUTFIT("outfit") {
        override fun getTitleRes() = R.string.outfit
    },
    PICKAXE("pickaxe") {
        override fun getTitleRes() = R.string.pickaxe
    },
    GLIDER("glider") {
        override fun getTitleRes() = R.string.glider
    },
    BUNDLE("bundle") {
        override fun getTitleRes() = R.string.bundle
    };


    abstract fun getTitleRes(): Int

    companion object {
        fun getShopType(id: String?): ShopType {
            for (weaponImageType in values())
                if (weaponImageType.id == id) {
                    return weaponImageType
                }

            return UNKNOWN
        }
    }
}