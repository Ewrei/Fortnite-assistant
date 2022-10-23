package robin.vitalij.fortniteassitant.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Single
import robin.vitalij.fortniteassitant.db.entity.BannerEntity

@Dao
interface BannerDao {

    @Query("SELECT * FROM Banner")
    suspend fun getBanners(): List<BannerEntity>

    @Query("SELECT * FROM Banner WHERE id = :id")
    fun getBanner(id: String): Single<BannerEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBanners(list: List<BannerEntity>)

    @Query("DELETE FROM Banner")
    fun removeBanners()

}