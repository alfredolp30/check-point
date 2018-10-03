package br.com.semanapesada.checkpoint.database

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
class CheckPoint {
    @PrimaryKey(autoGenerate = true)
    var uid: Long? = null
    var datetime: String? = null
    var entering: Boolean = false
    var accuracy: Float = 0f

    constructor(datetime: String, entering: Boolean, accuracy: Float) {
        this.datetime = datetime
        this.entering = entering
        this.accuracy = accuracy
    }
}
