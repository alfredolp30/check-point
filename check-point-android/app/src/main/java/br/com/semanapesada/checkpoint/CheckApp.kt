package br.com.semanapesada.checkpoint

import android.app.Application
import br.com.semanapesada.checkpoint.database.AppDatabase
import android.arch.persistence.room.Room



class CheckApp : Application() {

    companion object {
        lateinit var prefs : CheckPreferences
        lateinit var db: AppDatabase
    }

    override fun onCreate() {
        super.onCreate()

        prefs = CheckPreferences(this)
        db = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "database.db").allowMainThreadQueries().build()
    }
}