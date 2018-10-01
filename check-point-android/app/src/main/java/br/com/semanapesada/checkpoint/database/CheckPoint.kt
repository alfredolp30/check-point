package br.com.semanapesada.checkpoint.database

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
class CheckPoint {
    @PrimaryKey(autoGenerate = true)
    var uid: Int? = null
    var datetime: String? = null
    var entering: Boolean = false


    constructor(datetime: String, entering: Boolean) {
        this.datetime = datetime
        this.entering = entering
    }
}
