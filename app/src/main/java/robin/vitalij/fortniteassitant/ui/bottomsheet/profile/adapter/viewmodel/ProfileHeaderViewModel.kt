package robin.vitalij.fortniteassitant.ui.bottomsheet.profile.adapter.viewmodel

data class ProfileHeaderViewModel(
    val avatarUrl: String,
    val userName: String,
    val playerId: String,
    val level: Int,
    val progress: Int,
    val matches: Int,
    val kd: Double,
    val winRate: Double,
    val playTime: String,
    val totalMatches: String
) : Profile {
    override fun getType() = ProfileType.HEADER
}