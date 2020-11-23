package robin.vitalij.fortniteassitant.db.dao

import androidx.room.Dao
import androidx.room.Query
import io.reactivex.Flowable
import robin.vitalij.fortniteassitant.db.entity.MatchEntity

@Dao
interface MatchDao {

    @Query("SELECT * FROM Match WHERE playerId = :playerId")
    fun getHistoryMatch(playerId: String): Flowable<List<MatchEntity>>
}