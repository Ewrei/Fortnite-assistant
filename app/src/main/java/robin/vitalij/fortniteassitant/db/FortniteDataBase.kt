package robin.vitalij.fortniteassitant.db

import androidx.room.Database
import androidx.room.RoomDatabase
import robin.vitalij.fortniteassitant.db.dao.*
import robin.vitalij.fortniteassitant.db.entity.*

@Database(
    entities = [UserEntity::class, MatchEntity::class, PlayerSession::class, WeaponEntity::class, FishEntity::class],
    version = 2
)

abstract class FortniteDataBase : RoomDatabase() {

    abstract fun matchDao(): MatchDao

    abstract fun saveDao(): SaveDao

    abstract fun userDao(): UserDao

    abstract fun weaponDao(): WeaponDao

    abstract fun fishDao(): FishDao

}