package com.example.colorlistapp.di

import android.content.Context
import androidx.room.Room
import com.example.colorlistapp.db.AppDatabase
import com.example.colorlistapp.db.ColorDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {



    //This function provide RoomDatabase instance to the class
    @Provides
    @Singleton
    fun provideDataBase(@ApplicationContext context: Context) : AppDatabase =
        Room.databaseBuilder(context , AppDatabase::class.java , "Color.db")
            .fallbackToDestructiveMigration(false)
            .build()


    @Provides
    @Singleton
    fun provideColorDao(appDatabase: AppDatabase) : ColorDao = appDatabase.colorDao()

}