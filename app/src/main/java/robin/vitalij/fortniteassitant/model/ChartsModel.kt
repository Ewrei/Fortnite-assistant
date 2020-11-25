package robin.vitalij.fortniteassitant.model

data class ChartsModel(
    var header: Double = 0.0,
    var sessionModels: List<SessionModel> = arrayListOf()
)