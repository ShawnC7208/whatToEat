package com.chandwani.whattoeat

/**
 * Created by Arsalan on 1/8/2018.
 */
class cards {
    private var url: String
    private var name: String
    private var rating: String
    private var phone: String
    private var reviewCount: Double

    constructor(url:String, name:String, rating:String, phone:String, reviewCount:Double) {
        this.name=name
        this.url=url
        this.rating=rating
        this.phone=phone
        this.reviewCount = reviewCount
    }

    fun getReviewCount():Double {
        return reviewCount
    }

    fun geturl():String {
        return url
    }


    fun getName():String {
        return name
    }

    fun getRating():String {
        return rating
    }

    fun setRating() {
        this.rating=rating
    }

    fun getPhone():String {
        return phone
    }

    fun setPhone() {
        this.phone=phone
    }

}