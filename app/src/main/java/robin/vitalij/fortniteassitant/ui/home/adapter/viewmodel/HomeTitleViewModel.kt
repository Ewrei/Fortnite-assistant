package robin.vitalij.fortniteassitant.ui.home.adapter.viewmodel

data class HomeTitleViewModel(
    val title: String
) : Home {
    override fun getType() = HomeType.TITLE
}