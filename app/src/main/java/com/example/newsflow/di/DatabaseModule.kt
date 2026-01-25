package com.example.newsflow.di

import android.content.Context
import androidx.room.Room
import com.example.newsflow.data.local.BookmarkDao
import com.example.newsflow.data.local.NewsDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun providesDb(@ApplicationContext context: Context): NewsDatabase =
        Room.databaseBuilder(context, NewsDatabase::class.java, "news_db").build()

    @Provides
    @Singleton
    fun providesBookmarkDao(db: NewsDatabase): BookmarkDao = db.bookmarkDao()
}