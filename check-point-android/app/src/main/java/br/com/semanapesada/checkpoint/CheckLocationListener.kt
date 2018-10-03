package br.com.semanapesada.checkpoint

import android.location.Location
import android.location.LocationListener
import android.os.Bundle
import android.util.Log
import br.com.semanapesada.checkpoint.database.CheckPoint
import br.com.semanapesada.checkpoint.event.MessageEvent
import br.com.semanapesada.checkpoint.extension.datetimeKeyFormat
import org.greenrobot.eventbus.EventBus
import java.util.*


class CheckLocationListener : LocationListener {

    companion object {
        const val RATIO_DEFAULT = 10
    }

    val ratio: Float get() = AppCheck.prefs.getInt(PreferenceKey.RATIO.name, RATIO_DEFAULT).toFloat()

    override fun onLocationChanged(location: Location?) {
        Log.d("CHANGE_LONGITUDE", location?.longitude?.toString())
        Log.d("CHANGE_LATITUDE", location?.latitude?.toString())

        if (AppCheck.prefs.getBoolean(PreferenceKey.HAS_LOCAL.name, false)) {
            val latitude = Double.fromBits(AppCheck.prefs.getLong(PreferenceKey.LOCAL_LATITUDE.name, 0L))
            val longitude = Double.fromBits(AppCheck.prefs.getLong(PreferenceKey.LOCAL_LONGITUDE.name, 0L))

            val locationCheck = Location("")
            locationCheck.latitude = latitude
            locationCheck.longitude = longitude
            locationCheck.altitude = location?.altitude ?: 0.0

            val meters = location?.distanceTo(locationCheck) ?: 0f
            val accuracy = location?.accuracy ?: 0f

            AppCheck.prefs.putInt(PreferenceKey.LAST_DISTANCE.name, meters.toRawBits())
            EventBus.getDefault().post(MessageEvent.ChangeDistance(meters))

            if (AppCheck.prefs.containsKey(PreferenceKey.IS_INSIDE.name)) {
                val isInside = AppCheck.prefs.getBoolean(PreferenceKey.IS_INSIDE.name, true)

                if (isInside && meters > ratio) {
                    AppCheck.prefs.putBoolean(PreferenceKey.IS_INSIDE.name, false)

                    saveCheckPoint(false, accuracy)
                } else if (!isInside && meters <= ratio) {
                    AppCheck.prefs.putBoolean(PreferenceKey.IS_INSIDE.name, true)

                    saveCheckPoint(true, accuracy)
                }
            } else {
                Log.d("IS_INSIDE", "not contains")
                AppCheck.prefs.putBoolean(PreferenceKey.IS_INSIDE.name, meters < ratio)
            }
        }
    }

    private fun saveCheckPoint(entering: Boolean, accuracy: Float) {
        val datetime = GregorianCalendar().datetimeKeyFormat()

        if (datetime.isNotBlank()) {
            val checkPoint = CheckPoint(datetime, entering, accuracy)
            checkPoint.uid = AppCheck.db.pointDao().add(checkPoint) ?: 0

            EventBus.getDefault().post(MessageEvent.NewCheckPoint(checkPoint))
        }
    }

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {}

    override fun onProviderEnabled(provider: String?) {}

    override fun onProviderDisabled(provider: String?) {}
}