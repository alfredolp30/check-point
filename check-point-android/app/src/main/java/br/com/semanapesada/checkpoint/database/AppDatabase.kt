package br.com.semanapesada.checkpoint.database

import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.Database


@Database(entities = [CheckPoint::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun pointDao(): CheckPointDao
}