package robin.vitalij.fortniteassitant.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "Weapon")
class WeaponEntity(
    @PrimaryKey
    @ColumnInfo(name = "weapon_id") var weaponId: String = "",
    @ColumnInfo(name = "id") var id: String = "",
    @ColumnInfo(name = "enabled") var enabled: Boolean = false,
    @ColumnInfo(name = "name") var name: String = "",
    @ColumnInfo(name = "description") var description: String = "",
    @ColumnInfo(name = "rarity") var rarity: String = "",
    @ColumnInfo(name = "type") var type: String = "",
    @ColumnInfo(name = "searchTags") var searchTags: String = "",
    @ColumnInfo(name = "background") var background: String = "",
    @ColumnInfo(name = "dmgPB") var dmgPB: Double = 0.0,
    @ColumnInfo(name = "firingRate") var firingRate: Double = 0.0,
    @ColumnInfo(name = "clipSize") var clipSize: Double = 0.0,
    @ColumnInfo(name = "reloadTime") var reloadTime: Double = 0.0,
    @ColumnInfo(name = "bulletsPerCartridge") var bulletsPerCartridge: Double = 0.0,
    @ColumnInfo(name = "spread") var spread: Double = 0.0,
    @ColumnInfo(name = "spreadDownsights") var spreadDownsights: Double = 0.0,
    @ColumnInfo(name = "damageZone_Critical") var damageZoneCritical: Double = 0.0
): Serializable