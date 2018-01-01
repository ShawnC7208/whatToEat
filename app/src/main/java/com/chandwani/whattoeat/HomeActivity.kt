package com.chandwani.whattoeat

import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import butterknife.OnClick
import com.lorentzos.flingswipe.SwipeFlingAdapterView
import com.google.android.gms.location.places.GeoDataClient
import com.google.android.gms.location.places.Places

class HomeActivity : AppCompatActivity() {

    private var al: ArrayList<String>? = null
    private var arrayAdapter: ArrayAdapter<String>? = null
    private var i: Int = 0
    private lateinit var mGeoDataClient: GeoDataClient
    private var locationManager : LocationManager? = null
    var lat = 0.0
    var lng = 0.0

    private val locationListener: LocationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            lat=location.latitude
            lng=location.longitude
//            var locationToast = Toast.makeText( this@HomeActivity,
//                    location.longitude.toString() + ":" + location.latitude.toString(),
//                    Toast.LENGTH_SHORT)
//            locationToast.show()
        }
        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
        override fun onProviderEnabled(provider: String) {}
        override fun onProviderDisabled(provider: String) {}
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        locationManager = getSystemService(LOCATION_SERVICE) as LocationManager?

        try {
            // Request location updates
            locationManager?.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000L, 10f, locationListener)
        } catch(ex: SecurityException) {
            Log.d("myTag", "Security Exception, no location available");
        }

        val yelpHelper = YelpHelper()
//        var testName = yelpHelper.getBussinessList(lat.toString(),lng.toString())
//        var testToast = Toast.makeText(this,testName, Toast.LENGTH_SHORT)
//        testToast.show()

        //Get values from extras for account information
        var extrasBundle = intent.extras
        var email = extrasBundle.getString("email")
        var givenName = extrasBundle.getString("givenName")
        //var testToast = Toast.makeText(this,givenName, Toast.LENGTH_SHORT)
        //testToast.show()

        var flingContainer: SwipeFlingAdapterView = findViewById(R.id.frame)

        al = ArrayList<String>()

        al!!.add("Card 1")
        al!!.add("Card 2")

        arrayAdapter = ArrayAdapter<String>(this, R.layout.card, R.id.helloText, al)

        flingContainer.setAdapter(arrayAdapter)
        flingContainer.adapter = arrayAdapter
        flingContainer.setFlingListener(object : SwipeFlingAdapterView.onFlingListener {
            override fun onScroll(scrollProgressPercent: Float) {
                val view = flingContainer.selectedView
                view.findViewById<View>(R.id.item_swipe_right_indicator).setAlpha(if (scrollProgressPercent < 0) -scrollProgressPercent else 0f)
                view.findViewById<View>(R.id.item_swipe_left_indicator).setAlpha(if (scrollProgressPercent > 0) scrollProgressPercent else 0f)
            }

            override fun removeFirstObjectInAdapter() {
                // this is to delete an object from the Adapter (/AdapterView)
                Log.d("LIST", "removed object!")
                al!!.removeAt(0)
                arrayAdapter!!.notifyDataSetChanged()
            }

            override fun onLeftCardExit(dataObject: Any) {
                //Do something on the left!
                Toast.makeText(this@HomeActivity, "Left!", Toast.LENGTH_SHORT).show()
            }

            override fun onRightCardExit(dataObject: Any) {
                //Do something on the right!
                Toast.makeText(this@HomeActivity, "Right!", Toast.LENGTH_SHORT).show()
            }

            override fun onAdapterAboutToEmpty(itemsInAdapter: Int) {
                // Ask for more data here
                al!!.add("XML " + i.toString())
                arrayAdapter!!.notifyDataSetChanged()
                Log.d("LIST", "notified")
                i += 1
            }
        })

        // Add an OnItemClickListener
        flingContainer.setOnItemClickListener {
            itemPosition, dataObject -> Toast.makeText(this@HomeActivity, "Clicked!", Toast.LENGTH_SHORT)
        }

        @OnClick(R.id.item_swipe_right_indicator)
        fun right() {
            flingContainer.topCardListener.selectRight()
        }

        @OnClick(R.id.item_swipe_left_indicator)
        fun left() {
            flingContainer.topCardListener.selectLeft()
        }
    }
}
