package robin.vitalij.fortniteassitant.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Single
import robin.vitalij.fortniteassitant.db.entity.PlayerSession
import robin.vitalij.fortniteassitant.db.entity.WeaponEntity

@Dao
interface WeaponDao {

    @Query("SELECT * FROM Weapon")
    fun getWeapons(): Single<List<WeaponEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWeapons(list :List<WeaponEntity>)

}