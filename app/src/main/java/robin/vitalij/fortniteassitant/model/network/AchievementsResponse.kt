package robin.vitalij.fortniteassitant.model.network

import com.google.gson.annotations.SerializedName
import robin.vitalij.fortniteassitant.db.entity.AchievementEntity

class AchievementResponse(
    @SerializedName("result") val result: Boolean,
    @SerializedName("season") val seaon: Int,
    @SerializedName("achievements") val achievements: List<AchievementEntity>
)