package robin.vitalij.fortniteassitant.model.enums

enum class AvatarType {

    DEAD_POOL {
        override fun getImageUrl() = "https://media.fortniteapi.io/images/c21648653c1d9104479072ee6fff7e78/background.png"
    },
    VILLAINOUS_CURVE {
        override fun getImageUrl() = "https://media.fortniteapi.io/images/1f192950b44f75dd7025159c927544e6/background.png"
    },
    BIG_GUY {
        override fun getImageUrl() = "https://media.fortniteapi.io/images/2b68f305c8801f284ebfcd93226ee08c/background.png"
    },
    BIG_MAN_SHADOW {
        override fun getImageUrl() = "https://media.fortniteapi.io/images/def90fa1c96e1833098fc9dd6aeae8f1/background.png"
    },
    STARS_AND_STRIPES_FIGHTER {
        override fun getImageUrl() = "https://media.fortniteapi.io/images/5b2ee25e4bac6c054914f0b48fdf9076/background.png"
    },
    ZOMBIE_INFANTRY {
        override fun getImageUrl() = "https://media.fortniteapi.io/images/a37a5df21bc3a1550e84ceee3806dc08/background.png"
    };

    abstract fun getImageUrl(): String
}