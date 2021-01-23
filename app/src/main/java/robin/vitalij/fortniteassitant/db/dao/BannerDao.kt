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
    fun getBanner(): Single<List<BannerEntity>>

    @Query("SELECT * FROM Banner WHERE id = :id")
    fun getBanner(id: String): Single<BannerEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBanner(list: List<BannerEntity>)

    @Query("DELETE FROM Banner")
    fun removeBanner()

}