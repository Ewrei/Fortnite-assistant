package robin.vitalij.fortniteassitant.ui.bottomsheet.user

import robin.vitalij.fortniteassitant.repository.comparison.ComparisonRepository
import robin.vitalij.fortniteassitant.repository.db.UserRepository
import robin.vitalij.fortniteassitant.repository.storage.PreferenceManager
import robin.vitalij.fortniteassitant.ui.common.BaseViewModelFactory
import javax.inject.Inject

class UserResultViewModelFactory @Inject constructor(
    private val preferenceManager: PreferenceManager,
    private val comparisonRepository: ComparisonRepository,
    private val userRepository: UserRepository
) : BaseViewModelFactory<UserResultViewModel>(UserResultViewModel::class.java) {

    private var viewModel: UserResultViewModel? = null

    override fun createViewModel(): UserResultViewModel {
        return viewModel ?: run {
            val model = UserResultViewModel(
                preferenceManager,
                comparisonRepository,
                userRepository
            )
            viewModel = model
            return model
        }
    }
}