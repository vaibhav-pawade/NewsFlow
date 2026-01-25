package com.example.newsflow.data.local

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [BookmarkEntity::class], version = 1, exportSchema = false)
abstract class NewsDatabase : RoomDatabase() {
    abstract fun bookmarkDao(): BookmarkDao
}