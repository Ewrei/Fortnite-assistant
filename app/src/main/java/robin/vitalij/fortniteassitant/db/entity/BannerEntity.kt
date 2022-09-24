package robin.vitalij.fortniteassitant.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "Banner")
class BannerEntity(
    @PrimaryKey
    @ColumnInfo(name = "id") var id: String,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "description") var description: String?,
    @ColumnInfo(name = "devName") var devName: String,
    @ColumnInfo(name = "category") var category: String,
    @ColumnInfo(name = "fullUsageRights") var fullUsageRights: Boolean,
    @ColumnInfo(name = "bannerImage") var bannerImage: BannerImage
)

class BannerImage(
    @SerializedName("smallIcon") var smallIcon: String?,
    @SerializedName("icon") var icon: String?,
)