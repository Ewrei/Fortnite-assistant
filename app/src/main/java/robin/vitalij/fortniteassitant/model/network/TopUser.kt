package robin.vitalij.fortniteassitant.model.network

import com.google.gson.annotations.SerializedName

class TopUser(
    @SerializedName("playerId") var playerId: String,
    @SerializedName("userName") var userName: String,
    @SerializedName("timePlayed") var timePlayed: Long
)