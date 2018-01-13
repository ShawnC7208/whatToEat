package com.chandwani.whattoeat

import android.annotation.SuppressLint
import android.location.Location
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import butterknife.OnClick
import com.chandwani.whattoeat.ClassModels.YelpApiModels.YelpSearchResultModel.Business
import com.chandwani.whattoeat.ClassModels.YelpApiModels.YelpSearchResultModel.YelpBusinessSearchResult
import com.chandwani.whattoeat.YelpApi.YelpSearchRepositoryProvider
import com.google.android.gms.location.LocationRequest
import com.lorentzos.flingswipe.SwipeFlingAdapterView
import com.squareup.picasso.Picasso
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import java.util.*
import pl.charmas.android.reactivelocation2.ReactiveLocationProvider
import com.bumptech.glide.Glide
import kotlin.collections.ArrayList


class HomeActivity : AppCompatActivity() {

    private var arrayAdapter: arrayAdapter? = null
    private var rowItems: ArrayList<cards>? = null
    lateinit var listview: ListView

    //private var al: ArrayList<String>? = null
    //private var arrayAdapter: ArrayAdapter<String>? = null
    private var i: Int = 0
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    private lateinit var flingContainer: SwipeFlingAdapterView

    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)


        //Initialize variables
        //al = ArrayList<String>()
        rowItems = ArrayList<cards>()
        //flingContainer = findViewById<SwipeFlingAdapterView>(R.id.frame)
        //arrayAdapter = ArrayAdapter<String>(this, R.layout.card, R.id.helloText, al)
        //arrayAdapter= arrayAdapter(this,R.layout.card, rowItems)


        //Get the location of device currently
        val request = LocationRequest.create() //standard GMS LocationRequest
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setNumUpdates(1)
                .setInterval(100)

        //Get location with observable then call yelp api on the subscribe after location returns
        val locationProvider = ReactiveLocationProvider(this)
        locationProvider.getUpdatedLocation(request)
                .subscribe(object : Consumer<Location> {
                    override fun accept(location: Location?) {
                        callYelpAPI("food", location!!.latitude, location!!.longitude)
                    }
                })
    }

    //Call yelp api
    fun callYelpAPI(term: String ,lat: Double, lng: Double) {
        //Call Yelp API
        val repository = YelpSearchRepositoryProvider.provideYelpSearchRepository()
        compositeDisposable.add(
                repository.businessList(term, lat.toString(), lng.toString())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe({
                            result -> addBusinessCards(result)
                        }, {
                            error -> error.printStackTrace()
                        })
        )
    }

    //Called after getting search results form Yelp API
    fun addBusinessCards(yelpApiSearchResults: YelpBusinessSearchResult) {
        //Do for each loop here for buisness inside of the YelpBusinessSearchResult object

        for(business in yelpApiSearchResults.businesses) {
            getDetailsForBusiness(business)
        }
    }

    //Get more details for a Business
    fun getDetailsForBusiness(business: Business) {
        val repository = YelpSearchRepositoryProvider.provideYelpSearchRepository()
        compositeDisposable.add(
                repository.businessDetails(business.id)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe({
                            result -> addCardToList(business, result)
                        }, {
                            error -> error.printStackTrace()
                        })
        )
    }

    //Add A card to the list of cards
    fun addCardToList(business: Business, buisnessDetail: Any) {

        //var image:ImageView= findViewById<ImageView>(R.id.image)
        var bussinessInfo:String = business.name //+ "\n" + business.rating
        var imageUrl:String = business.image_url

        //al!!.add(bussinessInfo)
        val card = cards(imageUrl,bussinessInfo)
        rowItems!!.add(card)

        if(arrayAdapter == null) {
            flingContainer = findViewById<SwipeFlingAdapterView>(R.id.frame)
            arrayAdapter= arrayAdapter(this,R.layout.card, rowItems)
            createSwipeCards()
        }
        arrayAdapter!!.add(card)
        arrayAdapter!!.notifyDataSetChanged()
    }

    //Create swipe container
    fun createSwipeCards() {

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
                //al!!.removeAt(0)
                //rowItems!!.removeAt(0)
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
//                al!!.add("XML Test" + i.toString())
//                arrayAdapter!!.notifyDataSetChanged()
//                Log.d("LIST", "notified")
//                i += 1
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
