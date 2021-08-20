package robin.vitalij.fortniteassitant.model.enums

import robin.vitalij.fortniteassitant.R

enum class ShopType(val id: String) {

    EMOTE("emote") {
        override fun getTitleRes() = R.string.emote
    },
    MUSIC("music") {
        override fun getTitleRes() = R.string.music
    },
    SPRAY("spray") {
        override fun getTitleRes() = R.string.spray
    },
    LOADING_SREEN("loadingscreen") {
        override fun getTitleRes() = R.string.loading_screen
    },
    BACKPACK("backpack") {
        override fun getTitleRes() = R.string.backpack
    },
    EMOJI("emoji") {
        override fun getTitleRes() = R.string.emoji
    },
    WRAP("wrap") {
        override fun getTitleRes() = R.string.wrap
    },
    PET_CARRIRER("petcarrier") {
        override fun getTitleRes() = R.string.petcarrier
    },
    CONTRAIL("contrail") {
        override fun getTitleRes() = R.string.contrail
    },
    TOY("toy") {
        override fun getTitleRes() = R.string.toy
    },
    BANNER("banner") {
        override fun getTitleRes() = R.string.banner
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
    },
    UNKNOWN("Unknown") {
        override fun getTitleRes() = R.string.unknown
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