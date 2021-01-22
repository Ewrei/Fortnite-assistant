package robin.vitalij.fortniteassitant.model.enums

import robin.vitalij.fortniteassitant.R

enum class NewsType {
    BR_NEWS {
        override fun getTitleRes() = R.string.br_news
        override fun getNewsName() = "br"
    },
    STW {
        override fun getTitleRes() = R.string.stw_news
        override fun getNewsName() = "stw"
    },
    CREATIVE_NEWS {
        override fun getTitleRes() = R.string.creative_news
        override fun getNewsName() = "creative"
    };

    abstract fun getTitleRes(): Int
    abstract fun getNewsName(): String
}