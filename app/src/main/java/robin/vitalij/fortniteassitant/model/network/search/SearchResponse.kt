package robin.vitalij.fortniteassitant.model.network.search

import com.google.gson.annotations.SerializedName

data class SearchResponse(
    @SerializedName("result") val result: Boolean,
    @SerializedName("account_id") val accountId: String,
    @SerializedName("all_matches") val allMatches: List<MatchesModel>,
    @SerializedName("error") val error: String
)

data class MatchesModel(
    @SerializedName("accountId") val accountId: String,
    @SerializedName("matchType") val matchType: String,
    @SerializedName("epicMutuals") val epicMutuals: String,
    @SerializedName("sortPosition") val sortPosition: Int,
    @SerializedName("matches") val matches: List<MatchesBodyModel>
)

data class MatchesBodyModel(
    @SerializedName("value") val value: String,
    @SerializedName("platform") val platform: String
)

data class SearchUserModel(
    @SerializedName("result") val result: Boolean,
    @SerializedName("account_id") val accountId: String
)