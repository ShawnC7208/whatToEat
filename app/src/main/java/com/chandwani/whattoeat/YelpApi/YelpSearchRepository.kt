package com.chandwani.whattoeat.YelpApi

import com.chandwani.whattoeat.ClassModels.YelpApiModels.YelpSearchResultModel.YelpBusinessSearchResult
import com.chandwani.whattoeat.ClassModels.YelpApiModels.YelpSearchResultModel.yelpReviewModels.Review
import com.chandwani.whattoeat.ClassModels.YelpApiModels.YelpSearchResultModel.yelpReviewModels.Reviews
import io.reactivex.Observable

/**
 * Created by Shawn on 1/1/2018.
 */
class YelpSearchRepository(val yelpHelper: YelpHelper) {
    fun businessList(term: String, lat: String, lng: String): Observable<YelpBusinessSearchResult> {
        return yelpHelper.businessesList(term,
                lat,
                lng,
                50,
                "Bearer "+"umnrzi9gXZOdZv3APeOxbsGktOY7O0ndH-CI6wn9vg6f_U0gBLkUKiz5O4IyePSnweGre8iOtgjHE5pn7Sv3s8e1GUnDp0EGggS9O9dZ9oz1ot-kpn0AzOladwlIWnYx")
    }
    fun businessDetails(id: String): Observable<Reviews> {
        return yelpHelper.businessDetails(id,
                "Bearer "+"umnrzi9gXZOdZv3APeOxbsGktOY7O0ndH-CI6wn9vg6f_U0gBLkUKiz5O4IyePSnweGre8iOtgjHE5pn7Sv3s8e1GUnDp0EGggS9O9dZ9oz1ot-kpn0AzOladwlIWnYx")
    }
    fun businessReviews(id: String): Observable<Reviews> {
        return yelpHelper.businessReviews(id,
                "Bearer "+"umnrzi9gXZOdZv3APeOxbsGktOY7O0ndH-CI6wn9vg6f_U0gBLkUKiz5O4IyePSnweGre8iOtgjHE5pn7Sv3s8e1GUnDp0EGggS9O9dZ9oz1ot-kpn0AzOladwlIWnYx")
    }

}