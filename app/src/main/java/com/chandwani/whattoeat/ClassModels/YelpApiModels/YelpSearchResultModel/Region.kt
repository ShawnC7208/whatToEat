package com.chandwani.whattoeat.ClassModels.YelpApiModels.YelpSearchResultModel

import com.google.gson.annotations.SerializedName
import com.yelp.fusion.client.models.Coordinates
/**
 * Created by Shawn on 1/3/2018.
 */

data class  Region (
        @SerializedName("center") val center: Coordinates
)