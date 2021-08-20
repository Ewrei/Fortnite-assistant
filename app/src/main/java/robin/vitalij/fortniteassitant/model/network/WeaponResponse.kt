package robin.vitalij.fortniteassitant.model.network

import com.google.gson.annotations.SerializedName

class WeaponResponse(
    @SerializedName("result") var result: Boolean,
    @SerializedName("lang") var lang: String,
    @SerializedName("weapons") var weapons: List<Weapon>
)

class Weapon(
    @SerializedName("id") var id: String,
    @SerializedName("enabled") var enabled: Boolean,
    @SerializedName("name") var name: String,
    @SerializedName("description") var description: String?,
    @SerializedName("rarity") var rarity: String,
    @SerializedName("type") var type: String,
    @SerializedName("gameplayTags") var gameplayTags: List<String>,
    @SerializedName("searchTags") var searchTags: String,
    @SerializedName("images") var images: Image?,
    @SerializedName("mainStats") var mainStats: MainStats
)

class Image(
    @SerializedName("icon") var icon: String,
    @SerializedName("background") var background: String?
)

class MainStats(
    @SerializedName("DmgPB") var dmgPB: Double,
    @SerializedName("FiringRate") var firingRate: Double,
    @SerializedName("ClipSize") var clipSize: Double,
    @SerializedName("ReloadTime") var reloadTime: Double,
    @SerializedName("BulletsPerCartridge") var bulletsPerCartridge: Double,
    @SerializedName("Spread") var spread: Double,
    @SerializedName("SpreadDownsights") var spreadDownsights: Double,
    @SerializedName("DamageZone_Critical") var damageZoneCritical: Double
)