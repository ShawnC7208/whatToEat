package com.chandwani.whattoeat.ClassModels.YelpApiModels.YelpSearchResultModel.yelpReviewModels

import com.google.gson.annotations.SerializedName

/**
 * Created by shawnchandwani on 1/19/18.
 */
data class Reviews (
        @SerializedName("reviews") val reviews: ArrayList<Review>,
        @SerializedName("total") val total: Int,
        @SerializedName("possible_languages") val possible_languages: ArrayList<String>
        )