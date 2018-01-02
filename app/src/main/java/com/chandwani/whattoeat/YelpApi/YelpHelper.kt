package com.chandwani.whattoeat.YelpApi

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

/**
 * Created by Arsalan on 12/30/2017.
 */
interface YelpHelper{


    @GET("businesses/search")
    fun buisnessesList(@Query("latitude") lat : String,
                       @Query("longitude") lng : String,
                       @Header("Authorization") key: String): io.reactivex.Observable<Any>
    companion object Factory {
        fun create(): YelpHelper {
            val retrofit = Retrofit
                    .Builder()
                    .baseUrl("https://api.yelp.com/v3/")
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            return  retrofit.create(YelpHelper::class.java)
        }
    }

    //private lateinit var apiFactory : YelpFusionApiFactory
    //private lateinit var yelpFusionApi : YelpFusionApi
//    constructor(){


//        apiFactory = YelpFusionApiFactory()
//        yelpFusionApi=apiFactory.createAPI("fPm6_W_1h4Svblimj7GJAw", "sXo8RejGu9sQbh8NK8APAF8v3NbwveR0ycScLkve1YGNKuAGhbITGe81cJSrvNq5")
 //   }

  //  fun getBussinessList(lat : String,lng : String):String{
//        val params:HashMap<String,String> = HashMap()
//        params.put("latitude",lat)
//        params.put("longitude",lng)
//        val call = yelpFusionApi.getBusinessSearch(params)
//        val response = call.execute()
//        var nameTest = response.body()!!.businesses.get(0).name
//        return nameTest
  //      return ""
  //  }
}