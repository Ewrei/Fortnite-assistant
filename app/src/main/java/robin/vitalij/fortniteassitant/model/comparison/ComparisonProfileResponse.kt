package robin.vitalij.fortniteassitant.model.comparison

data class ComparisonProfileResponse(
    val playerModel: PlayerModel = PlayerModel(),
    val playerTwoModel: PlayerModel = PlayerModel()
)