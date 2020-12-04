package robin.vitalij.fortniteassitant.di.module

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import robin.vitalij.fortniteassitant.db.FortniteDataBase
import javax.inject.Singleton

private const val DATABASE_NAME = "fortnite-assistant-db"

@Module
class DatabaseModule(context: Context) {

    private val appDatabase = Room
        .databaseBuilder(context, FortniteDataBase::class.java, DATABASE_NAME)
        .fallbackToDestructiveMigration()
        .allowMainThreadQueries()
        .build()

    @Provides
    @Singleton
    fun provideDatabase() = appDatabase

    @Provides
    @Singleton
    fun provideUserDao() = appDatabase.userDao()

    @Provides
    @Singleton
    fun provideSaveDao() = appDatabase.saveDao()

    @Provides
    @Singleton
    fun provideMatchDao() = appDatabase.matchDao()

    @Provides
    @Singleton
    fun provideWeaponDao() = appDatabase.weaponDao()

    @Provides
    @Singleton
    fun provideFishDao() = appDatabase.fishDao()

    @Provides
    @Singleton
    fun provideAchievementDao() = appDatabase.achievementDao()

    @Provides
    @Singleton
    fun provideCosmeticsNewDao() = appDatabase.cosmeticsNewDao()

}