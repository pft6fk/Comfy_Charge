package com.example.comfycharge1

import android.Manifest
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.example.comfycharge1.databinding.ActivityMapsBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.*
import com.google.maps.DirectionsApiRequest
import com.google.maps.GeoApiContext
import com.google.maps.PendingResult
import com.google.maps.internal.PolylineEncoding
import com.google.maps.model.DirectionsResult

class MapsActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener, GoogleMap.OnInfoWindowClickListener {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private lateinit var lastLocation: Location
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    lateinit var drawerLayout: DrawerLayout
    private var currentLatLong = LatLng(41.33299704410577, 69.26452677632705)
    private var mGeoApiContext: GeoApiContext? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        drawerLayout = binding.drawerLayout
        actionBar?.setDisplayHomeAsUpEnabled(false)

        setSupportActionBar(binding.toolBar)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        binding.account.setOnClickListener {
            Toast.makeText(this, "Opens account page", Toast.LENGTH_SHORT).show()
        }

        binding.openDrawer.setOnClickListener { //opens drawer layout
            drawerLayout.openDrawer(GravityCompat.END)
        }

        binding.account.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.setOnMarkerClickListener(this)
        mMap.uiSettings.isZoomControlsEnabled = true

        binding.btFindNearLocation.setOnClickListener {
            setLocations()
        }

        mMap.setOnPolylineClickListener(this)
        mMap.setOnInfoWindowClickListener(this)

        setUpMap()
    }
    private fun setLocations() {
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLong, 11.5f))
        val tashkent = LatLng(41.311081, 69.240562)
        mMap.addMarker(
            MarkerOptions().position(tashkent).title("Fast charger")
                .snippet("Amity University Tashkent")
        )
        val station1 = LatLng(41.321081, 69.230562)
        mMap.addMarker(
            MarkerOptions().position(station1).title("Normal charger").snippet("Tok bor station")
        )
        val station2 = LatLng(41.30396468309177, 69.32219717877584)
        mMap.addMarker(
            MarkerOptions().position(station2).title("Super fast charger")
                .snippet("Makro supermarket")
        )
        val station3 = LatLng(41.30720338596268, 69.29564358105252)
        mMap.addMarker(
            MarkerOptions().position(station3).title("Normal charger").snippet("Makro supermarket")
        )
        val station4 = LatLng(41.34936784158046, 69.17799879592985)
        mMap.addMarker(
            MarkerOptions().position(station4).title("Fast charger").snippet("Tok Bor station")
        )
        val station5 = LatLng(41.296455195349246, 69.26863822018343)
        mMap.addMarker(
            MarkerOptions().position(station5).title("Fast charger")
                .snippet("Grand Mir hotel station")
        )


        binding.btFindNearLocation.visibility = View.GONE
        binding.btStart.visibility = View.GONE
        binding.edChargePercent.visibility = View.GONE
    }
