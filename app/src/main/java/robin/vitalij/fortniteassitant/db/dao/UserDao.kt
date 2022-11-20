package robin.vitalij.fortniteassitant.db.dao

import androidx.room.Dao
import androidx.room.Query
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Single
import robin.vitalij.fortniteassitant.db.entity.UserEntity
import robin.vitalij.fortniteassitant.db.projection.User
import robin.vitalij.fortniteassitant.db.projection.UserHistory

@Dao
interface UserDao {

    @Query("SELECT * FROM User WHERE player_id = :playerId order by playerSessionId desc limit 1")
    fun getUserFull(playerId: String): Maybe<User>

    @Query("SELECT * FROM User WHERE player_id = :playerId order by playerSessionId desc limit 1")
    suspend fun getUserEntity(playerId: String): UserEntity

    @Query("SELECT * FROM User WHERE player_id = :playerId order by playerSessionId desc limit 2")
    suspend fun getLastTwoUserEntities(playerId: String): List<UserEntity>

    @Query("SELECT * FROM PlayerSession WHERE accountId = :playerId ORDER BY playerSessionId DESC")
    suspend fun getUserHistory(playerId: String): List<UserHistory>

    @Query("SELECT * FROM User WHERE playerSessionId = :playerSessionId")
    suspend fun getUserEntitySessionIdNewVersion(playerSessionId: Long): UserEntity

    @Query("SELECT *, MAX(alloverallmatches) FROM User GROUP BY player_id")
    suspend fun getUsers(): List<UserEntity>

    @Query("DELETE FROM User WHERE player_id = :playerId")
    fun deleteProfile(playerId: String)

}