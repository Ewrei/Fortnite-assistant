package robin.vitalij.fortniteassitant.ui.top.adapter.viewmodel

import robin.vitalij.fortniteassitant.model.TopFullModel

class TopHeaderViewModel(val topFullModel: TopFullModel) : Top {
    override fun getType() = TopViewModelType.HEADER
}