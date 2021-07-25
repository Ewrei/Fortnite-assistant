package robin.vitalij.fortniteassitant.model.network.search

import com.google.gson.annotations.SerializedName

class SearchResponse(
    @SerializedName("result") val result: Boolean,
    @SerializedName("account_id") val accountId: String,
    @SerializedName("all_matches") val allMatches: List<Matches>,
    @SerializedName("error") val error: String
)

class Matches(
    @SerializedName("accountId") val accountId: String,
    @SerializedName("matchType") val matchType: String,
    @SerializedName("epicMutuals") val epicMutuals: String,
    @SerializedName("sortPosition") val sortPosition: Int,
    @SerializedName("matches") val matches: List<MatchesBody>
)

class MatchesBody(
    @SerializedName("value") val value: String,
    @SerializedName("platform") val platform: String
)