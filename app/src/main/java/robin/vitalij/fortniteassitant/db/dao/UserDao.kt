package robin.vitalij.fortniteassitant.db.dao

import androidx.room.Dao
import androidx.room.Query
import io.reactivex.Flowable
import io.reactivex.Maybe
import robin.vitalij.fortniteassitant.db.entity.MatchEntity
import robin.vitalij.fortniteassitant.db.entity.UserEntity
import robin.vitalij.fortniteassitant.db.projection.User
import robin.vitalij.fortniteassitant.db.projection.UserHistory

@Dao
interface UserDao {

    @Query("SELECT * FROM User WHERE player_id = :playerId order by playerSessionId desc limit 1")
    fun getUserFull(playerId: String): Maybe<User>

    @Query("SELECT * FROM User WHERE player_id = :playerId order by playerSessionId desc limit 1")
    fun getFlowableUserEntity(playerId: String): Flowable<UserEntity>

    @Query("SELECT * FROM User WHERE player_id = :playerId order by playerSessionId desc limit 2")
    fun getLastTwoUserEntities(playerId: String): Flowable<List<UserEntity>>

    @Query("SELECT * FROM PlayerSession WHERE accountId = :playerId ORDER BY playerSessionId DESC")
    fun getUserHistory(playerId: String): Flowable<List<UserHistory>>

}