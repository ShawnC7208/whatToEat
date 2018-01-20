package com.chandwani.whattoeat.ClassModels.YelpApiModels.YelpSearchResultModel.yelpReviewModels

import com.google.gson.annotations.SerializedName

/**
 * Created by shawnchandwani on 1/19/18.
 */
data class user (
        @SerializedName("image_url") val image_url: String,
        @SerializedName("name") val name: String
        )