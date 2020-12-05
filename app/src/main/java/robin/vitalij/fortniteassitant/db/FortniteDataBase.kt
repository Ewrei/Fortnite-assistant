package robin.vitalij.fortniteassitant.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import robin.vitalij.fortniteassitant.db.converter.OptionConverter
import robin.vitalij.fortniteassitant.db.converter.StringConverter
import robin.vitalij.fortniteassitant.db.converter.VariantConverter
import robin.vitalij.fortniteassitant.db.dao.*
import robin.vitalij.fortniteassitant.db.entity.*

@Database(
    entities = [
        UserEntity::class,
        MatchEntity::class,
        PlayerSession::class,
        WeaponEntity::class,
        FishEntity::class,
        AchievementEntity::class,
        CosmeticsNewEntity::class,
        CosmeticsEntity::class
    ],
    version = 1
)

@TypeConverters(VariantConverter::class, StringConverter::class, OptionConverter::class)
abstract class FortniteDataBase : RoomDatabase() {

    abstract fun matchDao(): MatchDao

    abstract fun saveDao(): SaveDao

    abstract fun userDao(): UserDao

    abstract fun weaponDao(): WeaponDao

    abstract fun fishDao(): FishDao

    abstract fun achievementDao(): AchievementDao

    abstract fun cosmeticsNewDao(): CosmeticsNewDao

    abstract fun cosmeticsDao(): CosmeticsDao

}