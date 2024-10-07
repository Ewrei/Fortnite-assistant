package robin.vitalij.fortniteassitant.repository.comparison

import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.mapLatest
import robin.vitalij.fortniteassitant.model.LoadingState
import robin.vitalij.fortniteassitant.model.comparison.ComparisonProfileResponse
import robin.vitalij.fortniteassitant.model.enums.BattlesType
import robin.vitalij.fortniteassitant.model.enums.GameType
import robin.vitalij.fortniteassitant.utils.ResourceProvider
import robin.vitalij.fortniteassitant.utils.mapper.ComparisonPlayersMapper
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ComparisonProfileRepository @Inject constructor(private val resourceProvider: ResourceProvider) {

    private val comparisonProfileState =
        MutableStateFlow<LoadingState<ComparisonProfileResponse>>(LoadingState.Loading)

    private val comparisonProfileStateResult: StateFlow<LoadingState<ComparisonProfileResponse>> =
        comparisonProfileState

    private var comparisonSubject: BehaviorSubject<ComparisonProfileResponse> =
        BehaviorSubject.create()

    fun updateProfile(comparisonProfileResponse: ComparisonProfileResponse) {
        comparisonProfileState.value = LoadingState.Success(comparisonProfileResponse)
        comparisonSubject.onNext(comparisonProfileResponse)
    }

    fun getData(): Observable<ComparisonProfileResponse> = comparisonSubject

    fun getComparisonProfileStatistics(
        isSchedule: Boolean,
        battlesType: BattlesType,
        gameType: GameType
    ) = comparisonProfileStateResult.mapLatest {
        return@mapLatest when (it) {
            is LoadingState.Success -> LoadingState.Success(
                ComparisonPlayersMapper(
                    resourceProvider,
                    isSchedule,
                    battlesType,
                    gameType
                ).transform(it.data)
            )

            is LoadingState.Error -> LoadingState.Error(it.cause)
            is LoadingState.Loading -> LoadingState.Loading
        }

    }

}