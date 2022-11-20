package robin.vitalij.fortniteassitant.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Single
import robin.vitalij.fortniteassitant.db.entity.CosmeticsEntity
import robin.vitalij.fortniteassitant.db.entity.CosmeticsNewEntity

@Dao
interface CosmeticsDao {

    @Query("SELECT * FROM Cosmetics")
    suspend fun getCosmetics(): List<CosmeticsEntity>

    @Query("SELECT * FROM Cosmetics WHERE typevalue = :type")
    suspend fun getCosmetics(type: String): List<CosmeticsEntity>

    @Query("SELECT * FROM Cosmetics WHERE id = :id")
    suspend fun getCosmetic(id: String): CosmeticsEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCosmetics(list: List<CosmeticsEntity>)

    @Query("DELETE FROM Cosmetics")
    fun removeCosmetics()

}