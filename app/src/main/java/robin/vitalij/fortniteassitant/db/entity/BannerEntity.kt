package robin.vitalij.fortniteassitant.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "Banner")
class BannerEntity(
    @PrimaryKey
    @SerializedName("id") var id: String,
    @SerializedName("name") var name: String,
    @SerializedName("description") var description: String?,
    @SerializedName("devName") var devName: String,
    @SerializedName("category") var category: String,
    @SerializedName("fullUsageRights") var fullUsageRights: Boolean,
    @SerializedName("images") var bannerImage: BannerImage
)

class BannerImage(
    @SerializedName("smallIcon") var smallIcon: String?,
    @SerializedName("icon") var icon: String?,
)