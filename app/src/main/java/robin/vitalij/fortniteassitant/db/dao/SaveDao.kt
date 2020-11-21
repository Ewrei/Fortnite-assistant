package robin.vitalij.fortniteassitant.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Transaction
import robin.vitalij.fortniteassitant.db.entity.MatchEntity
import robin.vitalij.fortniteassitant.db.entity.PlayerSession
import robin.vitalij.fortniteassitant.db.entity.UserEntity
import robin.vitalij.fortniteassitant.model.SaveUserModel

@Dao
interface SaveDao {

    @Transaction
    fun insertFullUser(
        saveUserModel: SaveUserModel
    ) {
        val sessionId = insertPlayerSession(saveUserModel.session)
        saveUserModel.userEntity.playerSessionId = sessionId

        insertUser(saveUserModel.userEntity)
        insertMatch(saveUserModel.matches)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPlayerSession(playerSession: PlayerSession): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(userEntity: UserEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMatch(maps: List<MatchEntity>)

}