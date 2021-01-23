package robin.vitalij.fortniteassitant.db.migration

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

object Migration {
    val MIGRATION_1_2: Migration = object : androidx.room.migration.Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL("CREATE TABLE `Banner` (`id` TEXT NOT NULL, `name` TEXT NOT NULL, `description` TEXT, `devName` TEXT NOT NULL, `category` TEXT NOT NULL, `fullUsageRights` INTEGER NOT NULL, `bannerImage` TEXT NOT NULL, PRIMARY KEY(`id`))")
        }
    }
}