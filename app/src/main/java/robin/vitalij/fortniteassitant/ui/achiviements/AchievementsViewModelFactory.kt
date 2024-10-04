package robin.vitalij.fortniteassitant.ui.achiviements

import robin.vitalij.fortniteassitant.repository.AchievementRepository
import robin.vitalij.fortniteassitant.ui.common.BaseViewModelFactory
import javax.inject.Inject

class AchievementsViewModelFactory @Inject constructor(
    private val achievementRepository: AchievementRepository
) : BaseViewModelFactory<AchievementsViewModel>(AchievementsViewModel::class.java) {

    private var viewModel: AchievementsViewModel? = null

    override fun createViewModel(): AchievementsViewModel {
        return viewModel ?: run {
            val model = AchievementsViewModel(achievementRepository)
            viewModel = model
            return model
        }
    }
}