package robin.vitalij.fortniteassitant.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "Fish")
class FishEntity(
    @PrimaryKey
    @ColumnInfo(name = "id") var id: String,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "description") var description: String,
    @ColumnInfo(name = "details") var details: String,
    @ColumnInfo(name = "needsProFishingRod") var needsProFishingRod: Boolean,
    @ColumnInfo(name = "sizeMin") var sizeMin: Int,
    @ColumnInfo(name = "sizeMax") var sizeMax: Int,
    @ColumnInfo(name = "heal") var heal: Int,
    @ColumnInfo(name = "rarity") var rarity: String,
    @ColumnInfo(name = "maxStackSize") var maxStackSize: Int,
    @ColumnInfo(name = "image") var image: String
): Serializable {

    fun getFullSize() = "$sizeMin cm - $sizeMax cm"

}