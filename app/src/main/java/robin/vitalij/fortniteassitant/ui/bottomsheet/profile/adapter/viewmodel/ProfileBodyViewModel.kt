package robin.vitalij.fortniteassitant.ui.bottomsheet.profile.adapter.viewmodel

import robin.vitalij.fortniteassitant.ui.bottomsheet.profile.adapter.viewholder.body.adapter.viewmodel.BodyStats

data class ProfileBodyViewModel(
    val all: List<BodyStats>,
    val keyboardMouse: List<BodyStats>,
    val gamepad: List<BodyStats>,
    val touch: List<BodyStats>
) : Profile {
    override fun getType() = ProfileType.BODY
}