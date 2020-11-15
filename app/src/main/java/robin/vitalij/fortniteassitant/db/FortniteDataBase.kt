package robin.vitalij.fortniteassitant.db

import androidx.room.Database
import androidx.room.RoomDatabase
import robin.vitalij.fortniteassitant.db.entity.UserEntity

@Database(
    entities = [UserEntity::class],
    version = 1
)

abstract class FortniteDataBase : RoomDatabase() {


}