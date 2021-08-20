package robin.vitalij.fortniteassitant.repository.comparison

import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import robin.vitalij.fortniteassitant.model.comparison.ComparisonProfileResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ComparisonProfileRepository @Inject constructor() {

    private var comparisonSubject: BehaviorSubject<ComparisonProfileResponse> =
        BehaviorSubject.create()

    fun updateProfile(comparisonProfileResponse: ComparisonProfileResponse) {
        comparisonSubject.onNext(comparisonProfileResponse)
    }

    fun getData(): Observable<ComparisonProfileResponse> = comparisonSubject

}