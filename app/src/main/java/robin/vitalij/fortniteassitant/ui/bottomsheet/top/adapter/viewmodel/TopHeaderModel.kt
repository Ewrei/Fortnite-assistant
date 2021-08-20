package robin.vitalij.fortniteassitant.ui.bottomsheet.top.adapter.viewmodel

class TopHeaderModel(
    val title: String
) : TopResult {

    override fun getType() = TopResultType.HEADER
}