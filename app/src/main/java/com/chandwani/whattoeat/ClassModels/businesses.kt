package com.chandwani.whattoeat.ClassModels

import com.google.gson.annotations.SerializedName
import com.yelp.fusion.client.models.Coordinates
import com.yelp.fusion.client.models.Location

/**
 * Created by Arsalan on 1/2/2018.
 */

data class Business(
        //private val Categories: HashMap<String,ArrayList<String>>,
        //private val Transaction = HashMap<String,String>,
        @SerializedName("id") val id: String,
        @SerializedName("name") val name: String,
        @SerializedName("image_url") val image_url: String,
        @SerializedName("is_closed") val is_closed: Boolean,
        @SerializedName("url") val url: String,
        @SerializedName("review_count") val review_count: Double,
        //@SerializedName("categories") val categories: Categories,
        @SerializedName("rating") val rating: Double,
        @SerializedName("coordinates") val coordinates: Coordinates,
        //@SerializedName("transactions") val transactions: Transaction
        @SerializedName("price") val price: Double,
        //@SerializedName("location") val location: Location,
        @SerializedName("phone") val phone: String,
        @SerializedName("display_phone") val display_phone: String,
        @SerializedName("distance") val distance: Double
)
