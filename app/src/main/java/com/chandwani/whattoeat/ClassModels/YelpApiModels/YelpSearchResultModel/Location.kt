package com.chandwani.whattoeat.ClassModels.YelpApiModels.YelpSearchResultModel

import com.google.gson.annotations.SerializedName
/**
 * Created by Shawn on 1/3/2018.
 */

data class  Location (
        @SerializedName("address1") val address1: String,
        @SerializedName("address2") val address2: String,
        @SerializedName("address3") val address3: String?,
        @SerializedName("city") val city: String,
        @SerializedName("zip_code") val zip_code: String,
        @SerializedName("country") val country: String,
        @SerializedName("state") val state: String,
        @SerializedName("display_address") val display_address: ArrayList<String>
)