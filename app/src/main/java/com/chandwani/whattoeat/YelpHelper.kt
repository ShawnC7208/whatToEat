package com.chandwani.whattoeat

import com.yelp.fusion.client.connection.YelpFusionApi
import com.yelp.fusion.client.connection.YelpFusionApiFactory
import com.yelp.fusion.client.models.SearchResponse
import android.os.AsyncTask.execute





/**
 * Created by Arsalan on 12/30/2017.
 */
class YelpHelper{
    private lateinit var apiFactory : YelpFusionApiFactory
    private lateinit var yelpFusionApi : YelpFusionApi
    constructor(){
        apiFactory = YelpFusionApiFactory()
    //    yelpFusionApi=apiFactory.createAPI("fPm6_W_1h4Svblimj7GJAw", "sXo8RejGu9sQbh8NK8APAF8v3NbwveR0ycScLkve1YGNKuAGhbITGe81cJSrvNq5")
    }

    fun getBussinessList(lat : String,lng : String):String{
        val params:HashMap<String,String> = HashMap()
        params.put("latitude",lat)
        params.put("longitude",lng)
        val call = yelpFusionApi.getBusinessSearch(params)
        val response = call.execute()
        var nameTest = response.body().businesses.get(0).name
        return nameTest
    }
}