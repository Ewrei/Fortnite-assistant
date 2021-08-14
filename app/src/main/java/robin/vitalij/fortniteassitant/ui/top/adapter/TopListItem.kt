package robin.vitalij.fortniteassitant.ui.top.adapter

import robin.vitalij.fortniteassitant.model.TopFullModel

sealed class TopListItem {

    data class HeaderItem(val topFullModel: TopFullModel) : TopListItem()

    data class PlayerItem(
        val position: Int,
        val userName: String,
        val playerId: String,
        val avatar: String,
        val value: String
    ) : TopListItem()

    data class CurrentPositionItem(
        val position: String,
        val value: String
    ) : TopListItem()
}