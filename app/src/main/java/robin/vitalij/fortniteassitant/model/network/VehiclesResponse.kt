package robin.vitalij.fortniteassitant.model.network

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class VehiclesResponse(
    @SerializedName("result") val result: Boolean,
    @SerializedName("lang") val lang: String,
    @SerializedName("vehicles") val vehicles: List<VehicleModel>
)

@Parcelize
data class VehicleModel(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("icon") val icon: String,
    @SerializedName("stats") val stats: VehicleStatsModel,
    @SerializedName("spawnNames") val spawnNames: List<String>,
    @SerializedName("gears") val gears: List<VehicleGearsModel>
) : Parcelable

@Parcelize
data class VehicleGearsModel(
    @SerializedName("TopSpeed") val topSpeed: String,
    @SerializedName("MinSpeed") val minSpeed: String,
    @SerializedName("PushForce") val pushForce: String,
    @SerializedName("RampTime") val rampTime: String,
    @SerializedName("SteeringAngleMultiplier") val steeringAngleMultiplier: String,
    @SerializedName("bAutoBrake") val bAutoBrake: Boolean,
    @SerializedName("bIgnoreGravity") val bIgnoreGravity: Boolean
) : Parcelable

@Parcelize
data class VehicleStatsModel(
    @SerializedName("Default.Valet.MinSpawnPercent.SportsCar") val minSpawnPercent: String,
    @SerializedName("Default.Valet.MaxSpawnPercent.SportsCar") val maxSpawnPercent: String,
    @SerializedName("Default.Valet.SportsCar.MaxCanSpawn") val maxCanSpawn: String,
    @SerializedName("Default.Valet.MinPercentWithGas.SportsCar") val minPercentWithGas: String,
    @SerializedName("Default.Valet.MaxPercentWithGas.SportsCar") val maxPercentWithGas: String,
    @SerializedName("Default.Valet.MinPercentInoperable.SportsCar") val minPercentInoperable: String,
    @SerializedName("Default.Valet.MaxPercentInoperable.SportsCar") val maxPercentInoperable: String,
    @SerializedName("Default.Valet.ScalePontoonTickRate.Rear.SportsCar") val scalePontoonTickRateRear: String,
    @SerializedName("Default.Valet.ScalePontoonTickRate.Front.SportsCar") val scalePontoonTickRateFront: String,
    @SerializedName("AthenaValetSmall.Defaults.FortAttributeSet_VehicleFuel.MinFuelAtSpawn") val fortAttributeSetVehicleFuelMinFuelAtSpawn: String,
    @SerializedName("AthenaValetSmall.Defaults.FortAttributeSet_VehicleFuel.MaxFuelAtSpawn") val FortAttributeSetVehicleFuelMaxFuelAtSpawn: String,
) : Parcelable