//
//    private fun getDirectionsUrl(): Any {
//
//    }

    private fun setUpMap() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions( //granting permission
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                1
            )

            return
        }

        if (mGeoApiContext == null) {
            mGeoApiContext =
                GeoApiContext.Builder().apiKey("AIzaSyARKFRFzIheLxDt4rTx8hN62HRTwUP7Gv4").build()
        }

        mMap.isMyLocationEnabled = true
        fusedLocationClient.lastLocation.addOnSuccessListener(this) { location ->
            if (location != null) {
                lastLocation = location
//                val currentLatLong = LatLng(location.latitude, location.longitude)
                currentLatLong = LatLng(41.33299704410577, 69.26452677632705)
                placeMarkerOnMap(currentLatLong)
                mMap.animateCamera(
                    CameraUpdateFactory.newLatLngZoom(
                        currentLatLong,
                        17f
                    )
                ) //zooming current location
            }
        }
    }

    private fun placeMarkerOnMap(currentLatLong: LatLng) {
        val markerOptions = MarkerOptions().position(currentLatLong).title("You") //main title
            .snippet("Amity University Tashkent") //sub title
            .icon(bitmapDescriptorFromVector(this, R.drawable.ic_location)) //custom marker
        mMap.addMarker(markerOptions)
    }

    override fun onMarkerClick(p0: Marker?) = false

    private fun openInfo(location: LatLng) {

    }

    //for using custom marker in map
    private fun bitmapDescriptorFromVector(context: Context, vectorResId: Int): BitmapDescriptor? {
        return ContextCompat.getDrawable(context, vectorResId)?.run {
            setBounds(0, 0, intrinsicWidth, intrinsicHeight)
            val bitmap =
                Bitmap.createBitmap(intrinsicWidth, intrinsicHeight, Bitmap.Config.ARGB_8888)
            draw(Canvas(bitmap))
            BitmapDescriptorFactory.fromBitmap(bitmap)
        }
    }

    private fun calculateDirections(marker: Marker) {
        Log.d("TAG", "calculateDirections: calculating directions.")
        val destination = com.google.maps.model.LatLng(
            marker.position.latitude,
            marker.position.longitude
        )
        val directions = DirectionsApiRequest(mGeoApiContext)
        directions.alternatives(true)
        directions.origin(
            com.google.maps.model.LatLng(
                currentLatLong.latitude,
                currentLatLong.longitude
            )
        )
        Log.d("TAG123", "calculateDirections: destination: $destination")
        directions.destination(destination).setCallback(object :
            PendingResult.Callback<DirectionsResult?> {

            override fun onFailure(e: Throwable) {
                Log.e("TAG123", "calculateDirections: Failed to get directions: " + e.message)
            }

            override fun onResult(result: DirectionsResult?) {
                Log.d("TAG123", "calculateDirections: routes: " + result?.routes!![0].toString())
                Log.d("TAG123", "calculateDirections: duration: " + result.routes[0].legs[0].duration)
                Log.d("TAG123", "calculateDirections: distance: " + result.routes[0].legs[0].distance)
                Log.d(
                    "TAG123",
                    "calculateDirections: geocodedWayPoints: " + result.geocodedWaypoints[0].toString()
                )
                addPolylinesToMap(result)
            }
        })
    }

    override fun onInfoWindowClick(marker: Marker) {
        Log.d("TAG123", "inside info window")
        if (marker.snippet.equals("This is you")) {
            marker.hideInfoWindow()
        } else {

            val builder = AlertDialog.Builder(this)


            Log.d("TAG123", "inside else condition")
            builder.setMessage(marker.snippet)
                .setCancelable(true)
                .setPositiveButton(
                    "Yes"
                ) {
                        dialog, id ->
                    calculateDirections(marker)
                    dialog.dismiss() }
                .setNegativeButton(
                    "No"
                ) { dialog, id -> dialog.cancel()
                    Log.d("TAG123", "inside setNegativeButton")}

            val dialog = builder.create()
            dialog.show()

            val buttonPositive = dialog.getButton(DialogInterface.BUTTON_POSITIVE)
            with(buttonPositive) {
                setTextColor(ContextCompat.getColor(context, android.R.color.black))
            }
            val buttonNegative= dialog.getButton(DialogInterface.BUTTON_NEGATIVE)
            with(buttonNegative) {
                setTextColor(ContextCompat.getColor(context, android.R.color.black))
            }


        }
    }

    private fun addPolylinesToMap(result: DirectionsResult) {
        Handler(Looper.getMainLooper()).post(Runnable {
            Log.d("TAG123", "run: result routes: " + result.routes.size)
            for (route in result.routes) {
                Log.d("TAG123", "run: leg: " + route.legs[0].toString())
                val decodedPath = PolylineEncoding.decode(route.overviewPolyline.encodedPath)
                val newDecodedPath: MutableList<LatLng> = ArrayList()

                // This loops through all the LatLng coordinates of ONE polyline.
                for (latLng in decodedPath) {

//                        Log.d(TAG, "run: latlng: " + latLng.toString());
                    newDecodedPath.add(
                        LatLng(
                            latLng.lat,
                            latLng.lng
                        )
                    )
                }
                val polyline: Polyline =
                    mMap.addPolyline(PolylineOptions().addAll(newDecodedPath))
                polyline.color = ContextCompat.getColor(this, android.R.color.darker_gray)
                polyline.isClickable = true
            }
        })
    }
}