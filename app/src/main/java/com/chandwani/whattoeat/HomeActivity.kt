package com.chandwani.whattoeat

import android.annotation.SuppressLint
import android.content.Intent
import android.location.Location
import android.net.Uri
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.design.widget.FloatingActionButton
import android.text.Html
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
import com.chandwani.whattoeat.ClassModels.YelpApiModels.YelpSearchResultModel.yelpReviewModels.Review
import com.chandwani.whattoeat.ClassModels.YelpApiModels.YelpSearchResultModel.yelpReviewModels.Reviews
import io.reactivex.Scheduler
import retrofit2.HttpException
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList


class HomeActivity : AppCompatActivity() {

    private var arrayAdapter: arrayAdapter? = null
    private var rowItems: ArrayList<cards>? = null
    private var offsetCounter: Int = 20
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    private var startLat: Double = 0.0
    private var startLng: Double = 0.0
    private lateinit var flingContainer: SwipeFlingAdapterView
    private var cardAddress: String? = ""
    private lateinit var directionButton: FloatingActionButton
    private lateinit var shareButton: FloatingActionButton
    private lateinit var scheduler: Scheduler

    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        var threadCount: Int = Runtime.getRuntime().availableProcessors();
        var threadPoolExecutor: ExecutorService = Executors.newFixedThreadPool(threadCount);
        scheduler = Schedulers.from(threadPoolExecutor);


        //Initialize variables
        rowItems = ArrayList<cards>()

