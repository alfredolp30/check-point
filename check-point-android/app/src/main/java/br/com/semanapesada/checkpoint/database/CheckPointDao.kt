package br.com.semanapesada.checkpoint.database

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query

@Dao
interface CheckPointDao {
    @Query("SELECT * FROM CheckPoint")
    fun getList() : List<CheckPoint>

    @Insert
    fun add(checkPoint: CheckPoint): Long?

    @Delete
    fun remove(checkPoint: CheckPoint)
}