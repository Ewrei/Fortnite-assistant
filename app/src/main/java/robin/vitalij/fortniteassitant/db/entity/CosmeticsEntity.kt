package robin.vitalij.fortniteassitant.db.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "Cosmetics")
class CosmeticsEntity(
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