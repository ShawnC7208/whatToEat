package com.chandwani.whattoeat.ClassModels.YelpApiModels.YelpSearchResultModel

import com.google.gson.annotations.SerializedName

/**
 * Created by Shawn on 1/3/2018.
 */
data class YelpBusinessSearchResult(
        @SerializedName("businesses") val businesses: ArrayList<Business>,
        @SerializedName("total") val total: Int,
        @SerializedName("region") val region: Region
        )

