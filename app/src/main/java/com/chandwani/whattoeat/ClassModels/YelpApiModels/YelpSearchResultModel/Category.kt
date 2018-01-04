package com.chandwani.whattoeat.ClassModels.YelpApiModels.YelpSearchResultModel

import com.google.gson.annotations.SerializedName
/**
 * Created by Shawn on 1/3/2018.
 */

data class  Category (
        @SerializedName("alias") val alias: String,
        @SerializedName("title") val title: String
)