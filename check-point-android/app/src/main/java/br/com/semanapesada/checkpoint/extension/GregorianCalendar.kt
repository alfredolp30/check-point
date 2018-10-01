package br.com.semanapesada.checkpoint.extension

import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

fun GregorianCalendar.datetimeKeyFormat() : String {
    val format = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())

    return try {
        format.format(this.time)
    } catch (e: Exception) {
        ""
    }
}