package robin.vitalij.fortniteassitant.repository

import robin.vitalij.fortniteassitant.model.enums.FirebaseDynamicLinkType
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseDynamicLinkRepository @Inject constructor() {

    var firebaseDynamicLinkType: FirebaseDynamicLinkType? = null
    var id: String? = null

    fun clear() {
        firebaseDynamicLinkType = null
        id = null
    }
}