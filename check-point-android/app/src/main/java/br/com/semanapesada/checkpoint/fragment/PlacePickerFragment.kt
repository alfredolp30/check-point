package br.com.semanapesada.checkpoint.fragment

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.semanapesada.checkpoint.R

import kotlinx.android.synthetic.main.fragment_place_picker.*
import com.google.android.gms.location.places.ui.PlacePicker
import java.lang.Exception
import android.widget.Toast
import android.content.Intent
import android.location.LocationManager
import br.com.semanapesada.checkpoint.CheckApp
import br.com.semanapesada.checkpoint.PreferenceKey
import br.com.semanapesada.checkpoint.extension.showToast


class PlacePickerFragment : Fragment() {
    companion object {
        const val PLACE_PICKER_REQUEST = 1
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_place_picker, null)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        btnPicker.setOnClickListener {
            placePicker()
        }

        if (CheckApp.prefs.getBoolean(PreferenceKey.HAS_LOCAL.name, true)) {
            txtLocal.text = getString(R.string.local, CheckApp.prefs.getString(PreferenceKey.LOCAL_ADDRESS.name))
        }
    }

    private fun placePicker() {
        val builder = PlacePicker.IntentBuilder()

        try {
            startActivityForResult(builder.build(activity), PLACE_PICKER_REQUEST)
        } catch (e: Exception) {

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                val place = PlacePicker.getPlace(context, data)

                context?.showToast(getString(R.string.selected_location), Toast.LENGTH_LONG)

                CheckApp.prefs.putBoolean(PreferenceKey.HAS_LOCAL.name, true)
                CheckApp.prefs.putLong(PreferenceKey.LOCAL_LATITUDE.name, place.latLng.latitude.toRawBits())
                CheckApp.prefs.putLong(PreferenceKey.LOCAL_LONGITUDE.name, place.latLng.longitude.toRawBits())
                CheckApp.prefs.putString(PreferenceKey.LOCAL_NAME.name, place.name.toString())
                CheckApp.prefs.putString(PreferenceKey.LOCAL_ADDRESS.name, place.address.toString())
                CheckApp.prefs.removeKey(PreferenceKey.IS_INSIDE.name)

                txtLocal.text = getString(R.string.local, place.address)
            }
        }
    }

}