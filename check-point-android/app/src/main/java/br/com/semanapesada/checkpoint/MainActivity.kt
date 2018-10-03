package br.com.semanapesada.checkpoint

import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import br.com.semanapesada.checkpoint.fragment.CheckFragment
import br.com.semanapesada.checkpoint.fragment.ConfigFragment
import br.com.semanapesada.checkpoint.fragment.PlacePickerFragment
import kotlinx.android.synthetic.main.activity_main.*
import android.location.LocationManager
import android.support.v4.content.ContextCompat
import android.location.Criteria




class MainActivity : AppCompatActivity() {
    private val listener = CheckLocationListener()

    companion object {
        const val REQUEST_CODE_LOCATION = 10
    }

    private val itemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                supportFragmentManager.beginTransaction().replace(R.id.containerFragment, PlacePickerFragment()).commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                supportFragmentManager.beginTransaction().replace(R.id.containerFragment, CheckFragment()).commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                supportFragmentManager.beginTransaction().replace(R.id.containerFragment, ConfigFragment()).commit()
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigation.setOnNavigationItemSelectedListener(itemSelectedListener)

        supportFragmentManager.beginTransaction().add(R.id.containerFragment, PlacePickerFragment()).commit()

        listenerLocation()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == REQUEST_CODE_LOCATION) {
            permissions.forEachIndexed { index, _ ->
                if (grantResults[index] != PackageManager.PERMISSION_GRANTED) {
                    return
                }
            }

            listenerLocation()
        }
    }

    private fun listenerLocation() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
        || ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(android.Manifest.permission.ACCESS_COARSE_LOCATION,
                    android.Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_CODE_LOCATION)
        } else {
            val locationManager = getSystemService(Context.LOCATION_SERVICE) as? LocationManager


            locationManager?.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 20000, 20f, listener)
        }
    }
}
