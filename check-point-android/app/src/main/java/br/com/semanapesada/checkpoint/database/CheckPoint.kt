package br.com.semanapesada.checkpoint.database

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
class Point {
    @PrimaryKey(autoGenerate = true)
    private val uid: Int? = null
    private var datetime: String? = null
    private var entering: Boolean = false


    constructor(datetime: String, entering: Boolean) {
        this.datetime = datetime
        this.entering = entering
    }
}
