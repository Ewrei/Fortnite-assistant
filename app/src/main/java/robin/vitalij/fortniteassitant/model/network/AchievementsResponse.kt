package robin.vitalij.fortniteassitant.model.network

import com.google.gson.annotations.SerializedName
import robin.vitalij.fortniteassitant.db.entity.AchievementEntity

class AchievementResponse(
    @SerializedName("result") var result: Boolean,
    @SerializedName("season") var seaon: Int,
    @SerializedName("achievements") var achievements: List<AchievementEntity>
)