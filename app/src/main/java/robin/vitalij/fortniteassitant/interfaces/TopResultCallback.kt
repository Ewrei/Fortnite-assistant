package robin.vitalij.fortniteassitant.interfaces

import robin.vitalij.fortniteassitant.model.enums.TopType

interface TopResultCallback {

    fun checkTop(topType: TopType)
}