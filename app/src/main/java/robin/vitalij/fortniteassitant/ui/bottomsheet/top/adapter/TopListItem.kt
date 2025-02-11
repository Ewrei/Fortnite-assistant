package robin.vitalij.fortniteassitant.ui.bottomsheet.top.adapter

import robin.vitalij.fortniteassitant.model.enums.TopType

sealed class TopListItem {

    data class HeaderItem(
        val title: String
    ) : TopListItem()

    data class ContentItem(
        val topType: TopType
    ) : TopListItem()

}