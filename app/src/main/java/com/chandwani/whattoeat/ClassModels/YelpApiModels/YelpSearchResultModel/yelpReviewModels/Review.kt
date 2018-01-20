package com.chandwani.whattoeat.ClassModels.YelpApiModels.YelpSearchResultModel.yelpReviewModels

import com.google.gson.annotations.SerializedName

/**
 * Created by shawnchandwani on 1/19/18.
 */
data class Review (
    @SerializedName("id") val id: String,
    @SerializedName("url") val url: String,
    @SerializedName("text") val text: String,
    @SerializedName("rating") val rating: Int,
    @SerializedName("user") val user: user,
    @SerializedName("time_created") val time_created: String
    )