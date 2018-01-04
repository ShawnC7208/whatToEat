package com.chandwani.whattoeat.YelpApi

import com.chandwani.whattoeat.ClassModels.YelpApiModels.YelpSearchResultModel.YelpBuisnessSearchResult
import io.reactivex.Observable

/**
 * Created by Shawn on 1/1/2018.
 */
class YelpSearchRepository(val yelpHelper: YelpHelper) {
    fun buisnessList(lat: String, lng: String): Observable<YelpBuisnessSearchResult> {
        return yelpHelper.buisnessesList(lat,
                lng,
                "Bearer "+"umnrzi9gXZOdZv3APeOxbsGktOY7O0ndH-CI6wn9vg6f_U0gBLkUKiz5O4IyePSnweGre8iOtgjHE5pn7Sv3s8e1GUnDp0EGggS9O9dZ9oz1ot-kpn0AzOladwlIWnYx")
    }
}