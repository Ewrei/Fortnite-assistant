package robin.vitalij.fortniteassitant.interfaces

import robin.vitalij.fortniteassitant.model.network.stats.FortniteProfileResponse

interface RegistrationProfileCallback {

    fun addedProfile(fortniteProfileResponse: FortniteProfileResponse)

}