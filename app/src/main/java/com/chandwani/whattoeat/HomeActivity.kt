package com.chandwani.whattoeat

import android.annotation.SuppressLint
import android.location.Location
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import butterknife.OnClick
import com.chandwani.whattoeat.ClassModels.YelpApiModels.YelpSearchResultModel.Business
import com.chandwani.whattoeat.ClassModels.YelpApiModels.YelpSearchResultModel.YelpBusinessSearchResult
import com.chandwani.whattoeat.YelpApi.YelpSearchRepositoryProvider
import com.google.android.gms.location.LocationRequest
import com.lorentzos.flingswipe.SwipeFlingAdapterView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import java.util.*
import pl.charmas.android.reactivelocation2.ReactiveLocationProvider


class HomeActivity : AppCompatActivity() {

    private var al: ArrayList<String>? = null
    private var arrayAdapter: ArrayAdapter<String>? = null
    private var i: Int = 0
    val compositeDisposable: CompositeDisposable = CompositeDisposable()


    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        //Get values from extras for account information
        //var extrasBundle = intent.extras
        //var email = extrasBundle.getString("email")
        //var givenName = extrasBundle.getString("givenName")

        val request = LocationRequest.create() //standard GMS LocationRequest
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setNumUpdates(1)
                .setInterval(100)


        //Create swipe container
        var flingContainer: SwipeFlingAdapterView = findViewById<SwipeFlingAdapterView>(R.id.frame)

        al = ArrayList<String>()

        //al!!.add("Card 1")
        //al!!.add("Card 2")

        //Get location with observable then call yelp api on the subscribe after location returns
        val locationProvider = ReactiveLocationProvider(this)
        locationProvider.getUpdatedLocation(request)
                .subscribe(object : Consumer<Location> {
                    override fun accept(location: Location?) {
                        callYelpAPI(location!!.latitude, location!!.longitude)
                    }
                })


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

    //Call yelp api
    fun callYelpAPI(lat: Double, lng: Double) {
        //Call Yelp API
        val repository = YelpSearchRepositoryProvider.provideYelpSearchRepository()
        compositeDisposable.add(
                repository.buisnessList(lat.toString(), lng.toString())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe({
                            result -> getBusinessDetails(result)
                        }, {
                            error -> error.printStackTrace()
                        })
        )
    }

    fun getBusinessDetails(yelpApiSearchResults: YelpBusinessSearchResult) {
        //Do for each loop here for buisness inside of the YelpBusinessSearchResult object
        var business:Business
        for(business in yelpApiSearchResults.businesses) {
            var bussinessInfo:String = business.name + "\n" + business.rating
            al!!.add(bussinessInfo)
            arrayAdapter!!.notifyDataSetChanged()
            Log.d("LIST", "notified")
            //Toast.makeText(this@HomeActivity, business.name, Toast.LENGTH_SHORT).show()
        }
    }
}
