package robin.vitalij.fortniteassitant.model.network

import com.google.gson.annotations.SerializedName

class WeaponResponse(
    @SerializedName("result") val result: Boolean,
    @SerializedName("lang") val lang: String,
    @SerializedName("weapons") val weapons: List<Weapon>
)

class Weapon(
    @SerializedName("id") val id: String,
    @SerializedName("enabled") val enabled: Boolean,
    @SerializedName("name") val name: String,
    @SerializedName("description") val description: String?,
    @SerializedName("rarity") val rarity: String,
    @SerializedName("type") val type: String,
    @SerializedName("gameplayTags") val gameplayTags: List<String>,
    @SerializedName("searchTags") val searchTags: String,
    @SerializedName("images") val images: Image?,
    @SerializedName("mainStats") val mainStats: MainStats
)

class Image(
    @SerializedName("icon") val icon: String?,
    @SerializedName("background") val background: String?
)

class MainStats(
    @SerializedName("DmgPB") val dmgPB: Double,
    @SerializedName("FiringRate") val firingRate: Double,
    @SerializedName("ClipSize") val clipSize: Double,
    @SerializedName("ReloadTime") val reloadTime: Double,
    @SerializedName("BulletsPerCartridge") val bulletsPerCartridge: Double,
    @SerializedName("Spread") val spread: Double,
    @SerializedName("SpreadDownsights") val spreadDownsights: Double,
    @SerializedName("DamageZone_Critical") val damageZoneCritical: Double
)