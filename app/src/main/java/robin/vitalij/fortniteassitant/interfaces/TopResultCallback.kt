package robin.vitalij.fortniteassitant.interfaces

import robin.vitalij.fortniteassitant.model.TopFullModel

interface TopResultCallback {

    fun checkTop(topType: TopFullModel)
}