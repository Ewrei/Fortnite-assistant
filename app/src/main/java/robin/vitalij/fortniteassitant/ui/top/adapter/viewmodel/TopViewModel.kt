package robin.vitalij.fortniteassitant.ui.top.adapter.viewmodel

class TopViewModel(
    val position: Int,
    val userName: String,
    val playerId: String,
    val avatar: String,
    val value: String
) : Top {
    override fun getType() = TopViewModelType.TOP
}