package robin.vitalij.fortniteassitant.ui.bottomsheet.top.adapter.viewmodel

import robin.vitalij.fortniteassitant.model.enums.TopType

class TopContentModel(
    val topType: TopType
) : TopResult {

    override fun getType() = TopResultType.CONTENT
}