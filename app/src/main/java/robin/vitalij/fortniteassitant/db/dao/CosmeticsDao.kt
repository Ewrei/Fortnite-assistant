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
    fun getCosmetics(): Single<List<CosmeticsEntity>>

    @Query("SELECT * FROM Cosmetics WHERE typevalue = :type")
    fun getCosmetics(type: String): Single<List<CosmeticsEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCosmetics(list: List<CosmeticsEntity>)

    @Query("DELETE FROM Cosmetics")
    fun removeCosmetics()

}