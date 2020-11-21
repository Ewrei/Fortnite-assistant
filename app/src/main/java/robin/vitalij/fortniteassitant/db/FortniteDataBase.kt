package robin.vitalij.fortniteassitant.db

import androidx.room.Database
import androidx.room.RoomDatabase
import robin.vitalij.fortniteassitant.db.dao.MatchDao
import robin.vitalij.fortniteassitant.db.dao.SaveDao
import robin.vitalij.fortniteassitant.db.dao.UserDao
import robin.vitalij.fortniteassitant.db.entity.MatchEntity
import robin.vitalij.fortniteassitant.db.entity.PlayerSession
import robin.vitalij.fortniteassitant.db.entity.UserEntity

@Database(
    entities = [UserEntity::class, MatchEntity::class, PlayerSession::class],
    version = 2
)

abstract class FortniteDataBase : RoomDatabase() {

    abstract fun matchDao(): MatchDao

    abstract fun saveDao(): SaveDao

    abstract fun userDao(): UserDao

}