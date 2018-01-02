package com.chandwani.whattoeat

import android.content.Intent
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.location.places.GeoDataClient

class MainActivity : AppCompatActivity(),
        View.OnClickListener {

    lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var mGeoDataClient: GeoDataClient
    private var locationManager : LocationManager? = null
    val RC_SIGN_IN = 100
    var lat = 0.0
    var lng = 0.0

    //Location listener
    private val locationListener: LocationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            lat=location.latitude
            lng=location.longitude
        }
        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
        override fun onProviderEnabled(provider: String) {}
        override fun onProviderDisabled(provider: String) {}
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var gso: GoogleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        var signInButton = findViewById<SignInButton>(R.id.sign_in_button)
        signInButton.setSize(1)
        signInButton.setOnClickListener(this)

        //First get Location info
        locationManager = getSystemService(LOCATION_SERVICE) as LocationManager?
        try {
            // Request location updates
            locationManager?.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000L, 10f, locationListener)
        } catch(ex: SecurityException) {
            Log.d("myTag", "Security Exception, no location available")
        }

    }

    override fun onStart() {
        super.onStart()
        var account: GoogleSignInAccount? = GoogleSignIn.getLastSignedInAccount(this)

        //updateUI(account)
    }

    override fun onClick(V: View?) {
        when(V!!.getId()) {
            R.id.sign_in_button -> signIn()
        }
    }

    private fun signIn() {
        var signInIntent: Intent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            var task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(completedTask: com.google.android.gms.tasks.Task<GoogleSignInAccount>) {
        try {

            val account = completedTask.getResult(ApiException::class.java)

            updateUI(account)
        }
        catch (e : ApiException) {
            //TODO: catch exception
        }
    }

    private fun updateUI(account: GoogleSignInAccount?) {
        var intent = Intent(this, HomeActivity::class.java)
        intent.putExtra("email", account!!.email.toString())
        intent.putExtra("givenName", account!!.givenName.toString())
        intent.putExtra("lat", lat)
        intent.putExtra("lng", lng)
        startActivity(intent)
    }
}
