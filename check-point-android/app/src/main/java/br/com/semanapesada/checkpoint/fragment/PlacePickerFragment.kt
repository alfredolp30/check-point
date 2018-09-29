package br.com.semanapesada.checkpoint.fragment

import android.app.Activity
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
import com.google.android.gms.location.places.Place
import android.content.Intent




class PlacePickerFragment : Fragment() {
    companion object {
        const val PLACE_PICKER_REQUEST = 1
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_place_picker, null)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        button.setOnClickListener {
            placePicker()
        }
    }

    fun placePicker() {
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
                val toastMsg = String.format("Place: %s", place.name)
                Toast.makeText(context, toastMsg, Toast.LENGTH_LONG).show()
            }
        }
    }

}