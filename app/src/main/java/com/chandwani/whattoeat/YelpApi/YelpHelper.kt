package com.chandwani.whattoeat.YelpApi

import com.chandwani.whattoeat.ClassModels.YelpApiModels.YelpSearchResultModel.YelpBusinessSearchResult
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
                       @Header("Authorization") key: String): io.reactivex.Observable<YelpBusinessSearchResult>
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
}