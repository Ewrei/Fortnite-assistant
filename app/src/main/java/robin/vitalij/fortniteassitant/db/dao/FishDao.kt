package robin.vitalij.fortniteassitant.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Single
import robin.vitalij.fortniteassitant.db.entity.FishEntity

@Dao
interface FishDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFish(list: List<FishEntity>)

    @Query("SELECT * FROM Fish")
    suspend fun getFish(): List<FishEntity>

    @Query("SELECT * FROM Fish WHERE lower(id) = lower(:fishId)")
    fun getFish(fishId: String): Single<FishEntity>
}