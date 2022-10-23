package robin.vitalij.fortniteassitant.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Single
import robin.vitalij.fortniteassitant.db.entity.AchievementEntity

@Dao
interface AchievementDao {

    @Query("SELECT * FROM Achievement")
    suspend fun getAchievements(): List<AchievementEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAchievements(list: List<AchievementEntity>)

    @Query("DELETE FROM Achievement")
    fun removeAchievements()

}