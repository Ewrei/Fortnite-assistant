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


}