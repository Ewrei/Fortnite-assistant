package robin.vitalij.fortniteassitant.db.entity

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "CosmeticsNew")
class CosmeticsNewEntity(
    @PrimaryKey
    @SerializedName("id") var id: String,
    @SerializedName("name") var name: String,
    @SerializedName("description") var description: String?,
    @Embedded(prefix = "type") @SerializedName("type") var type: Type,
    @Embedded(prefix = "rarity") @SerializedName("rarity") var rarity: Rarity?,
    @Embedded(prefix = "series") @SerializedName("series") var series: Series?,
    @Embedded(prefix = "set") @SerializedName("set") var set: Set?,
    @Embedded(prefix = "introduction") @SerializedName("introduction") var introduction: Introduction?,
    @Embedded(prefix = "images") @SerializedName("images") var images: Images?,
    @SerializedName("variants") var variants: List<Variant>?,
    @SerializedName("gameplayTags") var gameplayTags: List<String>?,
    @SerializedName("added") var added: String?
)

@Parcelize
class Series(
    @SerializedName("value") var value: String,
    @SerializedName("image") var image: String?,
    @SerializedName("backendValue") var backendValue: String
) : Parcelable

@Parcelize
class Type(
    @SerializedName("value") var value: String,
    @SerializedName("displayValue") var displayValue: String,
    @SerializedName("backendValue") var backendValue: String
) : Parcelable

@Parcelize
class Rarity(
    @SerializedName("value") var value: String,
    @SerializedName("displayValue") var displayValue: String,
    @SerializedName("backendValue") var backendValue: String
) : Parcelable

class Set(
    @SerializedName("value") var value: String?,
    @SerializedName("text") var text: String?,
    @SerializedName("backendValue") var backendValue: String?
)

class Introduction(
    @SerializedName("chapter") var chapter: String?,
    @SerializedName("season") var season: String?,
    @SerializedName("text") var text: String?,
    @SerializedName("backendValue") var backendValue: Int?
)

class Images(
    @SerializedName("smallIcon") var smallIcon: String?,
    @SerializedName("icon") var icon: String?,
    @SerializedName("featured") var featured: String?,
    @Embedded(prefix = "other") @SerializedName("other") var other: Other?
)

class Other(
    @SerializedName("coverart") var coverart: String
)

class Variant(
    @SerializedName("channel") var channel: String,
    @SerializedName("type") var type: String,
    @SerializedName("options") var options: List<Option>?
)

class Option(
    @SerializedName("tag") var tag: String,
    @SerializedName("name") var name: String,
    @SerializedName("image") var image: String
)