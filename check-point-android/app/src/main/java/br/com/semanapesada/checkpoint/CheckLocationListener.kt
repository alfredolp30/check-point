package br.com.semanapesada.checkpoint

import android.location.Location
import android.location.LocationListener
import android.os.Bundle
import br.com.semanapesada.checkpoint.database.CheckPoint
import br.com.semanapesada.checkpoint.extension.datetimeKeyFormat
import java.util.*


class CheckLocationListener : LocationListener {
    override fun onLocationChanged(location: Location?) {
        if (CheckApp.prefs.getBoolean(PreferenceKey.HAS_LOCAL.name, true)) {
            val latitude = Double.fromBits(CheckApp.prefs.getLong(PreferenceKey.LOCAL_LATITUDE.name, 0L))
            val longitude = Double.fromBits(CheckApp.prefs.getLong(PreferenceKey.LOCAL_LONGITUDE.name, 0L))

            val locationCheck = Location("")
            locationCheck.latitude = latitude
            locationCheck.longitude = longitude
            locationCheck.altitude = location?.altitude ?: 0.0

            val meters = location?.distanceTo(locationCheck) ?: 0f

            CheckApp.prefs.putInt(PreferenceKey.LAST_DISTANCE.name, meters.toRawBits())

            if (CheckApp.prefs.containsKey(PreferenceKey.IS_INSIDE.name)) {
                val isInside = CheckApp.prefs.getBoolean(PreferenceKey.IS_INSIDE.name, true)

                if (isInside && meters > 10f) {
                    CheckApp.prefs.putBoolean(PreferenceKey.IS_INSIDE.name, false)

                    saveCheckPoint(false)
                } else if (!isInside && meters <= 10f) {
                    CheckApp.prefs.putBoolean(PreferenceKey.IS_INSIDE.name, true)

                    saveCheckPoint(true)
                }
            } else {
                CheckApp.prefs.putBoolean(PreferenceKey.IS_INSIDE.name, meters < 10f)
            }
        }
    }

    private fun saveCheckPoint(entering: Boolean) {
        val datetime = GregorianCalendar().datetimeKeyFormat()

        if (datetime.isNotBlank()) {
            CheckApp.db.pointDao().add(CheckPoint(datetime, entering))
        }
    }

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {}

    override fun onProviderEnabled(provider: String?) {}

    override fun onProviderDisabled(provider: String?) {}
}