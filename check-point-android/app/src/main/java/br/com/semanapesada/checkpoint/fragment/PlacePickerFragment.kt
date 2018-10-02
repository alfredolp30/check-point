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
import android.util.Log
import br.com.semanapesada.checkpoint.AppCheck
import br.com.semanapesada.checkpoint.PreferenceKey
import br.com.semanapesada.checkpoint.extension.showToast
import android.support.v7.app.AlertDialog
import android.widget.SeekBar
import android.widget.TextView
import br.com.semanapesada.checkpoint.CheckLocationListener.Companion.RATIO_DEFAULT
import br.com.semanapesada.checkpoint.event.MessageEvent
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.lang.ref.WeakReference


class PlacePickerFragment : Fragment(), SeekBar.OnSeekBarChangeListener {

    private var weakTxtDialogRation: WeakReference<TextView>? = null

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

        if (AppCheck.prefs.getBoolean(PreferenceKey.HAS_LOCAL.name, false)) {
            txtLocal.text = getString(R.string.local, AppCheck.prefs.getString(PreferenceKey.LOCAL_ADDRESS.name))
        }

        val meters = AppCheck.prefs.getInt(PreferenceKey.RATIO.name, RATIO_DEFAULT)
        txtRatio.text = getString(R.string.ratio, meters)

        btnRatio.setOnClickListener {
            createDialogRatio()
        }

        val distance = Float.fromBits(AppCheck.prefs.getInt(PreferenceKey.LAST_DISTANCE.name, 0))
        txtDistance.text = getString(R.string.distance, distance.toString())
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        EventBus.getDefault().register(this)
    }

    override fun onDetach() {
        super.onDetach()

        EventBus.getDefault().unregister(this)
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

                AppCheck.prefs.putBoolean(PreferenceKey.HAS_LOCAL.name, true)
                AppCheck.prefs.putLong(PreferenceKey.LOCAL_LATITUDE.name, place.latLng.latitude.toRawBits())
                AppCheck.prefs.putLong(PreferenceKey.LOCAL_LONGITUDE.name, place.latLng.longitude.toRawBits())
                AppCheck.prefs.putString(PreferenceKey.LOCAL_NAME.name, place.name.toString())
                AppCheck.prefs.putString(PreferenceKey.LOCAL_ADDRESS.name, place.address.toString())
                AppCheck.prefs.removeKey(PreferenceKey.IS_INSIDE.name)

                Log.d("PLACE_LONGITUDE", place.latLng.longitude.toString())
                Log.d("PLACE_LATITUDE", place.latLng.latitude.toString())

                txtLocal.text = getString(R.string.local, place.address)
            }
        }
    }


    private fun createDialogRatio() {
        val dialogBuilder = AlertDialog.Builder(context!!)

        dialogBuilder.setTitle("Raio de distância")
        dialogBuilder.setCancelable(true)

        val view = LayoutInflater.from(context).inflate(R.layout.dialog_ratio, null)
        dialogBuilder.setView(view)

        dialogBuilder.setPositiveButton("Escolher") { dialog, _ ->
            val seekBarRation = (dialog as? AlertDialog)?.findViewById<SeekBar>(R.id.seekBarRatio)

            val meters = 5 + (seekBarRation?.progress ?: 0) * 5

            AppCheck.prefs.putInt(PreferenceKey.RATIO.name, meters)
            AppCheck.prefs.removeKey(PreferenceKey.IS_INSIDE.name)
            AppCheck.prefs.removeKey(PreferenceKey.LAST_DISTANCE.name)

            txtRatio.text = getString(R.string.ratio, meters)
        }

        dialogBuilder.setNegativeButton("Cancelar", null)

        val dialog = dialogBuilder.create()

        val seekBarRatio: SeekBar? = view.findViewById(R.id.seekBarRatio)
        weakTxtDialogRation = WeakReference(view.findViewById(R.id.txtDialogRatio))

        seekBarRatio?.setOnSeekBarChangeListener(this)

        val meters = AppCheck.prefs.getInt(PreferenceKey.RATIO.name, RATIO_DEFAULT)
        val progress = (meters/5) - 1

        weakTxtDialogRation?.get()?.text = getString(R.string.ratio, meters)
        seekBarRatio?.progress = progress

        if (activity?.isDestroyed == false && activity?.isFinishing == false) {
            dialog.show()
        }
    }

    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        val meters = 5 + progress * 5
        weakTxtDialogRation?.get()?.text = getString(R.string.ratio, meters)
    }

    override fun onStartTrackingTouch(p0: SeekBar?) {

    }

    override fun onStopTrackingTouch(p0: SeekBar?) {

    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(changeDistance: MessageEvent.ChangeDistance) {
        txtDistance.text = getString(R.string.distance, changeDistance.meters.toString())
    }

}