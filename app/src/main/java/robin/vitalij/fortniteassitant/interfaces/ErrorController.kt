package robin.vitalij.fortniteassitant.interfaces

import robin.vitalij.fortniteassitant.model.ErrorModel

interface ErrorController {

    fun setError(errorModel: ErrorModel)

    fun hideError()

}