package br.com.semanapesada.checkpoint.fragment

import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.semanapesada.checkpoint.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.MapView



/**
 * Created by Alfredo L. Porfirio on 28/09/18.
 * Copyright Universo Online 2018. All rights reserved.
 */
class LocationFragment : Fragment() {
    var mapView: MapView? = null
    var googleMap: GoogleMap? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_location, null)

        val mapView = rootView.findViewById(R.id.mapView) as? MapView

        mapView?.onCreate(savedInstanceState)
        mapView?.onResume() // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(activity!!.applicationContext)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        if (ContextCompat.checkSelfPermission(context!!, android.Manifest.permission.ACCESS_COARSE_LOCATION ) != PackageManager.PERMISSION_GRANTED ) {
            ActivityCompat.requestPermissions( activity!!, arrayOf(android.Manifest.permission.ACCESS_COARSE_LOCATION), 1)
        } else {
            configGoogleMaps()
        }

        return rootView
    }


    fun configGoogleMaps() {
        mapView?.getMapAsync { mMap ->
            googleMap = mMap
            // For showing a move to my location button
            googleMap?.isMyLocationEnabled = true

            // For dropping a marker at a point on the Map
            val sydney = LatLng(-34.0, 151.0)
            googleMap?.addMarker(MarkerOptions().position(sydney).title("Marker Title").snippet("Marker Description"))

            // For zooming automatically to the location of the marker
            val cameraPosition = CameraPosition.Builder().target(sydney).zoom(12f).build()
            googleMap?.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
        }
    }
}