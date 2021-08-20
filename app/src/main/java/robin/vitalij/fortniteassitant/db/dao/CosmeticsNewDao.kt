package robin.vitalij.fortniteassitant.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Single
import robin.vitalij.fortniteassitant.db.entity.CosmeticsEntity
import robin.vitalij.fortniteassitant.db.entity.CosmeticsNewEntity

@Dao
interface CosmeticsNewDao {

    @Query("SELECT * FROM CosmeticsNew")
    fun getCosmeticsNew(): Single<List<CosmeticsNewEntity>>

    @Query("SELECT * FROM CosmeticsNew WHERE id = :id")
    fun getCosmetic(id: String): Single<CosmeticsNewEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCosmeticsNew(list: List<CosmeticsNewEntity>)

    @Query("DELETE FROM CosmeticsNew")
    fun removeCosmeticsNew()

}