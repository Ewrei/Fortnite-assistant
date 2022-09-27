package robin.vitalij.fortniteassitant.model

import robin.vitalij.fortniteassitant.model.enums.ConfigType

data class ContactUsModel(
    val url: String,
    val configType: ConfigType
)