        flingContainer = findViewById<SwipeFlingAdapterView>(R.id.frame)
        arrayAdapter= arrayAdapter(this,R.layout.card, rowItems)
        createSwipeCards()

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
                        startLat = location!!.latitude
                        startLng = location.longitude
                        callYelpAPI("food", location.latitude, location.longitude, 20)
                    }
                })

        directionButton = findViewById(R.id.Navigation)
        shareButton = findViewById(R.id.Share)

    }

    //Call yelp api
    fun callYelpAPI(term: String ,lat: Double, lng: Double, offset: Int) {
        //Call Yelp API
        val repository = YelpSearchRepositoryProvider.provideYelpSearchRepository()
        compositeDisposable.add(
                repository.businessList(term, lat.toString(), lng.toString(), offset)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(scheduler)
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
            getReviewsForBusiness(business)
            //addCardToList(business)
            //getDetailsForBusiness(business)
        }
    }

    //Get more details for a Business
    fun getDetailsForBusiness(business: Business) {
        val repository = YelpSearchRepositoryProvider.provideYelpSearchRepository()
        compositeDisposable.add(
                repository.businessDetails(business.id)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(scheduler)
                        .subscribe({
                            result -> addCardToList(business, result)
                        }, {
                            error -> error.printStackTrace()
                        })
        )
    }

    //Get reviews for a Business
    fun getReviewsForBusiness(business: Business) {
        val repository = YelpSearchRepositoryProvider.provideYelpSearchRepository()
        compositeDisposable.add(
                repository.businessReviews(business.id)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(scheduler)
                        .subscribe({
                            result -> addCardToList(business, result)
                        }, {
                            error ->
                            try {
                                if((error as HttpException).code() == 429) {
                                    getReviewsForBusiness(business)
                                } else {
                                    error.printStackTrace()
                                }
                            } catch (ex: Exception) {
                                error.printStackTrace()
                            }
                        })
        )
    }

    //Add A card to the list of cards
    fun addCardToList(business: Business, businessReviews: Reviews) {

        var bussinessName:String = business.name
        var imageUrl:String = business.image_url
        var businessRating:String = business.rating.toString()
        var businessPhone:String = "Phone: "+business.display_phone
        var reviewCount:Double = business.review_count
        var firstReviewUserImage:String = businessReviews.reviews[0].user.image_url
        var firstReviewUserName:String = businessReviews.reviews[0].user.name
        var firstReviewText:String = businessReviews.reviews[0].text
        var businessAddress:String

        //Get Address
        businessAddress = bussinessName
        for(addressString in business.location.display_address) {
            businessAddress += (" " + addressString)
        }

        val card = cards(imageUrl,bussinessName,businessRating,businessPhone, reviewCount,firstReviewUserImage,firstReviewUserName,firstReviewText, businessAddress)
        rowItems!!.add(card)
        arrayAdapter!!.notifyDataSetChanged()

        if (cardAddress == "") {
            cardAddress = rowItems!![0].getBusinessAddress();
            directionButton.setOnClickListener {
                var mapsIntent = Intent(android.content.Intent.ACTION_VIEW, Uri.parse("http://maps.google.co.in/maps?q="  + cardAddress));
                startActivity(mapsIntent);
            }
            shareButton.setOnClickListener {
                var sharingIntent = Intent(Intent.ACTION_SEND)
                sharingIntent.type = "text/plain"
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, cardAddress)
                startActivity(Intent.createChooser(sharingIntent, "Share using"))
            }
        }
    }

    /*
    //Add A card to the list of cards
    fun addCardToList(business: Business, buisnessDetail: Any) {

        var bussinessName:String = business.name //+ "\n" + business.rating
        var imageUrl:String = business.image_url
        var businessRating:String = business.rating.toString()
        var businessPhone:String = "Phone: "+business.display_phone
        var reviewCount:Double = business.review_count

        val card = cards(imageUrl,bussinessName,businessRating,businessPhone, reviewCount)
        rowItems!!.add(card)
        arrayAdapter!!.notifyDataSetChanged()
    }

    //Add A card to the list of cards
    fun addCardToList(business: Business) {

        var bussinessName:String = business.name //+ "\n" + business.rating
        var imageUrl:String = business.image_url
        var businessRating:String = business.rating.toString()
        var businessPhone:String = "Phone: "+business.display_phone
        var reviewCount:Double = business.review_count
        val card = cards(imageUrl,bussinessName,businessRating,businessPhone, reviewCount)
        rowItems!!.add(card)
        arrayAdapter!!.notifyDataSetChanged()
    }
    */

    //Create swipe container
    fun createSwipeCards() {

        flingContainer.setAdapter(arrayAdapter)
        flingContainer.adapter = arrayAdapter
        flingContainer.setFlingListener(object : SwipeFlingAdapterView.onFlingListener {
            @RequiresApi(Build.VERSION_CODES.HONEYCOMB)
            override fun onScroll(scrollProgressPercent: Float) {
                val view = flingContainer.selectedView
                view.findViewById<View>(R.id.item_swipe_right_indicator).setAlpha(if (scrollProgressPercent < 0) -scrollProgressPercent else 0f)
                view.findViewById<View>(R.id.item_swipe_left_indicator).setAlpha(if (scrollProgressPercent > 0) scrollProgressPercent else 0f)
            }

            override fun removeFirstObjectInAdapter() {
                // this is to delete an object from the Adapter (/AdapterView)
                Log.d("LIST", "removed object!")
                if(rowItems!!.count() > 2) {
                    cardAddress = rowItems!![1].getBusinessAddress()
                    directionButton.setOnClickListener {
                        var mapsIntent = Intent(android.content.Intent.ACTION_VIEW, Uri.parse("http://maps.google.co.in/maps?q="  + cardAddress))
                        startActivity(mapsIntent);
                    }
                    shareButton.setOnClickListener {
                        var sharingIntent = Intent(Intent.ACTION_SEND)
                        sharingIntent.type = "text/plain"
                        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, cardAddress)
                        startActivity(Intent.createChooser(sharingIntent, "Share using"))
                    }
                }
                rowItems!!.removeAt(0)
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
                callYelpAPI("food", startLat, startLng, offsetCounter)
                offsetCounter += 20
            }
        })

        // Add an OnItemClickListener
        flingContainer.setOnItemClickListener {
            itemPosition, dataObject -> Toast.makeText(this@HomeActivity, "Clicked!", Toast.LENGTH_SHORT).show()
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
