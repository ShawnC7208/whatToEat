package com.chandwani.whattoeat.ClassModels.YelpApiModels.YelpSearchResultModel

import com.google.gson.annotations.SerializedName
import com.yelp.fusion.client.models.Coordinates

/**
 * Created by Arsalan on 1/2/2018.
 */

data class Business (
        @SerializedName("id") val id: String,
        @SerializedName("name") val name: String,
        @SerializedName("image_url") val image_url: String,
        @SerializedName("is_closed") val is_closed: Boolean,
        @SerializedName("url") val url: String,
        @SerializedName("review_count") val review_count: Double,
        @SerializedName("categories") val categories: ArrayList<Category>,
        @SerializedName("rating") val rating: Double,
        @SerializedName("coordinates") val coordinates: Coordinates,
        @SerializedName("transactions") val transactions: ArrayList<Any>,
        @SerializedName("price") val price: String,
        @SerializedName("location") val location: Location,
        @SerializedName("phone") val phone: String,
        @SerializedName("display_phone") val display_phone: String,
        @SerializedName("distance") val distance: Double
)