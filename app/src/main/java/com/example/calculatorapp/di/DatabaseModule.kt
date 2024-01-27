package com.example.calculatorapp.di

import android.content.Context
import com.example.calculatorapp.database.AppDatabase
import com.example.calculatorapp.database.HistoryDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext applicationContext: Context): AppDatabase {
        return AppDatabase.getInstance(applicationContext)
    }

    @Provides
    fun providePlaceDao(appDatabase: AppDatabase): HistoryDao {
        return appDatabase.historyDao()
    }
}